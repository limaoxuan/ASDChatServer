package Factory;

import dao.ResponseModel;

public class ResponseMessageFactory {

    public static ResponseModel registerUserFail() {
        return responseMessage(false, "register failure");
    }

    public static ResponseModel registerUserSuccess() {
        return responseMessage(true, "register success");
    }

    public static ResponseModel sendMessageSuccess() {
        return responseMessage(true, "send success");
    }

    public static ResponseModel sendMessageFail() {
        return responseMessage(false, "send failure");
    }

    public static ResponseModel loginSuccess() {
        return responseMessage(true, "login success");
    }

    public static ResponseModel loginFail() {
        return responseMessage(false, "login failure");
    }


    public static ResponseModel invalidJSON() {
        return new ResponseModel(false, "Invalid JSON");
    }

    public static ResponseModel responseMessage(boolean success, String message) {
        return new ResponseModel(success, message);
    }

    private ResponseMessageFactory() {
    }
}
