package com.eraybd.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class activity_contact extends AppCompatActivity {

    private CheckBox cb_anonymous;
    private EditText et_name, et_phone, et_message;
    private RadioGroup rg_main;

    private String comboBoxText;
    private String dbUrl = "jdbc:jtds:sqlserver://192.168.3.2:1433;databaseName=gazi_proje;user=gazi;password=123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        cb_anonymous = findViewById(R.id.checkBox_anonymous);
        et_name = findViewById(R.id.rp_et_name);
        et_phone = findViewById(R.id.rp_et_phone);
        et_message = findViewById(R.id.fp_et_email);
        rg_main = findViewById(R.id.radioGroup);

        cb_anonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    et_name.setEnabled(false);
                    et_phone.setEnabled(false);
                    et_name.setBackgroundResource(R.drawable.disabled_background);
                    et_phone.setBackgroundResource(R.drawable.disabled_background);
                } else {
                    et_name.setEnabled(true);
                    et_phone.setEnabled(true);
                    et_name.setBackgroundResource(R.drawable.text_border_background);
                    et_phone.setBackgroundResource(R.drawable.text_border_background);
                }
            }
        });

        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                String text = rb.getText().toString();
                setComboBox(text);
            }
        });

        Button submitButton = findViewById(R.id.fp_bt_recover);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveDataAsyncTask saveDataAsyncTask = new SaveDataAsyncTask();
                saveDataAsyncTask.execute();
            }
        });
    }

    public void setComboBox(String text) {
        comboBoxText = text;
    }

    private class SaveDataAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            String name = "";
            String phone = "";
            if (!cb_anonymous.isChecked()) {
                name = et_name.getText().toString();
                phone = et_phone.getText().toString();
            }
            String message = et_message.getText().toString();
            String suggestion = comboBoxText;

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(dbUrl);

                String query = "INSERT INTO contact (name, phone, message, message_type) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setString(2, phone);
                statement.setString(3, message);
                statement.setString(4, suggestion);
                statement.executeUpdate();

                statement.close();
                connection.close();
                return true;
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(activity_contact.this, "We received your message! Thank you for your attention!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(activity_contact.this, "Failed to save data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
