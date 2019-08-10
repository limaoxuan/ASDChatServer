package dao;

public class MessageModel {
    private String cmd;
    private String to;
    private String from;
    private boolean broadcast;
    private String payload;

    public MessageModel(String cmd, String to, String from, boolean broadcast, String payload) {
        this.cmd = cmd;
        this.to = to;
        this.from = from;
        this.broadcast = broadcast;
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


    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
