package Strategy;

import Factory.ResponseMessageFactory;
import Server.ClientHandler;
import Singleton.UserManager;
import com.alibaba.fastjson.JSON;
import dao.MessageModel;
import dao.ResponseModel;
import dao.User;
import dataaccess.DataAccessFacade;

public class RegisterStrategy implements MessageStrategy {
    public ResponseModel handleMessage(MessageModel model, ClientHandler handler) {
        boolean success = new DataAccessFacade().addUser(new User(model.getFrom(), model.getPayload()));
        if (success) {
            UserManager.getInstance().addCurrentClientHandle(model.getFrom(), handler);
            return ResponseMessageFactory.registerUserSuccess();
        }
        return ResponseMessageFactory.registerUserFail();
    }
}
