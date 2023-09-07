package org.lab6;

import org.lab6.actions.Action;
import org.lab6.udp.ChunksData;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;
import org.lab6.actions.*;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

import static org.lab6.Utils.*;

public class ConnectionManager {

    public static final int PACKET_SIZE = 64 - 8;
    //    public static final int PACKET_SIZE = 5*1024-8;
    private DatagramChannel channel;
    private Selector selector;
    private SocketAddress clientAddress;

    private Map<String, ChunksData> clientChunks = new HashMap<>();
    private Map<ServerCommandType, Action> actions = new LinkedHashMap<>();

    public ConnectionManager() {
        registerAction(ServerCommandType.GET_INFO, new InfoAction());
        registerAction(ServerCommandType.SHOW, new ShowAction());
        registerAction(ServerCommandType.ADD, new AddAction());
        registerAction(ServerCommandType.UPDATE, new UpdateAction());
        registerAction(ServerCommandType.REMOVE_BY_ID, new RemoveByIdAction());
        registerAction(ServerCommandType.CLEAR, new ClearAction());
        registerAction(ServerCommandType.REMOVE_LAST, new RemoveLastAction());
        registerAction(ServerCommandType.REMOVE_GREATER, new RemoveGreaterAction());
        registerAction(ServerCommandType.REORDER, new ReorderAction());
        registerAction(ServerCommandType.GET_MAX_DISTANCE, new MaxByDistanceAction());
        registerAction(ServerCommandType.GET, new GetAction());
    }


    public void run() throws IOException {
        // Создаем канал и настраиваем его на неблокирующий режим
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        int bufferSize = 1024 * 1024; // 1 MB
        channel.setOption(StandardSocketOptions.SO_RCVBUF, bufferSize);
        channel.setOption(StandardSocketOptions.SO_SNDBUF, bufferSize);

        Scanner scanner = new Scanner(System.in);
        int port;
        while (true) {
            System.out.println("Enter the port: ");
            try {
                port = Integer.parseInt(scanner.nextLine());
                if (available(port)) {
                    channel.bind(new InetSocketAddress(port));
                    break;
                }
            } catch (Exception e) {
                System.out.println("The port doesn't fit");
            }
        }
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
        System.out.println("The server's awake.");

        // Ожидаем сообщения от клиента
        while (true) {
            selector.select();
            // Обрабатываем готовые ключи
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                // Если ключ готов к чтению
                if (key.isReadable()) {
                    ByteBuffer bufferHelp = ByteBuffer.allocate(PACKET_SIZE + 8);
                    clientAddress = channel.receive(bufferHelp);
                    byte[] recieved = bufferHelp.array();
                    System.out.println("The chunk was received with a long " + recieved.length + " byte from the client " + clientAddress.toString());
                    byte validation = Utils.checkFirst4Bytes(recieved);
                    if (validation == 1) {
                        byte[] size = Arrays.copyOfRange(recieved, 4, 8);
                        ChunksData chunksData = new ChunksData(Utils.fromByteArray(size));
                        clientChunks.put(clientAddress.toString(), chunksData);
                    } else if (validation == 0) {
                        if (!clientChunks.containsKey(clientAddress.toString()))
                            continue;
                        int index = Utils.fromByteArray(Arrays.copyOfRange(recieved, 4, 8));
                        byte[] data = Arrays.copyOfRange(recieved, 8, recieved.length);
                        clientChunks.get(clientAddress.toString()).addChunk(index, data);
                        if (clientChunks.get(clientAddress.toString()).isReady()) {
                            //
                            byte[] request = clientChunks.get(clientAddress.toString()).getFullResponse();
                            System.out.println("Received a complete client request with a length of " + request.length + " byte.");
                            ServerCommand message = executeInput((ServerCommand) deserializeObject(request));
                            System.out.println("Sending a reply " + message.type + "  to the client " + clientAddress.toString());
                            List<byte[]> chunks = splitByteArray(serializeObject(message));
                            List<byte[]> firstInfo = new ArrayList<>();
                            firstInfo.add(new byte[]{1, 1, 1, 1});
                            firstInfo.add(intToBytes(chunks.size()));
                            ByteBuffer firstInfoSize = ByteBuffer.wrap(joinByteArrays(firstInfo));
                            channel.send(firstInfoSize, clientAddress);
                            System.out.println("Send a chunk of length " + firstInfoSize.array().length + " byte to the client " + clientAddress.toString());
                            for (int i = 0; i < chunks.size(); i++) {
                                List<byte[]> chunkInfo = new ArrayList<>();
                                chunkInfo.add(new byte[]{0, 0, 0, 0});
                                chunkInfo.add(intToBytes(i));
                                chunkInfo.add(chunks.get(i));
                                ByteBuffer responseBuffer = ByteBuffer.wrap(Utils.joinByteArrays(chunkInfo));
                                channel.send(responseBuffer, clientAddress);
                                System.out.println("Send a chunk of length " + responseBuffer.array().length + " byte to the client " + clientAddress.toString());
                            }
                            clientChunks.remove(clientAddress.toString());
                        }
                    }

                }
            }
        }
    }

    /**
     * Execute action
     *
     * @param action Command with arguments
     * @return Result
     */
    public final ServerCommand executeInput(ServerCommand action) {
        if (!actions.containsKey(action.type))
            return new ServerCommand(ServerCommandType.ERROR, "Unknown command for the server".getBytes());
        return actions.get(action.type).execute(action.data);
    }

    public static boolean available(int port) {
        if (port < 1023 || port > 65534) {
            return false;
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

    public static List<byte[]> splitByteArray(byte[] source) {
        int maxChunkSize = PACKET_SIZE;
        if (source.length <= maxChunkSize) {
            return Collections.singletonList(source);
        }
        int numChunks = (int) Math.ceil((double) source.length / maxChunkSize);
        List<byte[]> chunks = new ArrayList<>(numChunks);

        int offset = 0;
        for (int i = 0; i < numChunks; i++) {
            int chunkSize = Math.min(maxChunkSize, source.length - offset);
            byte[] chunk = Arrays.copyOfRange(source, offset, offset + chunkSize);
            chunks.add(chunk);
            offset += chunkSize;
        }

        return chunks;
    }

    public Action getAction(ServerCommandType type) {
        return actions.get(type);
    }

    public final void registerAction(ServerCommandType type, Action action) {
        actions.put(type, action);
    }
}



