package Factory;

import Strategy.MessageStrategy;
import Strategy.RegisterStrategy;
import Strategy.SendStrategy;

import java.util.HashMap;
import java.util.Map;

public class MessageStrategyFactory {
    private static Map<String, MessageStrategy> MESSAGE_STRATEGY_MAP = new HashMap<String, MessageStrategy>();


    static {
        MESSAGE_STRATEGY_MAP.put(MessageKey.register, new RegisterStrategy());
        MESSAGE_STRATEGY_MAP.put(MessageKey.send, new SendStrategy());

    }

    public static MessageStrategy getStrategy(String messageKey) {

        MessageStrategy messageStrategy = MESSAGE_STRATEGY_MAP.get(messageKey);
        return messageStrategy;

    }

    private MessageStrategyFactory() {

    }

    private interface MessageKey{
        String register = "register";
        String send = "send";

    }
}
