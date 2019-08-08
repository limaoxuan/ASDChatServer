package COR;

import Server.ClientHandler;
import dao.ResponseModel;

public abstract class AbstractHandler {
    protected AbstractHandler nextHandler;
    public abstract ResponseModel handleRequest(String str, ClientHandler clientHandler);
}
