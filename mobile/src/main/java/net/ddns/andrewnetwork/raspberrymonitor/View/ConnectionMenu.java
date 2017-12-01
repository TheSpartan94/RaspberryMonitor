package net.ddns.andrewnetwork.raspberrymonitor.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.ddns.andrewnetwork.raspberrymonitor.Adapter.ConnectionAdapter;
import net.ddns.andrewnetwork.raspberrymonitor.Class.Connection;
import net.ddns.andrewnetwork.raspberrymonitor.R;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by andry on 26/11/2017.
 */

public class ConnectionMenu extends AppCompatActivity {
    ConnectionAdapter conn;
    final int ADDED_CONNECTION = 1;
    ListView l;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_connection_menu);
        l = findViewById(R.id.Connection_list);
        ArrayList<Connection> connections = new ArrayList<>();

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DisplayConnections(connections));
        l.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        final Intent intent = new Intent(this, AddConnectionMenu.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent,ADDED_CONNECTION);
            }
        });
    }

    public ArrayList<String> DisplayConnections(ArrayList<Connection> connections) {
        ArrayList<String> displayable = new ArrayList<>();
        conn= new ConnectionAdapter(this);
        try {
            connections = conn.getList();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        for(Connection connection:connections)
            displayable.add("Name: " + connection.getName()
                    + "\nAddress: " +connection.getAddress() + ":" + connection.getPort() +
                    "\nCredentials: " + connection.getCredentials().getName());
        return displayable;
    }
}
