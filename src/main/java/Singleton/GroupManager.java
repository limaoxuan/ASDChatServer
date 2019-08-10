package Singleton;

import Server.ClientHandler;
import dataaccess.DataAccessFacade;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupManager {
    private HashMap<String, String> teamsMap = new HashMap<String, String>();

    private static GroupManager groupManager;

    private GroupManager() {

    }


    public boolean addGroup(String name, String members) {
        return new DataAccessFacade().addGroup(name, members);
    }

    public String getGroupsUsers(String group) {
//        HashMap<String, String> map = new DataAccessFacade().readGroupList();
        ;
        return new DataAccessFacade().getGroupUsers(group);
    }

    public String getGroups() {
        HashMap<String, String> mapMap = new DataAccessFacade().readGroupList();
        ArrayList<String> list = new ArrayList<>();
        for (String str : mapMap.keySet()) {
            list.add(str);
        }
        return String.join(",",list);
    }


    public static GroupManager getInstance() {
        if (groupManager == null) {
            synchronized (GroupManager.class) {
                if (groupManager == null) {
                    // intra-thread semantics
                    // create memory
                    // reference and init can change the sequence
                    // so we can define this instance as volatile which mean no intra-thread semantics
                    groupManager = new GroupManager();
                }
            }
        }
        return groupManager;
    }

}
