package Strategy;

import Server.ClientHandler;
import dao.MessageModel;
import dao.ResponseModel;

public interface MessageStrategy {
    public ResponseModel handleMessage(MessageModel string, ClientHandler handler);
}
