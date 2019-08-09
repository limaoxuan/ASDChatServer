package Client;

import com.alibaba.fastjson.JSON;
import dao.MessageModel;
import utility.CloseUtils;

import java.io.*;
import java.net.*;

public class TCPClient {

    private int port;
    private int local_port;
    private String ip;
    private Socket socket;

    public TCPClient(int localPort, int port, String ip) throws IOException {
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
            //接收数据
            ReadHandler readHandler = new ReadHandler(socket.getInputStream());
            readHandler.start();
            // 发送数据
            write(socket);

            readHandler.exit();

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



    private static void write(Socket client) throws IOException {
        //构建键盘输入
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        //得到Socket输出流，并转换为打印流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        do {
            //键盘读取一行
            String str = input.readLine();
            //发送到服务器
            if (str.equalsIgnoreCase("register")) {
                MessageModel testMessage = new MessageModel("register", "", "123456", true, "");
                String message = JSON.toJSONString(testMessage);
                socketPrintStream.println(message);
            } else if (str.equalsIgnoreCase("send")) {
                MessageModel testMessage = new MessageModel("send", "234567", "123456", false, "hello");
                String message = JSON.toJSONString(testMessage);
                socketPrintStream.println(message);

            }
            if (str.equalsIgnoreCase("login")) {
                MessageModel testMessage = new MessageModel("login", "", "123456", true, "");
                String message = JSON.toJSONString(testMessage);
                socketPrintStream.println(message);
            }

            if (str.equalsIgnoreCase("login1")) {
                MessageModel testMessage = new MessageModel("login", "", "234567", true, "");
                String message = JSON.toJSONString(testMessage);
                socketPrintStream.println(message);
            }

            if (str.equalsIgnoreCase("register1")) {
                MessageModel testMessage = new MessageModel("register", "", "234567", false, "");
                String message = JSON.toJSONString(testMessage);
                socketPrintStream.println(message);
            }

            else if (str.equalsIgnoreCase("send1")) {
                MessageModel testMessage = new MessageModel("send", "123456", "234567", false, "hello1");
                String message = JSON.toJSONString(testMessage);
                socketPrintStream.println(message);
            }
            else if (str.equalsIgnoreCase("sendAll")) {
                MessageModel testMessage = new MessageModel("send", "hello", "234567", true, "hello1");
                String message = JSON.toJSONString(testMessage);
                socketPrintStream.println(message);
            }
            else if (str.equalsIgnoreCase("Group")) {
                MessageModel testMessage = new MessageModel("group", "", "hello", false, "123456,234567");
                String message = JSON.toJSONString(testMessage);
                socketPrintStream.println(message);
            }


            if ("00bye00".equalsIgnoreCase(str)) {
                break;
            }
        } while (true);

        //释放资源
        socketPrintStream.close();
    }

    static class ReadHandler extends Thread {
        private boolean done = false;
        private final InputStream inputStream;

        ReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }


        @Override
        public void run() {
            super.run();

            try {
                //得到打印流，用于数据输出，服务器回送数据使用
//                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                //得到输入流，用于接收数据
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(inputStream));

                do {
                    String str;
                    try {
                        //客户端拿到数据
                        str = socketInput.readLine();
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                    if (str == null) {
                        System.out.println("连接已关闭，无法读取数据～");
                        break;
                    }
                    //打印到屏幕
                    System.out.println(str);


                } while (!done);


            } catch (Exception e) {
                if (!done) {
                    System.out.println("连接异常断开:" + e.getMessage());
                }
            } finally {
                CloseUtils.close(inputStream);
            }
        }

        void exit() {
            done = true;
            CloseUtils.close(inputStream);
        }
    }
}
