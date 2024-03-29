package COR;

import Factory.ResponseMessageFactory;
import Server.ClientHandler;
import com.alibaba.fastjson.JSON;
import dao.ResponseModel;

public class JSONHandler extends AbstractHandler {
    public String handleRequest(String str, ClientHandler clientHandler) {
        if (JSON.isValid(str)) {
            return nextHandler.handleRequest(str, clientHandler);
        }

        return JSON.toJSONString(ResponseMessageFactory.invalidJSON()) ;
    }
}
