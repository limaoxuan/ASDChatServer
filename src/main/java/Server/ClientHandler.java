package Server;

import Factory.MessageStrategyFactory;
import Singleton.UserManager;
import Strategy.MessageStrategy;
import com.alibaba.fastjson.JSON;
import dao.MessageModel;
import dao.ReponseModel;
import utility.CloseUtils;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler extends Thread {

    private final Socket socket;
    private final ClientReadHandler readHandler;
    private final ClientWriteHandler writeHandler;
    private final CloseNotify closeNotify;


    public ClientHandler(Socket socket,CloseNotify closeNotify) throws IOException {
        this.socket = socket;
        this.readHandler = new ClientReadHandler(socket.getInputStream());
        this.writeHandler = new ClientWriteHandler(socket.getOutputStream());
        this.closeNotify = closeNotify;
        System.out.println("new Client " + socket.getInetAddress() + "port " + socket.getPort());

    }

    public void exit() {
        readHandler.exit();
        writeHandler.exit();
        CloseUtils.close(socket);
        System.out.println("Client exit" + socket.getInetAddress() + "port " + socket.getPort());

    }

    public void send(String str) {
        writeHandler.send(str);
    }

    public void readToPrint() {
        readHandler.start();
    }



    private void exitByYourself() {
        exit();
        closeNotify.onSelfClosed(this);
    }

    public interface CloseNotify{
        void onSelfClosed(ClientHandler handler);
    }

    class ClientWriteHandler {
        private boolean done = false;
        private final PrintStream printStream;
        private final ExecutorService executorService;

        ClientWriteHandler(OutputStream outputStream) {
            this.printStream = new PrintStream(outputStream);
            this.executorService = Executors.newSingleThreadExecutor();
        }


        public void exit() {
            done = true;
            CloseUtils.close(printStream);
            executorService.shutdownNow();
        }

        public void send(String str) {
            executorService.execute(new WriteRunnable(str));
        }

        class WriteRunnable implements Runnable {
            private final String msg;

            WriteRunnable(String msg) {
                this.msg = msg;
            }

            public void run() {
                if (ClientWriteHandler.this.done) {
                    return;
                }
                try{
                    System.out.println(msg);
                    ClientWriteHandler.this.printStream.println(msg);
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    class ClientReadHandler extends Thread {
        private boolean done = false;
        private final InputStream inputStream;

        ClientReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }


        @Override
        public void run() {
            super.run();

            try {
                //得到打印流，用于数据输出，服务器回送数据使用
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                //得到输入流，用于接收数据
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(inputStream));

                do {
                    //客户端拿到数据
                    String str = socketInput.readLine();

                    if (str == null) {
                        System.out.println("客户端已无法读取数据");
                        //退出客户端
                        ClientHandler.this.exitByYourself();
                        break;
                    }

                    //打印到屏幕
                    System.out.println(str);
//                    MessageModel
                    if (JSON.isValid(str)) {
                        MessageModel messageModel = JSON.parseObject(str,MessageModel.class);
                        MessageStrategy strategy = MessageStrategyFactory.getStrategy(messageModel.getCmd());
                        String s = strategy.handleMessage(messageModel, ClientHandler.this);
                        socketOutput.println(s);
                    }


                } while (!done);


            } catch (Exception e) {
                if (!done) {
                    System.out.println("连接异常断开～");
                    ClientHandler.this.exitByYourself();
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
