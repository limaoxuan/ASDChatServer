package Strategy;

import Factory.ResponseMessageFactory;
import Server.ClientHandler;
import Singleton.GroupManager;
import dao.MessageModel;
import dao.ResponseModel;
import dataaccess.DataAccessFacade;

public class GroupStrategy implements MessageStrategy {
    public ResponseModel handleMessage(MessageModel messageModel, ClientHandler handler) {

        boolean success = GroupManager.getInstance().addGroup(messageModel.getFrom(), messageModel.getPayload());
        if (success) {
            return ResponseMessageFactory.addGroupSuccess();
        }

        return ResponseMessageFactory.addGroupFail();
    }
}
