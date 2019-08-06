import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();

        socket.setSoTimeout(3000);

        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 3000), 3000);

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
