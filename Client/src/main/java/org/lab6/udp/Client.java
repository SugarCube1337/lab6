package org.lab6.udp;


import org.lab6.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

public class Client {
    private DatagramChannel channel = null;
    private Selector selector = null;
    public static final int PACKET_SIZE = 64-8;;
    //public static final int PACKET_SIZE = 5*1024-8;


    public Client() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        int bufferSize = 1024 * 1024; // 1 MB
        //увеличение размеров для одного пакета
        channel.setOption(StandardSocketOptions.SO_RCVBUF, bufferSize);
        channel.setOption(StandardSocketOptions.SO_SNDBUF, bufferSize);

        Scanner scanner = new Scanner(System.in);
        int port;
        while (true){
            System.out.println("Enter the port:");
            try{
                port = Integer.parseInt(scanner.nextLine());
                if(available(port)){
                    channel.connect(new InetSocketAddress("localhost", port));
                    break;
                }
            }catch (Exception e){
                System.out.println("The port doesn't fit");
            }
        }
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
    }

    public byte[] sendMsg(byte[] message) throws IOException {
        List<byte[]> chunks = splitByteArray(message);
        List<byte[]> firstInfo = new ArrayList<>();

        firstInfo.add(new byte[]{1,1,1,1});
        firstInfo.add(Utils.intToBytes(chunks.size()));
        ByteBuffer bufferSize = ByteBuffer.wrap(Utils.joinByteArrays(firstInfo));
        channel.write(bufferSize);
        for (int i = 0; i < chunks.size(); i++) {
            List<byte[]> chunkInfo = new ArrayList<>();
            chunkInfo.add(new byte[]{0,0,0,0});
            chunkInfo.add(Utils.intToBytes(i));
            chunkInfo.add(chunks.get(i));
            ByteBuffer buffer = ByteBuffer.wrap(Utils.joinByteArrays(chunkInfo));
            channel.write(buffer);
        }
        ChunksData currentWorker = new ChunksData(1);
        while(currentWorker.isActual()) {
            selector.select();
            // Обрабатываем готовые ключи
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                if(currentWorker.isReady())
                    continue;
                SelectionKey key = it.next();
                it.remove();
                // Если ключ готов к чтению
                if (key.isReadable()) {
                    ByteBuffer helpBuffer = ByteBuffer.allocate(PACKET_SIZE+8);
                    channel.receive(helpBuffer);
                    byte[] recieved = helpBuffer.array();
                    byte validation = Utils.checkFirst4Bytes(recieved);
                    if(validation == 1) {
                        byte[] size = Arrays.copyOfRange(recieved, 4, 8);
                        currentWorker = new ChunksData(Utils.fromByteArray(size));
                    } else if(validation == 0) {
                        int index = Utils.fromByteArray(Arrays.copyOfRange(recieved, 4, 8));
                        byte[] data = Arrays.copyOfRange(recieved, 8, recieved.length);
                        currentWorker.addChunk(index, data);
                        if(currentWorker.isReady()) {
                            byte[] response = currentWorker.getFullResponse();
                            return response;
                        }
                    }
                }
            }
        }
        return null;
    }
    /**
     * Checks to see if a specific port is available.
     *
     * @param port the port to check for availability
     */
    public static boolean available(int port) {
        if (port < 1023 || port > 65534) {
            return false;
        }
        return true;
    }
    public static byte[] joinByteArrays(List<byte[]> chunks) {
        int totalLength = 0;
        for (byte[] chunk : chunks) {
            totalLength += chunk.length;
        }

        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, result, offset, chunk.length);
            offset += chunk.length;
        }

        return result;
    }

    public static List<byte[]> splitByteArray(byte[] source) {
        int maxChunkSize = PACKET_SIZE; //B если меняем тут, то меняем и в connect manager server: приходящие кб
        if (source.length <= maxChunkSize) {
            return Collections.singletonList(source);
        }
        List<byte[]> chunks = new ArrayList<>();
        for(int i = 0; i <= (int)Math.ceil(source.length/PACKET_SIZE); i++) {
            chunks.add(Arrays.copyOfRange(source, i*PACKET_SIZE, (i+1)*PACKET_SIZE));
        }
        return chunks;
    }
}