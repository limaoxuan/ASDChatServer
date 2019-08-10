package Factory;

import Strategy.*;

import java.util.HashMap;
import java.util.Map;

public class MessageStrategyFactory {
    private static Map<String, MessageStrategy> MESSAGE_STRATEGY_MAP = new HashMap<String, MessageStrategy>();


    static {
        MESSAGE_STRATEGY_MAP.put(MessageKey.register, new RegisterStrategy());
        MESSAGE_STRATEGY_MAP.put(MessageKey.send, new SendStrategy());
        MESSAGE_STRATEGY_MAP.put(MessageKey.login, new LoginStrategy());
        MESSAGE_STRATEGY_MAP.put(MessageKey.userList, new UserListStrategy());
        MESSAGE_STRATEGY_MAP.put(MessageKey.group, new GroupStrategy());
        MESSAGE_STRATEGY_MAP.put(MessageKey.groupList, new GroupListStrategy());

    }

    public static MessageStrategy getStrategy(String messageKey) {

        MessageStrategy messageStrategy = MESSAGE_STRATEGY_MAP.get(messageKey);
        return  messageStrategy != null ? messageStrategy : new CommonStrategy();

    }

    private MessageStrategyFactory() {

    }

    private interface MessageKey {
        String register = "register";
        String send = "send";
        String login = "login";
        String userList = "userList";
        String group = "group";
        String groupList = "groupList";

    }
}
