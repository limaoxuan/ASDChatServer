package Strategy;

import Server.ClientHandler;
import Singleton.GroupManager;
import dao.MessageModel;
import dao.ResponseModel;

public class GroupListStrategy implements MessageStrategy {

    public ResponseModel handleMessage(MessageModel string, ClientHandler handler) {
        return new ResponseModel(true, GroupManager.getInstance().getGroups());
    }
}
