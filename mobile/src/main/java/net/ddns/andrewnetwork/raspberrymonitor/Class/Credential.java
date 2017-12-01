package net.ddns.andrewnetwork.raspberrymonitor.Class;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by andrea on 13/11/17.
 */
public class Credential {

    protected String name;
    private String username;
    private char[] password;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public Credential(String name, String username, char[] password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Credential(String name, String username, char[] password,int id) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
