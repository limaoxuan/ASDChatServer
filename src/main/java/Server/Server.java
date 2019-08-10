package Server;

import utility.GetSystemSetting;
import utility.PropertyInterface;

import java.io.*;
import java.util.Properties;

public class Server {
//    private static final int PORT = 20000;

    public static void main(String[] args) throws IOException {

        TCPServer tcpServer = new TCPServer((Integer.parseInt(GetSystemSetting.getValueFromSetting(PropertyInterface.port))));
        tcpServer.start();
    }


}
