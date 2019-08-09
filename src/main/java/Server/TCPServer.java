package Server;

import Singleton.UserManager;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {

    private final int port;
    private ClientListener mListener;
//    private List<ClientHandler> clientHandlerList = new ArrayList<ClientHandler>();

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
        UserManager.getInstance().removeAll();
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

            System.out.println("Server is ready～");
            // wait client connect
            do {
                // get client
                Socket client;
                try {
                    client = server.accept();
                } catch (IOException e) {
                    continue;
                }
                ClientHandler clientHandler = null;
                try {
                    //  client create asynchronous thread
                    clientHandler = new ClientHandler(client, new ClientHandler.CloseNotify() {
                        public void onSelfClosed(ClientHandler handler) {
                            UserManager.getInstance().remove(handler);
                        }
                    });
                    //  start thread

                    clientHandler.readToPrint();
//                    clientHandlerList.add(clientHandler);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Client connect error" + e.getMessage());

                }
            } while (!done);

            System.out.println("Server close！");
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
