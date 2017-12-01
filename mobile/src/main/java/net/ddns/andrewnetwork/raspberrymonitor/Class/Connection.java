package net.ddns.andrewnetwork.raspberrymonitor.Class;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by andrea on 13/11/17.
 */

public class Connection {
    private int port;
    private String address;
    protected String name;
    private Credential credentials;
    private int id;

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public Credential getCredentials() {
        return credentials;
    }

    public Connection(String address, String name, int port,int id, Credential credentials) throws UnknownHostException {
            this.address = address;
            this.name = name;
            this.credentials = credentials;
            this.port = port;
            this.id=id;
    }

    public Connection(String address, int port,int id, Credential credentials) throws UnknownHostException {
        this.address = address;
        this.name = address.toString();
        this.credentials = credentials;
        this.port = port;
        this.id=id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
