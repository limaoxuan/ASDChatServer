package dao;

public class TestMessage {
    private String cmd;
    private String to;
    private String from;
    private int isBroadcast;
    private String payLoad;

    public TestMessage(String cmd, String to, String from, int isBroadcast, String payLoad) {
        this.cmd = cmd;
        this.to = to;
        this.from = from;
        this.isBroadcast = isBroadcast;
        this.payLoad = payLoad;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getIsBroadcast() {
        return isBroadcast;
    }

    public void setIsBroadcast(int isBroadcast) {
        this.isBroadcast = isBroadcast;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }
}
