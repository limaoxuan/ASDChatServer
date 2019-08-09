package Singleton;

import Factory.ResponseMessageFactory;
import Server.ClientHandler;
import dao.ResponseModel;
import dao.User;
import dataaccess.DataAccessFacade;

import java.util.HashMap;

public class UserManager {
    //    private HashMap<String, User> currentUser = new HashMap<String, User>();
    private HashMap<String, ClientHandler> userHandler = new HashMap<String, ClientHandler>();
    private HashMap<ClientHandler, String> handlerUser = new HashMap<ClientHandler, String>();

    private static UserManager userManager;

    private UserManager() {

    }



    public boolean checkLoginUser(String username, String password) {
        return new DataAccessFacade().checkLoginUser(new User(username, password));
    }

    public boolean addUser(String username, String password) {
        return  new DataAccessFacade().addUser(new User(username, password));
    }

    public void removeAll() {
        for (ClientHandler client : handlerUser.keySet()) {
            client.exit();
        }
        userHandler.clear();
        handlerUser.clear();
    }

    public void remove(ClientHandler clientHandler) {
        String username = handlerUser.get(clientHandler);
        if (username != null) {
            userHandler.remove(username);
            handlerUser.remove(clientHandler);
        }
    }

    public ResponseModel getCurrentUser() {
        StringBuffer res = new StringBuffer();
        for (String username : userHandler.keySet()) {
            res.append(username + ",");
        }
        res.deleteCharAt(res.length() - 1);
        return ResponseMessageFactory.responseMessage(true,res.toString());
    }


    public void addCurrentClientHandle(String str, ClientHandler clientHandler) {
        userHandler.put(str, clientHandler);
        handlerUser.put(clientHandler, str);

    }

    public ClientHandler getHandler(String username) {
        return userHandler.get(username);
    }

    public HashMap<String, User> allUser() {
        return new DataAccessFacade().readUserList();
    }


    public static UserManager getInstance() {
        if (userManager == null) {
            synchronized (UserManager.class) {
                if (userManager == null) {
                    // intra-thread semantics
                    // create memory
                    // reference and init can change the sequence
                    // so we can define this instance as volatile which mean no intra-thread semantics
                    userManager = new UserManager();
                }
            }
        }
        return userManager;
    }


}
