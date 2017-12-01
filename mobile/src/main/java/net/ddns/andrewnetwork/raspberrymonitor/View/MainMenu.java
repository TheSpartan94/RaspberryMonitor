package net.ddns.andrewnetwork.raspberrymonitor.View;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import net.ddns.andrewnetwork.raspberrymonitor.Adapter.ConnectionAdapter;
import net.ddns.andrewnetwork.raspberrymonitor.Class.Connection;
import net.ddns.andrewnetwork.raspberrymonitor.R;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ConnectionAdapter conn;
    Spinner spinner;
    final int ADDED_CONNECTION = 1;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    boolean IS_CONNECTED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(policy);
        conn = new ConnectionAdapter(this);
        setContentView(R.layout.activity_phone);
        Toolbar toolbar = findViewById(R.id.toolbar);
        spinner  = findViewById(R.id.spinner);
        setSupportActionBar(toolbar);
        final Intent intent = new Intent(this, AddConnectionMenu.class);
        final Intent intent2 = new Intent(this, ConnectionMenu.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton modifyfab = (FloatingActionButton) findViewById(R.id.modifyConnection);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent,ADDED_CONNECTION);
            }
        });

        modifyfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent2,ADDED_CONNECTION);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
            super.onResume();
            ArrayAdapter<Object> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, conn.getListOfNames());
            spinner.setAdapter(adapter);
            spinner.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            List l = new ArrayList();
            try {
                l = conn.getList();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            if (!l.isEmpty()) {
                spinner.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
                if (!IS_CONNECTED) {
                    try {
                        connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IS_CONNECTED = true;
                        }
                }



    @Override
    public void onStop(){
        super.onStop();
        IS_CONNECTED=false;
    }

   public void connect() throws Exception {
       final Context context = this;
       AsyncTask.execute(new Runnable() {
           @Override
           public void run() {
               Connection currentConnection  = null;
               try {
                   String selected = (String) spinner.getSelectedItem();
                   currentConnection = conn.getConnectionFromList(selected);
               } catch (UnknownHostException e) {
                   e.printStackTrace();
               }
                Toast message;
               if(conn.connect(currentConnection))
               message = Toast.makeText(context, "Connection established with " + currentConnection.getAddress(), Toast.LENGTH_SHORT);
               else message = Toast.makeText(context, "Error: cannot find host", Toast.LENGTH_SHORT);
           }
       });
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
