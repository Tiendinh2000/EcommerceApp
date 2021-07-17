package hanu.a2_1801040189.models;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String username;


    public User(String id, String username) {
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
