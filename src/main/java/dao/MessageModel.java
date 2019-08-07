package dao;

public class MessageModel {
    private String cmd;
    private String to;
    private String from;
    private boolean isBroadcast;
    private String payload;

    public MessageModel(String cmd, String to, String from, boolean isBroadcast, String payload) {
        this.cmd = cmd;
        this.to = to;
        this.from = from;
        this.isBroadcast = isBroadcast;
        this.payload = payload;
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

    public boolean getIsBroadcast() {
        return isBroadcast;
    }

    public void setIsBroadcast(boolean isBroadcast) {
        this.isBroadcast = isBroadcast;
    }

    public boolean isBroadcast() {
        return isBroadcast;
    }

    public void setBroadcast(boolean broadcast) {
        isBroadcast = broadcast;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
