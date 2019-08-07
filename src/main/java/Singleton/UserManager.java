package Singleton;

import dao.User;
import dataaccess.DataAccessFacade;

import java.util.HashMap;

public class UserManager {
    private HashMap<String, User> currentUser = new HashMap<String, User>();
    private UserManager userManager;

    private UserManager() {

    }

    public HashMap<String, User> allUser() {
        return new DataAccessFacade().readUserList();
    }

    private UserManager getInstance() {
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
