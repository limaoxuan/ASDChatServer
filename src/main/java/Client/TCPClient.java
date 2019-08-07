package Client;

import com.alibaba.fastjson.JSON;
import dao.MessageModel;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class TCPClient {

    private int port;
    private int local_port;
    private String  ip;
    private Socket socket;

    public TCPClient(int localPort, int port,String ip) throws IOException {
        this.port = port;
        this.local_port = localPort;
        this.ip = ip;
        socket = createSocket();
        initSocket(socket);
    }


    public void start() throws IOException {
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), this.port), 3000);
        System.out.println("已发起服务器连接，并进入后续流程～");
        System.out.println("客户端信息：" + socket.getLocalAddress() + " P:" + socket.getLocalPort());
        System.out.println("服务器信息：" + socket.getInetAddress() + " P:" + socket.getPort());
        try {
            // 发送接收数据
            todo(socket);
        } catch (Exception e) {
            System.out.println("异常关闭");
        }

        // 释放资源
        socket.close();
        System.out.println("客户端已退出～");
    }

    public void getClient() {

    }

    private void initSocket(Socket socket) throws SocketException {
        // load time
        socket.setSoTimeout(3000);

        socket.setReuseAddress(true);

        socket.setTcpNoDelay(true);

        // 2 minus
        socket.setKeepAlive(true);

//        socket.setOOBInline(false);

        socket.setReceiveBufferSize(64 * 1024 * 1024);
        socket.setSendBufferSize(64 * 1024 * 1024);


        socket.setPerformancePreferences(1, 1, 1);


    }

    private Socket createSocket() throws IOException {

        Socket socket = new Socket();
        socket.bind(new InetSocketAddress(Inet4Address.getLocalHost(), this.local_port));
        return socket;
    }

    private void todo(Socket client) throws IOException {
        // 构建键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));


        // 得到Socket输出流，并转换为打印流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);


        // 得到Socket输入流，并转换为BufferedReader
        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        boolean flag = true;
        do {
            // 键盘读取一行
            String str = input.readLine();


            if (str.equalsIgnoreCase("register")) {
                MessageModel testMessage = new MessageModel("register", "", "123456", '0', "");
                String message = JSON.toJSONString(testMessage);
                socketPrintStream.println(message);
            } else if (str.equalsIgnoreCase("send")) {

                    MessageModel testMessage = new MessageModel("send", "", "123456", '0', "");
                    String message = JSON.toJSONString(testMessage);
                    socketPrintStream.println(message);

            } else {
                socketPrintStream.println(str);
            }


            // 从服务器读取一行
            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        } while (flag);

        // 资源释放
        socketPrintStream.close();
        socketBufferedReader.close();

    }
}
