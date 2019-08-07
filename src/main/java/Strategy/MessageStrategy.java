package Strategy;

import Server.ClientHandler;
import dao.MessageModel;

public interface MessageStrategy {
    public String handleMessage(MessageModel string, ClientHandler handler);
}
