package net.ddns.andrewnetwork.raspberrymonitor.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.ddns.andrewnetwork.raspberrymonitor.Adapter.CredentialAdapter;
import net.ddns.andrewnetwork.raspberrymonitor.R;

/**
 * Created by andrea on 14/11/17.
 */

public class AddCredentialMenu extends AppCompatActivity {
    CredentialAdapter cred;
    EditText field1;
    EditText field2;
    EditText field3;
    Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_add_credential_menu);
        field1 = (EditText) findViewById(R.id.name);
        field2 = (EditText) findViewById(R.id.username);
        field3 = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button2);
        cred = new CredentialAdapter(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = field1.getText().toString();
                String username = field2.getText().toString();
                String password = field3.getText().toString();
                cred.add(name, username, password);
                cred.close();
                finish();
            }
        });
    }
}