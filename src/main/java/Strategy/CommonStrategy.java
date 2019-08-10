package Strategy;

import Factory.MessageStrategyFactory;
import Server.ClientHandler;
import dao.MessageModel;
import dao.ResponseModel;

public class CommonStrategy implements MessageStrategy {
    @Override
    public ResponseModel handleMessage(MessageModel string, ClientHandler handler) {
        return new ResponseModel(true,"unknown command");
    }
}
