package Strategy;

import Server.ClientHandler;
import Singleton.UserManager;
import dao.MessageModel;
import dao.ResponseModel;

public class UserListStrategy implements MessageStrategy{
    public ResponseModel handleMessage(MessageModel messageModel, ClientHandler handler) {
        return UserManager.getInstance().getCurrentUser();
    }
}
