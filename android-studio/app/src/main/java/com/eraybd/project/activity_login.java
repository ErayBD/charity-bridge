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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class activity_login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    private Button buttonLogin;


    // MSSQL veritabanı bağlantı bilgileri
    private static final String DB_URL = "jdbc:jtds:sqlserver://192.168.3.2:1433/gazi_proje";
    private static final String DB_USER = "gazi";
    private static final String DB_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.li_et_email);
        passwordEditText = findViewById(R.id.li_et_pw);
        buttonLogin = findViewById(R.id.li_bt_login);
        TextView signup = findViewById(R.id.li_tv_signup);
        progressBar = findViewById(R.id.progressBar);
        TextView forgotpw = findViewById(R.id.li_tv_forgotpw);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passToSignUp();
            }
        });
        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passToForgotPw();
            }
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // AsyncTask kullanarak ağ işlemlerini gerçekleştirme
        new LoginTask().execute(email, password);
    }

    public void passToForgotPw() {
        Intent intent = new Intent(this, activity_forgotpw.class);
        startActivity(intent);
    }

    public void passToSignUp() {
        Intent intent = new Intent(this, activity_signup.class);
        startActivity(intent);
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {
        public String userId = null;
        public String userName = null;
        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String password = params[1];


            // MSSQL veritabanına bağlanma işlemi
            try {

                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                // Veritabanı sorgusu
                String query = "SELECT * FROM Users WHERE email = ? AND password = ? ";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Giriş başarılı
                    userId = resultSet.getString("user_id");
                    userName = resultSet.getString("name");
                    conn.close();
                    return true;
                } else {
                    // Giriş başarısız
                    conn.close();
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressBar.setVisibility(View.GONE);

            if (result) {
                // Giriş başarılı
                Toast.makeText(activity_login.this, "Login successful.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("userID",userId);
                intent.putExtra("userName",userName);
                startActivity(intent);
                finish();

            } else {
                // Giriş başarısız
                Toast.makeText(activity_login.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}





/* buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(activity_login.this, "Enter E-Mail", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(activity_login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }



            }
        });*/
//progressBar.setVisibility(View.GONE);
//                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    startActivity(intent);
//                                    finish();

          /*    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                               */