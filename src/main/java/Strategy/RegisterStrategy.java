package Strategy;

import Server.ClientHandler;
import com.alibaba.fastjson.JSON;
import dao.MessageModel;
import dao.ReponseModel;
import dao.User;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class RegisterStrategy implements MessageStrategy {
    public String handleMessage(MessageModel obj, ClientHandler handler) {
        new DataAccessFacade().addUser(new User(obj.getFrom()));
        ReponseModel register = new ReponseModel(true, "register success");
        String message = JSON.toJSONString(register);
        return message;
    }
}
