package com.epam.training.ticketservice.model;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private boolean admin;
    @Column
    private boolean loggedIn;

    public User() {
    }

    public User(int id, String username, String password, boolean admin, boolean loggedIn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.loggedIn = loggedIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
