package Strategy;

import Server.ClientHandler;
import dao.MessageModel;

public class SendStrategy implements MessageStrategy{
    public String handleMessage(MessageModel message, ClientHandler handler) {


        return "send";
    }
}
