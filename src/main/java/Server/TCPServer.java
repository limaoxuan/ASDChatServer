package Server;

import Singleton.UserManager;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {

    private final int port;
    private ClientListener mListener;
    private List<ClientHandler> clientHandlerList = new ArrayList<ClientHandler>();

    public TCPServer(int port) {
        this.port = port;
    }


    public boolean start() {
        try {
            ClientListener listener = new ClientListener(port);
            mListener = listener;
            listener.start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void stop() {
        if (mListener != null) {
            mListener.exit();
        }
        for (ClientHandler client : clientHandlerList) {
            client.exit();
        }

        clientHandlerList.clear();
    }


    private class ClientListener extends Thread {
        private ServerSocket server;
        private boolean done = false;


        private ServerSocket createServerSocket() throws IOException {
            return new ServerSocket();
        }

        private ClientListener(int port) throws IOException {
            server = createServerSocket();
            initServerSocket(server);
            server.bind(new InetSocketAddress(Inet4Address.getLocalHost(), port), 50);


            System.out.println("Server.Server Ready");
            System.out.println("Server.Server:" + server.getInetAddress() + " port " + server.getLocalPort());
        }

        private void initServerSocket(ServerSocket serverSocket) throws IOException {
            serverSocket.setReuseAddress(true);
            serverSocket.setReceiveBufferSize(64 * 1024 * 1024);
//        serverSocket.setSoTimeout(2000);
            serverSocket.setPerformancePreferences(1, 1, 1);
        }

        @Override
        public void run() {
            super.run();

            System.out.println("服务器准备就绪～");
            // 等待客户端连接
            do {
                // 得到客户端
                Socket client;
                try {
                    client = server.accept();
                } catch (IOException e) {
                    continue;
                }
                ClientHandler clientHandler = null;
                try {
                    // 客户端构建异步线程
                    clientHandler = new ClientHandler(client, new ClientHandler.CloseNotify() {
                        public void onSelfClosed(ClientHandler handler) {
//                            UserManager.re
                            clientHandlerList.remove(handler);
                        }
                    });
                    // 启动线程

                    clientHandler.readToPrint();
                    clientHandlerList.add(clientHandler);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("客户端连接异常" + e.getMessage());

                }
            } while (!done);

            System.out.println("服务器已关闭！");
        }

        void exit() {
            done = true;
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
