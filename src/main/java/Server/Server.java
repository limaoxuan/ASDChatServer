package Server;

import Factory.MessageStrategyFactory;
import Strategy.MessageStrategy;
import com.alibaba.fastjson.JSON;
import dao.TestMessage;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private static final int PORT = 20000;

//    private static HashMap<String, ClientHandle> userMap = new HashMap<String, ClientHandle>();
////    private static HashMap<String,List<String>> groupMap = new HashMap<String, ClientHandle>();


    public static void main(String[] args) throws IOException {



        TCPServer tcpServer = new TCPServer(PORT);


        tcpServer.start();


    }








}
