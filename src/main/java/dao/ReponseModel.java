package dao;

public class ReponseModel {
    private boolean success;
    private String payload;

    public ReponseModel(boolean success, String payload) {
        this.success = success;
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
