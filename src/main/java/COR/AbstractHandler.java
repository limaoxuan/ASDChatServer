package COR;

import Server.ClientHandler;
import dao.ResponseModel;

public abstract class AbstractHandler {
    public AbstractHandler nextHandler;
    public abstract String  handleRequest(String str, ClientHandler clientHandler);
}
