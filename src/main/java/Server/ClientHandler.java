package Server;

import COR.AbChainBuilderTemplate;
import Business.MyChainBuilder;
import utility.CloseUtils;
import utility.GetSystemSetting;
import utility.PropertyInterface;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler extends Thread {

    private final Socket socket;
    private final ClientReadHandler readHandler;
    private final ClientWriteHandler writeHandler;
    private final CloseNotify closeNotify;


    public ClientHandler(Socket socket, CloseNotify closeNotify) throws IOException {
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

    public interface CloseNotify {
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
                try {
                    System.out.println(msg);
                    ClientWriteHandler.this.printStream.println(msg);
                } catch (Exception e) {
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
                //get socketOutput response data to client
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                //get socketInput receive data
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(inputStream));

                do {
                    //client get data
                    String str = socketInput.readLine();
                    System.out.println(str);
                    if (str == null) {
                        System.out.println("Client can't load data");
                        //exit client
                        ClientHandler.this.exitByYourself();
                        break;
                    }
//                    Class.forName(")
//                    System.out.println(new MyChainBuilder().getClass().getName());
//                    System.out.println(GetSystemSetting.getValueFromSetting(PropertyInterface.rootBuilder));
                    AbChainBuilderTemplate chainBuilder = (AbChainBuilderTemplate) Class.forName(GetSystemSetting.getValueFromSetting(PropertyInterface.rootBuilder)).newInstance();
//                    AbChainBuilderTemplate chainBuilder = new MyChainBuilder();
                    chainBuilder.build();
                    socketOutput.println(chainBuilder.getAbstractHandler().handleRequest(str, ClientHandler.this));
                } while (!done);


            } catch (Exception e) {
                if (!done) {
                    System.out.println("connect error close~");
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
