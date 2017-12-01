package net.ddns.andrewnetwork.raspberrymonitor.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import net.ddns.andrewnetwork.raspberrymonitor.Class.Connection;
import net.ddns.andrewnetwork.raspberrymonitor.Helper.ConnectionHelper;
import net.ddns.andrewnetwork.raspberrymonitor.Class.Credential;
import net.ddns.andrewnetwork.raspberrymonitor.Resource.Utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by andry on 17/11/2017.
 */

public class ConnectionAdapter {
    @SuppressWarnings("unused")
        private static final String LOG_TAG = ConnectionAdapter.class.getSimpleName();

    private Context context;
    private SQLiteDatabase database;
    private ConnectionHelper ConnectionManager;
    private CredentialAdapter CredentialManager;

    private static String TABLE_NAME;
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PORT = "port";
    public static final String KEY_CREDENTIAL = "credential";
    private static String[] Keys = {KEY_ID,KEY_NAME,KEY_ADDRESS,KEY_PORT,KEY_CREDENTIAL};

    public ConnectionAdapter(Context context) {
        this.context = context;
        TABLE_NAME = "connections";
        open();
    }

    public ConnectionAdapter open() throws SQLException {
        ConnectionManager = new ConnectionHelper(context);
        database = ConnectionManager.getWritableDatabase();
        ConnectionManager.onCreate(database);
        return this;
    }

    public void close() {
        ConnectionManager.close();
    }

    private ContentValues createContentValues(String name, String address, int port, Credential credential ) {
        ContentValues values = new ContentValues();
        values.put( KEY_NAME, name );
        values.put( KEY_ADDRESS, address);
        values.put( KEY_PORT, port );
        values.put( KEY_CREDENTIAL, credential.getId() );

        return values;
    }

    public ArrayList<ArrayList<String>> getBidimensionalList() throws UnknownHostException {
        return Utils.convertToBidemonsialListOfConnection(database.rawQuery("SELECT * FROM "+TABLE_NAME,null),context);
    }

    public ArrayList<Connection> getList() throws UnknownHostException {
        return Utils.convertToListOfConnection(database.rawQuery("SELECT * FROM "+TABLE_NAME,null),context);
    }

    public ArrayList<String> getListOfNames() {
        //returns a list of "selection"
        return Utils.convertToListOfStrings(database.query(TABLE_NAME, Keys, null, null, null, null, null),KEY_NAME);
    }



    public long add(String address, String name, int port, Credential credentials) throws UnknownHostException {
        ContentValues values = null;
        if(!address.isEmpty() && credentials!=null)
        {
            if(name.isEmpty()) values  = createContentValues(address,address,port,credentials);
            else values = createContentValues(name,address,port,credentials);
        }
        return database.insertOrThrow(TABLE_NAME, null, values);
    }

    public Connection getConnectionFromList(String nametobesearched) throws UnknownHostException {
        //returns a list of "selection"
        ArrayList<Connection> list = getList();
        for(Connection connection : list)
        {
            String name = connection.getName();
            if(name.equals(nametobesearched))
                return connection;

        }
        return null;
    }

    public boolean connect(Connection connection) {
        Credential credential = connection.getCredentials();
        String user = credential.getUsername();
        char[] password = credential.getPassword();
        String host = connection.getAddress();
        int port = connection.getPort();

        //String remoteFile="/home/john/test.txt";

            JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(user, host, port);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        String passwordstring = new String(password);
            session.setPassword(passwordstring);
            session.setConfig("StrictHostKeyChecking", "no");
        try {
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        /*    ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();*/
            if(session.isConnected()) return true;
            else return false;
            /*InputStream out= sftpChannel.get(remoteFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(out));
            String line;
            while ((line = br.readLine()) != null)
                System.out.println(line);
            br.close();
        }*/
    }
    }

