package Client;

import java.io.IOException;

public class Client {
    private static  int PORT = 20000;
    private static  int LOCAL_PORT = 20001;

    public static void main(String[] args) {
        try {
            TCPClient tcpClient = new TCPClient(LOCAL_PORT, PORT, "");
            tcpClient.start();

        } catch (IOException e) {

        }
    }
}
