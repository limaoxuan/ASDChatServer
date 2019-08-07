package dao;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 5147265048973262104L;

    private String id;
    private String username;
    User(String id, String username) {
        this.id = id;
        this.username = username;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
