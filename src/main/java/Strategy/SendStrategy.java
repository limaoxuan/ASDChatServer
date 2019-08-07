package Strategy;

import Server.ClientHandler;
import dao.TestMessage;

public class SendStrategy implements MessageStrategy{
    public String handleMessage(TestMessage message, ClientHandler handler) {


        return "send";
    }
}
