package Strategy;

import Factory.ResponseMessageFactory;
import Server.ClientHandler;
import Singleton.GroupManager;
import Singleton.UserManager;
import com.alibaba.fastjson.JSON;
import dao.MessageModel;
import dao.ResponseModel;

public class SendStrategy implements MessageStrategy {

    public ResponseModel handleMessage(MessageModel message, ClientHandler handler) {

        if (message.isBroadcast()) {
            String users = GroupManager.getInstance().getGroupsUsers(message.getTo());
            System.out.println(users);
            if (users == null) {
                return ResponseMessageFactory.sendMessageFail();
            }
            String[] split = users.split(",");
            for (int i = 0; i < split.length; i++) {
                send(UserManager.getInstance().getHandler(split[i]), JSON.toJSONString(message));
            }
            return ResponseMessageFactory.sendMessageSuccess();
        } else {
            ClientHandler targetHandler = UserManager.getInstance().getHandler(message.getTo());
            if (send(targetHandler, JSON.toJSONString(message))) {
                return ResponseMessageFactory.sendMessageSuccess();
            } else {
                return ResponseMessageFactory.sendMessageFail();
            }
        }
    }

    private boolean send(ClientHandler targetHandler, String info) {
        if (targetHandler != null) {
            targetHandler.send(info);
            return true;
        }
        return false;
    }
}
