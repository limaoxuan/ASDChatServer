package Business;

import COR.AbstractHandler;
import Factory.MessageStrategyFactory;
import Server.ClientHandler;
import Strategy.MessageStrategy;
import com.alibaba.fastjson.JSON;
import dao.MessageModel;

public class MessageHandler extends AbstractHandler {
    public String handleRequest(String str, ClientHandler clientHandler) {
        MessageModel messageModel = JSON.parseObject(str, MessageModel.class);
        MessageStrategy strategy = MessageStrategyFactory.getStrategy(messageModel.getCmd());
        return JSON.toJSONString(strategy.handleMessage(messageModel, clientHandler));
    }
}
