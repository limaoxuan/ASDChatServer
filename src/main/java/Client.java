import java.io.*;
import java.net.*;

public class Client {
    // server port
    private static final int PORT = 20000;
    private static final int LOCAL_PORT = 20001;


    private static Socket createSocket() throws IOException {
        // new blind connect sometimes we need to set more parameters without directly connect
//        Socket socket = new Socket("localhost",PORT,Inet4Address.getLocalHost(),LOCAL_PORT);

// this is good way
        Socket socket = new Socket();
//        bind 20001 port
        socket.bind(new InetSocketAddress(Inet4Address.getLocalHost(), LOCAL_PORT));
        return socket;
    }

    private static void initSocket(Socket socket) throws SocketException {
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

    public static void main(String[] args) throws IOException {
        Socket socket = createSocket();
        initSocket(socket);

        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), PORT), 3000);


        System.out.println("Connect success");
        System.out.println("Client:" + socket.getLocalAddress() + " port " + socket.getLocalPort());
        System.out.println("Server:" + socket.getInetAddress() + " port " + socket.getPort());

        try {
            sendMessage(socket);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error close");
        }
        socket.close();
        System.out.println("client exit");

    }

    private static void sendMessage(Socket client) throws IOException {
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        boolean flag = true;
        do {
            String str = input.readLine();
            socketPrintStream.println(str);


            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        } while (flag);

        socketPrintStream.close();
        socketBufferedReader.close();

    }
}
