package net.ddns.andrewnetwork.raspberrymonitor.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.ddns.andrewnetwork.raspberrymonitor.Class.Credential;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrea on 14/11/17.
 */

public class CredentialHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "raspberrymonitor.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "credentials";
   /* public static final String MEMORY_FILE ="credentials.json";
    public static final String MEMORY_KEY ="Credentialset";
    MemoryManager mem;*/
   private static final String TABLE_CREATE = "create table if not exists " + DATABASE_TABLE + " (_id integer primary key autoincrement, " +
           "name text not null, username text not null, password text not null);";


    public Cursor getAll(SQLiteDatabase database) {
        Cursor mCursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        return mCursor;
    }

    public CredentialHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS credentials");
        onCreate(database);
    }

    public static String getDatabaseTable() {
        return DATABASE_TABLE;
    }
}

