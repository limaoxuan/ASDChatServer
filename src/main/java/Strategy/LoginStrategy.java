package Strategy;

import Factory.ResponseMessageFactory;
import Server.ClientHandler;
import Singleton.UserManager;
import dao.MessageModel;
import dao.ResponseModel;


public class LoginStrategy implements MessageStrategy {
    public ResponseModel handleMessage(MessageModel model, ClientHandler handler) {

        boolean success = UserManager.getInstance().checkLoginUser(model.getFrom(), model.getPayload());
        if (success) {
            UserManager.getInstance().addCurrentClientHandle(model.getFrom(), handler);
            return ResponseMessageFactory.loginSuccess();
        }
        return ResponseMessageFactory.loginFail();
    }
}
