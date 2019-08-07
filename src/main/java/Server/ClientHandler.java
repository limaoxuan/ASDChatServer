package Server;

import Factory.MessageStrategyFactory;
import Strategy.MessageStrategy;
import com.alibaba.fastjson.JSON;
import dao.MessageModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private boolean flag = true;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public  void send(String str) {
        try {
            PrintStream socketOutput = new PrintStream(socket.getOutputStream());
            socketOutput.println(str);
        } catch (Exception e) {

        }

    }


    public void exit() {
        flag = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        System.out.println("new Client " + socket.getInetAddress() + "port " + socket.getPort());
        try {
            PrintStream socketOutput = new PrintStream(socket.getOutputStream());

            BufferedReader socketInput = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            do {
                String str = socketInput.readLine();
                System.out.println(str);
                if (JSON.isValid(str)) {
                    MessageModel testMessage = JSON.parseObject(str, MessageModel.class);

                    MessageStrategy messageStrategy = MessageStrategyFactory.getStrategy(testMessage.getCmd());
                    if (messageStrategy != null) {
                        String result = messageStrategy.handleMessage(testMessage, this);
                        System.out.println(result);
                        socketOutput.println(result);
                    } else {
                        socketOutput.println("wrong command");
                    }
                } else {
                    socketOutput.println("wrong command");
                }



            } while (flag);


            socketOutput.close();
            socketInput.close();
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
