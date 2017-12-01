package net.ddns.andrewnetwork.raspberrymonitor.View;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import net.ddns.andrewnetwork.raspberrymonitor.Adapter.ConnectionAdapter;
import net.ddns.andrewnetwork.raspberrymonitor.Adapter.CredentialAdapter;
import net.ddns.andrewnetwork.raspberrymonitor.Class.Credential;
import net.ddns.andrewnetwork.raspberrymonitor.R;

import java.net.UnknownHostException;

public class AddConnectionMenu extends AppCompatActivity {
    ConnectionAdapter conn;
    CredentialAdapter cred;
    FloatingActionButton fab;
    Button button;
    Intent intent;
     EditText field1;
     EditText field2;
     EditText field3;
     Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_add_connection_menu);
        field1 = (EditText) findViewById(R.id.editText);
        field2 = (EditText) findViewById(R.id.editText2);
        field3 = (EditText) findViewById(R.id.editText3);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        button = (Button) findViewById(R.id.button2);
        spinner = (Spinner) findViewById(R.id.spinner);
        intent = new Intent(this, AddCredentialMenu.class);
        conn = new ConnectionAdapter(this);
        cred = new CredentialAdapter(this);
        ArrayAdapter<Object> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cred.getListOfNames());
        spinner.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = field1.getText().toString();
                String address = field2.getText().toString();
                int port = Integer.parseInt(field3.getText().toString());
                Object credentialname = spinner.getSelectedItem();
                Credential credential = cred.getCredentialFromList((String) credentialname);
                try {
                    conn.add(address, name, port, credential);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                conn.close();
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        ArrayAdapter<Object> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cred.getListOfNames());
        spinner.setAdapter(adapter);
    }
    }