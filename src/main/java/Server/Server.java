package Server;

import java.io.*;

public class Server {
    private static final int PORT = 20000;

    public static void main(String[] args) throws IOException {
        TCPServer tcpServer = new TCPServer(PORT);
        tcpServer.start();
    }








}
