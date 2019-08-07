package Server;

import java.io.*;

public class Server {
    private static final int PORT = 20000;

//    private static HashMap<String, ClientHandle> userMap = new HashMap<String, ClientHandle>();
////    private static HashMap<String,List<String>> groupMap = new HashMap<String, ClientHandle>();


    public static void main(String[] args) throws IOException {



        TCPServer tcpServer = new TCPServer(PORT);


        tcpServer.start();


    }








}
