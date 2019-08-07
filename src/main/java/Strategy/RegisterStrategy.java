package Strategy;

import Server.ClientHandler;
import dao.MessageModel;

public class RegisterStrategy implements MessageStrategy {
    public String handleMessage(MessageModel string, ClientHandler handler) {
        return "bye";
    }
}
