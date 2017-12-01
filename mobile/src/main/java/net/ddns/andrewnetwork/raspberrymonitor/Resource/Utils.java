package net.ddns.andrewnetwork.raspberrymonitor.Resource;

import android.content.Context;
import android.database.Cursor;

import net.ddns.andrewnetwork.raspberrymonitor.Adapter.CredentialAdapter;
import net.ddns.andrewnetwork.raspberrymonitor.Class.Connection;
import net.ddns.andrewnetwork.raspberrymonitor.Class.Credential;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by andry on 24/11/2017.
 */

public abstract class Utils {

    public static ArrayList<String> convertToListOfStrings(Cursor c,String column) {
        ArrayList<String> mArrayList = new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            mArrayList.add(c.getString(c.getColumnIndex(column)));
        }
        return mArrayList;
    }

    public static ArrayList<String> convertToListOfStrings(Cursor c,int column) {
        ArrayList<String> mArrayList = new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            mArrayList.add(c.getString(column));
        }
        return mArrayList;
    }

    public static ArrayList<Credential> convertToListOfCredential(Cursor c) {
        ArrayList<Credential> mArrayList = new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            // The Cursor is now set to the right position
            String name = c.getString(c.getColumnIndex("name"));
            String username = c.getString(c.getColumnIndex("username"));
            char[] password = c.getString(c.getColumnIndex("password")).toCharArray();
            int id = c.getInt(c.getColumnIndex("_id"));
            mArrayList.add(new Credential(name,username,password,id));
        }
        return mArrayList;
    }

    public static ArrayList<Connection> convertToListOfConnection(Cursor c, Context context) throws UnknownHostException {
        ArrayList<Connection> mArrayList = new ArrayList<>();
        CredentialAdapter cred = new CredentialAdapter(context);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            // The Cursor is now set to the right position
            String name = c.getString(c.getColumnIndex("name"));
            String address = c.getString(c.getColumnIndex("address"));
            int port = c.getInt(c.getColumnIndex("port"));
            int id = c.getInt(c.getColumnIndex("_id"));
            Credential credential = cred.getCredentialFromList(c.getInt(c.getColumnIndex("credential")));
            mArrayList.add(new Connection(address,name,port,id,credential));
        }
        return mArrayList;
    }

    public static ArrayList<ArrayList<String>> convertToBidemonsialListOfConnection(Cursor c, Context context) throws UnknownHostException {
        ArrayList<ArrayList<String>>  mArrayList = new ArrayList<>();
        CredentialAdapter cred = new CredentialAdapter(context);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            ArrayList<String> connection = new ArrayList<>();
            connection.add(c.getString(c.getColumnIndex("name")));
            connection.add(c.getString(c.getColumnIndex("address")));
            connection.add(c.getString(c.getColumnIndex("port")));
            connection.add(cred.getCredentialFromList(c.getInt(c.getColumnIndex("credential"))).getName());
            mArrayList.add(connection);
        }
        return mArrayList;
    }
}
