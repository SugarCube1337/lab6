package org.lab6;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

public class ConnectionManager {

    public static final int PACKET_SIZE = 64 - 8;
    //    public static final int PACKET_SIZE = 5*1024-8;
    private DatagramChannel channel;
    private Selector selector;
    private SocketAddress clientAddress;

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
}



