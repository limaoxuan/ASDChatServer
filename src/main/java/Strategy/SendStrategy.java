package Strategy;

import Server.ClientHandler;
import Singleton.UserManager;
import com.alibaba.fastjson.JSON;
import dao.MessageModel;
import dao.ReponseModel;

public class SendStrategy implements MessageStrategy{
    public String handleMessage(MessageModel message, ClientHandler handler) {

//        UserManager.getInstance().printUserHandler();
        ClientHandler handler1 = UserManager.getInstance().getHandler(message.getTo());

        if (handler1 != null) {
            handler1.send(message.getPayload());
            ReponseModel register = new ReponseModel(true, "send success");
            String res = JSON.toJSONString(register);

            return res;
         } else {
            ReponseModel register = new ReponseModel(false, "user not fount");
            String res = JSON.toJSONString(register);

            return res;
        }


    }
}
