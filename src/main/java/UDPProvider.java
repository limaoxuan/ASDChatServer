import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.UUID;

public class UDPProvider {
    public static void main(String[] args) throws IOException {
        String tag = UUID.randomUUID().toString();
        Provider provider = new Provider(tag);
        provider.start();

        System.in.read();
        provider.exit();
    }

    private static class Provider extends Thread {
        private final String tag;
        private boolean done = false;
        private DatagramSocket ds = null;

        public Provider(String tag) {
            super();
            this.tag = tag;

        }


        @Override
        public void run() {
            super.run();

            System.out.println("UDPProvider Started");

            // provider(receiver) must set port
            try {
                ds = new DatagramSocket(20000);
                while (!done) {
                    final byte[] buf = new byte[512];

                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);


                    // receive data
                    ds.receive(receivePack);

                    // print receive data
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int dataLen = receivePack.getLength();
                    String data = new String(receivePack.getData(), 0, dataLen);

                    System.out.println("UDPProvider receive form ip: " +
                            "" + ip + " port: " + port + " data: " + data
                    );

                    int responsePort = MessageBuilder.parsePort(data);
                    if (responsePort != -1) {
                        // recall data
                        String reponseData = MessageBuilder.buildWithTAG(tag);

                        byte[] reponseDataBytes = reponseData.getBytes();
                        // according to sender ip port repose to sender
                        DatagramPacket responsePacket = new DatagramPacket(reponseDataBytes,
                                reponseDataBytes.length, receivePack.getAddress(), responsePort);

                        // finish
                        ds.send(responsePacket);


                    }


                }
            } catch (Exception e) {

            } finally {
                close();
            }

            System.out.println("UDPProvider finished");
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        void exit() {
            done = true;
            close();
        }
    }
}
