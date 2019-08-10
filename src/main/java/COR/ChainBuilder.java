package COR;


import Business.MessageHandler;

public class ChainBuilder {
    private AbstractHandler abstractHandler;

    public ChainBuilder() {
        buildChain();
    }

    private void buildChain() {
        AbstractHandler jsonHandler = new JSONHandler();
        AbstractHandler messageHandler = new MessageHandler();
        jsonHandler.nextHandler = messageHandler;
        abstractHandler = jsonHandler;
    }
    public AbstractHandler getAbstractHandler() {
        return abstractHandler;
    }

}
