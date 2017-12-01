package net.ddns.andrewnetwork.raspberrymonitor.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.ddns.andrewnetwork.raspberrymonitor.Class.Connection;

import java.util.ArrayList;

/**
 * Created by andrea on 13/11/17.
 */

public class ConnectionHelper extends SQLiteOpenHelper {
    ArrayList<Connection> connectionList = new ArrayList<>();
    Context context;
    private static final String DATABASE_NAME = "raspberrymonitor.db";
    private static final int DATABASE_VERSION = 1;
    /*public static final String MEMORY_FILE ="connections.json";
    public static final String MEMORY_KEY ="Connectionset";
    MemoryManager mem;*/
    private static final String DATABASE_CREATE = "create table if not exists connections (_id integer primary key autoincrement, " +
            "name text unique not null, address text not null, port int not null, credential integer not null,   FOREIGN KEY(credential) REFERENCES credentials(id)\n );";
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) {
        database.execSQL("DROP TABLE IF EXISTS connections");
        onCreate(database);
    }


    public ConnectionHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
      /*  this.context = context;
        mem = new MemoryManager(context,MEMORY_FILE);*/
    }
}
