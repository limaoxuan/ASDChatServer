import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3000);

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
