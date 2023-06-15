package com.eraybd.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class activity_payment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button bt_pay;
    private EditText etName, etNumber, etCVV;
    private Spinner spMonth, spYear;
    private String type_id,  price_Last, contact;
    private Integer location_id, donation_Quantity;
    private String user_id;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();

        if (intent != null) {
            type_id = intent.getStringExtra("type_id");
            location_id = intent.getIntExtra("location_id",0);
            donation_Quantity = intent.getIntExtra("donation_Quantity",0);
            price_Last = intent.getStringExtra("price_Last");
            contact = intent.getStringExtra("user_Contact");

        }

        bt_pay = findViewById(R.id.rp_bt_pay);
        etName = findViewById(R.id.py_et_name);
        etNumber = findViewById(R.id.py_et_number);
        etCVV = findViewById(R.id.py_et_cvv);
        spMonth = findViewById(R.id.py_sp_m);
        spYear = findViewById(R.id.py_sp_y);

        setBtPay("Pay " + activity_donate.getTotalAmountPayment());

        ArrayAdapter<CharSequence> m_adapter = ArrayAdapter.createFromResource(this, R.array.cvv_months, android.R.layout.simple_spinner_item);
        m_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(m_adapter);
        spMonth.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> y_adapter = ArrayAdapter.createFromResource(this, R.array.cvv_years, android.R.layout.simple_spinner_item);
        y_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(y_adapter);
        spYear.setOnItemSelectedListener(this);

        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPayButtonClicked();
            }
        });

        // Get user_id and user_name from MainActivity
        user_id = MainActivity.getUserID();
        user_name = MainActivity.getUserName();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setBtPay(String text) {
        bt_pay.setText(text);
    }

    public void onPayButtonClicked() {
        String userName = etName.getText().toString();
        String number = etNumber.getText().toString();
        String cvv = etCVV.getText().toString();
        String month = spMonth.getSelectedItem().toString();
        String year = spYear.getSelectedItem().toString();

        if (userName.isEmpty() || number.isEmpty() || cvv.isEmpty() || month.isEmpty() || year.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        } else {
            new PaymentTask().execute(userName, number, cvv, month, year);
            Toast.makeText(this, "Your donation has been made successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }
    }

    private class PaymentTask extends AsyncTask<String, Void, Boolean> {
        private Connection connection;

        @Override
        protected Boolean doInBackground(String... params) {
            String userName = params[0];
            String number = params[1];
            String cvv = params[2];
            String month = params[3];
            String year = params[4];

            String url = "jdbc:jtds:sqlserver://192.168.3.2:1433/gazi_proje";
            String username = "gazi";
            String password = "123456";

            try {
                connection = DriverManager.getConnection(url, username, password);

                // Payments tablosunu doldurma
                String insertPaymentQuery = "INSERT INTO Payments (user_id, card_number, exp_month, exp_year, cvv) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement paymentStatement = connection.prepareStatement(insertPaymentQuery);
                paymentStatement.setInt(1, Integer.parseInt(user_id)); // user_id değişkeni nereden geliyor?
                paymentStatement.setString(2, number);
                paymentStatement.setInt(3, Integer.parseInt(month));
                paymentStatement.setInt(4, Integer.parseInt(year));
                paymentStatement.setString(5, cvv);
                paymentStatement.executeUpdate();

                // Contacts tablosunu doldurma
                String insertContactQuery = "INSERT INTO Contacts (name, phone) VALUES (?, ?)";
                PreparedStatement contactStatement = connection.prepareStatement(insertContactQuery);
                contactStatement.setString(1, userName);
                contactStatement.setString(2, contact); // contact değişkeni nereden geliyor?
                contactStatement.executeUpdate();

                // Donations tablosunu doldurma
                String insertDonationQuery = "INSERT INTO Donations (type_id, user_id, location_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement donationStatement = connection.prepareStatement(insertDonationQuery);
                donationStatement.setInt(1, Integer.parseInt(type_id)); // type_id değişkeni nereden geliyor?
                donationStatement.setInt(2, Integer.parseInt(user_id)); // user_id değişkeni nereden geliyor?
                donationStatement.setInt(3, location_id); // location_id değişkeni nereden geliyor?
                donationStatement.setInt(4, donation_Quantity); // donation_Quantity değişkeni nereden geliyor?
                String str = price_Last;
                String numbersOnly = str.replaceAll("[^0-9]", "");
                int deneme = Integer.parseInt(numbersOnly);
                donationStatement.setInt(5, deneme);


                donationStatement.executeUpdate();

                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
