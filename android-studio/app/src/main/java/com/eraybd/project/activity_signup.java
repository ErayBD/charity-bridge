package com.eraybd.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eraybd.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class activity_signup extends AppCompatActivity {
    TextInputEditText editTextName, editTextEmail, editTextPassword,editTextPhone;
    Button buttonReg;
    ProgressBar progressBar;

    // SQL bağlantı bilgileri
    private static final String DB_URL = "jdbc:jtds:sqlserver://192.168.3.2:1433/gazi_proje";
    private static final String DB_USER = "gazi";
    private static final String DB_PASSWORD = "123456";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextName = findViewById(R.id.su_et_name);
        editTextEmail = findViewById(R.id.su_et_email);
        editTextPassword = findViewById(R.id.su_et_pw);
        progressBar = findViewById(R.id.progressBar);
        buttonReg = findViewById(R.id.su_bt_signup);
        editTextPhone = findViewById(R.id.su_et_name2);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String phone = editTextPhone.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(activity_signup.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(activity_signup.this, "Enter E-Mail", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(activity_signup.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(activity_signup.this, "Enter Phone", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kayıt ekleme işlemi
                registerUser(name, email, password,phone);
            }
        });
    }

    private void registerUser(String name, String email, String password, String phone) {
        progressBar.setVisibility(View.VISIBLE);

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "INSERT INTO Users(name, email, password,phone_number) VALUES (?, ?, ?,?)";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, name);
                        stmt.setString(2, email);
                        stmt.setString(3, password);
                        stmt.setString(4, phone);

                        return stmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return -1;
                }
            }

            @Override
            protected void onPostExecute(Integer affectedRows) {
                progressBar.setVisibility(View.GONE);

                if (affectedRows > 0) {
                    Toast.makeText(activity_signup.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_signup.this, activity_login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(activity_signup.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


}
