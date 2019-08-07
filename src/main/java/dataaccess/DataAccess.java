package dataaccess;

import dao.User;

import java.util.HashMap;

public interface DataAccess {
    public HashMap<String , User> readUserList();
    public void addUser(User user);

}
