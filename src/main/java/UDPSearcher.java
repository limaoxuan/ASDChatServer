import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class UDPSearcher {
    private static final int LISTEN_PORT = 30000;


    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("UDPSearcher Started");
        final Listener listen = listen();
        sendBroadcast();


        System.in.read();
        List<Device> devices = listen.getDevicesAndClose();
        System.out.println(devices.size());
        for (Device device : devices) {
            System.out.println(device.toString());
        }
    }
    private static Listener listen() throws InterruptedException {
        System.out.println("UDPSearcher start listen");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT, countDownLatch);
        listener.start();
        countDownLatch.await();
        return listener;
    }

    private static void sendBroadcast() throws IOException {
        System.out.println("UDPSearcher sendBroadcast Started");


        // search don't assign a port
        DatagramSocket ds = new DatagramSocket();


        // send message
        String requestData = MessageBuilder.buildWithPort(LISTEN_PORT);

        byte[] requestDataBytes = requestData.getBytes();
        // after set target address
        DatagramPacket requestPacket = new DatagramPacket(requestDataBytes,
                requestDataBytes.length);
        requestPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        requestPacket.setPort(20000);
        // finish
        ds.send(requestPacket);


        System.out.println("UDPSearcher finished");
        ds.close();

    }



    private static class Device {
        final int port;
        final String ip;
        final String tag;

        public Device(int port, String ip, String tag) {
            this.port = port;
            this.ip = ip;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return "Device{" +
                    "port=" + port +
                    ", ip='" + ip + '\'' +
                    ", tag='" + tag + '\'' +
                    '}';
        }
    }

    private static class Listener extends Thread {
        private final int listenPort;
        private final CountDownLatch countDownLatch;
        private final List<Device> devices = new ArrayList<Device>();
        private boolean done = false;
        private DatagramSocket ds = null;


        public Listener(int listenPort, CountDownLatch countDownLatch) {
            super();
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }


        @Override
        public void run() {
            super.run();

            countDownLatch.countDown();

            try {
                ds = new DatagramSocket(listenPort);
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

                    System.out.println("UDPSearcher receive form ip: " +
                            "" + ip + " port: " + port + " data: " + data
                    );
                    String tag = MessageBuilder.parseTAG(data);
                    System.out.println(tag);
                    if (tag != null) {
                        Device device = new Device(port, ip, tag);
                        devices.add(device);

                    }


                }
            } catch (Exception e) {

            } finally {
                close();

            }
            System.out.println("UDPSearcher listener finished");

        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        List<Device> getDevicesAndClose() {
            done = true;
            close();
            return devices;
        }
    }
}
