package net.ddns.andrewnetwork.raspberrymonitor.Adapter;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.ddns.andrewnetwork.raspberrymonitor.Class.Credential;
import net.ddns.andrewnetwork.raspberrymonitor.Helper.CredentialHelper;
import net.ddns.andrewnetwork.raspberrymonitor.Resource.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by andry on 15/11/2017.
 */
public class CredentialAdapter {

    private static final String LOG_TAG = CredentialAdapter.class.getSimpleName();

    private Context context;
    private SQLiteDatabase database;
    private CredentialHelper CredentialManager;

    private static String TABLE_NAME;

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    private static String[] Keys = {KEY_ID,KEY_NAME,KEY_USERNAME,KEY_PASSWORD};

    public CredentialAdapter(Context context) {
        this.context = context;
        open();
        TABLE_NAME = CredentialManager.getDatabaseTable();
    }

    public CredentialAdapter open() throws SQLException {
        CredentialManager = new CredentialHelper(context);
        database = CredentialManager.getWritableDatabase();
        CredentialManager.onCreate(database);
        return this;
    }

    public void close() {
        CredentialManager.close();
    }

    private ContentValues createContentValues(String name, String username, String password) {
        ContentValues values = new ContentValues();
        values.put( KEY_NAME, name );
        values.put( KEY_USERNAME, username );
        values.put( KEY_PASSWORD, password );

        return values;
    }

    public ArrayList<Credential> getList() {
        //returns a list of names
        return Utils.convertToListOfCredential(database.rawQuery("SELECT * FROM "+TABLE_NAME,null));
    }

   public ArrayList<String> getListOfNames() {
        //returns a list of "selection"
        return Utils.convertToListOfStrings(database.query(TABLE_NAME, Keys, null, null, null, null, null),KEY_NAME);
    }

    public Credential getCredentialFromList(int idtobesearched) {
        //returns a list of "selection"
        ArrayList<Credential> list = getList();
        for(Credential credential : list)
        {
            int id = credential.getId();
            if(id == idtobesearched)
                return credential;

        }
        return null;
    }

    public Credential getCredentialFromList(String nametobesearched) {
        //returns a list of "selection"
        ArrayList<Credential> list = getList();
        for(Credential credential : list)
        {
            String name = credential.getName();
            if(name.equals(nametobesearched))
                return credential;

        }
        return null;
    }


    public long add(String name, String username, String password) {
        ContentValues values = null;
        if(username!=null)
        {
            if(name.isEmpty()) values  = createContentValues(username,username,password);
            else values = createContentValues(name,username,password);
        }
        return database.insertOrThrow(TABLE_NAME, null, values);
    }
}
