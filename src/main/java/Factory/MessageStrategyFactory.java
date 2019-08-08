package Factory;

import Strategy.LoginStrategy;
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
        MESSAGE_STRATEGY_MAP.put(MessageKey.login, new LoginStrategy());

    }

    public static MessageStrategy getStrategy(String messageKey) {
        return MESSAGE_STRATEGY_MAP.get(messageKey);

    }

    private MessageStrategyFactory() {

    }

    private interface MessageKey{
        String register = "register";
        String send = "send";
        String login = "login";

    }
}
