package COR;


public abstract class AbChainBuilderTemplate {
    private AbstractHandler abstractHandler;

    public final void build() {
        this.abstractHandler = buildChain();
    }

    public abstract AbstractHandler buildChain();

    public final AbstractHandler getAbstractHandler() {
        return abstractHandler;
    }

}
