package Buiness;


import COR.AbChainBuilderTemplate;
import COR.AbstractHandler;
import COR.JSONHandler;

public class MyChainBuilder extends AbChainBuilderTemplate {
    @Override
    public AbstractHandler buildChain() {
        AbstractHandler jsonHandler = new JSONHandler();
        AbstractHandler messageHandler = new MessageHandler();
        jsonHandler.nextHandler = messageHandler;
        return jsonHandler;
    }

}
