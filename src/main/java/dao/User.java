package dao;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 5147265048973262104L;


    private String username;

    public User(String username) {

        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
