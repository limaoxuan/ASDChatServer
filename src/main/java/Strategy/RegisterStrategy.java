package Strategy;

import Server.ClientHandler;
import dao.TestMessage;

public class RegisterStrategy implements MessageStrategy {
    public String handleMessage(TestMessage string, ClientHandler handler) {
        return "bye";
    }
}
