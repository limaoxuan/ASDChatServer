package dataaccess;

import dao.User;

import java.util.HashMap;

public interface DataAccess {
    public HashMap<String, User> readUserList();

    public boolean addUser(User user);

    public boolean checkLoginUser(User user);

    public boolean addGroup(String teamName, String members);

    public HashMap<String, String> readGroupList();

    public String getGroupUsers(String groupName);

}
