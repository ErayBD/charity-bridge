package com.eraybd.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_forgotpw extends AppCompatActivity {

    String name, email;
    Button bt_recover;
    EditText et_name, et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpw);

        bt_recover = findViewById(R.id.fp_bt_recover);
        et_name = findViewById(R.id.rp_et_name);
        et_email = findViewById(R.id.fp_et_email);

        name = et_name.getText().toString();    // Recover-Name
        email = et_email.getText().toString();  // Recover-Email

        bt_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}