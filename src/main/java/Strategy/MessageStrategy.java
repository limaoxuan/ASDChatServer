package Strategy;

import Server.ClientHandler;
import dao.TestMessage;

public interface MessageStrategy {
    public String handleMessage(TestMessage string, ClientHandler handler);
}
