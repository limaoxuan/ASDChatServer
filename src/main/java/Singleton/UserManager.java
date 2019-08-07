package Singleton;

import Server.ClientHandler;
import dao.User;
import dataaccess.DataAccessFacade;

import java.util.HashMap;

public class UserManager {
//    private HashMap<String, User> currentUser = new HashMap<String, User>();
    private final HashMap<String, ClientHandler> userHandler = new HashMap<String, ClientHandler>();

    private static UserManager userManager;

    private UserManager() {

    }

    public ClientHandler getHandler(String username) {
        return userHandler.get(username);
    }

    public HashMap<String, User> allUser() {
        return new DataAccessFacade().readUserList();
    }



    public static  UserManager getInstance() {
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
