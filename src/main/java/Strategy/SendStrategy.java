package Strategy;

import Factory.ResponseMessageFactory;
import Server.ClientHandler;
import Singleton.UserManager;
import dao.MessageModel;
import dao.ResponseModel;

public class SendStrategy implements MessageStrategy {

    public ResponseModel handleMessage(MessageModel message, ClientHandler handler) {

        ClientHandler targetHandler = UserManager.getInstance().getHandler(message.getTo());

        if (targetHandler != null) {
            targetHandler.send(message.getPayload());
            return ResponseMessageFactory.sendMessageSuccess();
        }
        return ResponseMessageFactory.sendMessageFail();
    }
}
