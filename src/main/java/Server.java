import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 20000;

    //
    public static void main(String[] args) throws IOException {
        ServerSocket server = createServerSocket();
        initServerSocket(server);

        // queue is 50// accept remove
        server.bind(new InetSocketAddress(Inet4Address.getLocalHost(), PORT), 50);

        System.out.println("Server Ready");
        System.out.println("Server:" + server.getInetAddress() + " port " + server.getLocalPort());

        for (; ; ) {
            // get client
            Socket client = server.accept();
            // create asynchronous thread for every client
            ClientHandle clientHandle = new ClientHandle(client);
            // start thread
            clientHandle.start();
        }


    }

    private static void initServerSocket(ServerSocket serverSocket) throws IOException {
        serverSocket.setReuseAddress(true);

        serverSocket.setReceiveBufferSize(64 * 1024 * 1024);

//        serverSocket.setSoTimeout(2000);
        serverSocket.setPerformancePreferences(1, 1, 1);
    }

    private static ServerSocket createServerSocket() throws IOException {


        return new ServerSocket();
    }

    private static class ClientHandle extends Thread {
        private Socket socket;
        private boolean flag = true;

        ClientHandle(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("new Client " + socket.getInetAddress() + "port " + socket.getPort());

            try {
                PrintStream socketOutPut = new PrintStream(socket.getOutputStream());
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                do {
                    String str = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(str)) {
                        flag = false;
                        socketOutPut.println("bye");
                    } else {
                        System.out.println(str);
                        socketOutPut.println("length: " + str.length());
                    }

                } while (flag);

                socketInput.close();
                socketOutPut.close();
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Client exit" + socket.getInetAddress() + "port " + socket.getPort());

            }

        }
    }
}
