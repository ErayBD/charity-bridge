package com.eraybd.project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class activity_donate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static Button bt_location, btContinue;

    private static String selectedItem = "A"; //For .equals function, this cannot be null.

    private static String selectedItem2 = "A"; //For .equals function, this cannot be null.

    private int donation_price;

    private int donation_prices_int;

    private int donation_quantity;

    private int donation_quantity_int;

    private int total_amount;

    private static String total_amount_payment;

    TextView tvDesc, tvValue;
    EditText dt_et_cont;
    private static final String DB_URL = "jdbc:jtds:sqlserver://192.168.3.2:1433/gazi_proje";
    private static final String DB_USER = "gazi";
    private static final String DB_PASSWORD = "123456";

    String[] donation_type, donation_desc, donation_prices, donation_qua;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public String typeIntent,descIntent,contactIntent;
    public Integer quantityIntent;
    public Float AmountIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        btContinue = findViewById(R.id.dt_bt_continue);
        tvDesc = findViewById(R.id.dt_tv_descp);
        bt_location = findViewById(R.id.dt_bt_loc);
        tvValue = findViewById(R.id.dt_tv_value);
        dt_et_cont = findViewById(R.id.dt_et_cont);



        bt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity_gmap.setMapValueF();
                passToGMap();
            }
        });

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTotalAmountPayment();
                passToPayment();
            }
        });

        String[] donation_qua = getResources().getStringArray(R.array.donation_quantity);

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                    // SQL sorgusunu oluştur
                    String sql = "SELECT type_name, description, price FROM DonationTypes";
                    statement = connection.createStatement();

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    // Verileri saklamak için geçici ArrayList'ler oluştur
                    ArrayList<String> typeList = new ArrayList<>();
                    ArrayList<String> descList = new ArrayList<>();
                    ArrayList<String> priceList = new ArrayList<>();

                    typeList.add("Please Select");
                    //descList.add("Please Select");

                    // Sonuçları dolaşarak verileri ArrayList'lere ekle
                    while (resultSet.next()) {
                        String typeName = resultSet.getString("type_name");
                        String description = resultSet.getString("description");
                        String price = resultSet.getString("price");

                        typeList.add(typeName);
                        descList.add(description);
                        priceList.add(price);

                    }

                    // ArrayList'leri String[] dizilerine dönüştür

                    donation_type = typeList.toArray(new String[0]);
                    donation_desc =  descList.toArray(new String[0]);
                    donation_prices = priceList.toArray(new String[0]);


                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Kaynakları serbest bırak
                    try {
                        if (resultSet != null) resultSet.close();
                        if (statement != null) statement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();




        String[] branches = getResources().getStringArray(R.array.donation_quantity);


        Spinner type_spinner = findViewById(R.id.dt_type_spinner);
        ArrayAdapter<CharSequence> type_adapter = ArrayAdapter.createFromResource(this, R.array.donation_type, android.R.layout.simple_spinner_item);
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(type_adapter);

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = type_spinner.getSelectedItem().toString();

                if (selectedItem.equalsIgnoreCase(donation_type[1])) {
                    tvDesc.setText(donation_desc[0]);
                    donation_prices_int = Integer.parseInt(donation_prices[0]);
                    setDonationPrice(donation_prices_int);
                    setTotalAmount();
                    descIntent = donation_desc[0];
                    typeIntent = donation_type[1];
                    AmountIntent = Float.valueOf(donation_prices_int);
                }
                else if (selectedItem.equalsIgnoreCase(donation_type[2])) {
                    tvDesc.setText(donation_desc[1]);
                    donation_prices_int = Integer.parseInt(donation_prices[1]);
                    setDonationPrice(donation_prices_int);
                    setTotalAmount();
                    descIntent = donation_desc[1];
                    typeIntent = donation_type[2];
                    AmountIntent = Float.valueOf(donation_prices_int);
                }
                else if (selectedItem.equalsIgnoreCase(donation_type[3])) {
                    tvDesc.setText(donation_desc[2]);
                    donation_prices_int = Integer.parseInt(donation_prices[2]);
                    setDonationPrice(donation_prices_int);
                    setTotalAmount();

                    descIntent = donation_desc[2];
                    typeIntent = donation_type[3];
                    AmountIntent = Float.valueOf(donation_prices_int);
                }
                else if (selectedItem.equalsIgnoreCase(donation_type[4])) {
                    tvDesc.setText(donation_desc[3]);
                    donation_prices_int = Integer.parseInt(donation_prices[3]);
                    setDonationPrice(donation_prices_int);
                    setTotalAmount();


                    descIntent = donation_desc[3];
                    typeIntent = donation_type[4];
                    AmountIntent = Float.valueOf(donation_prices_int);
                }
                else if (selectedItem.equalsIgnoreCase(donation_type[5])) {
                    tvDesc.setText(donation_desc[4]);
                    donation_prices_int = Integer.parseInt(donation_prices[4]);
                    setDonationPrice(donation_prices_int);
                    setTotalAmount();

                    descIntent = donation_desc[4];
                    typeIntent = donation_type[5];
                    AmountIntent = Float.valueOf(donation_prices_int);
                }
                else if (selectedItem.equalsIgnoreCase(donation_type[6])) {
                    tvDesc.setText(donation_desc[5]);
                    donation_prices_int = Integer.parseInt(donation_prices[5]);
                    setDonationPrice(donation_prices_int);
                    setTotalAmount();

                    descIntent = donation_desc[5];
                    typeIntent = donation_type[6];
                    AmountIntent = Float.valueOf(donation_prices_int);
                }
                else if (selectedItem.equalsIgnoreCase(donation_type[7])) {
                    tvDesc.setText(donation_desc[6]);
                    donation_prices_int = Integer.parseInt(donation_prices[6]);
                    setDonationPrice(donation_prices_int);
                    setTotalAmount();

                    descIntent = donation_desc[6];
                    typeIntent = donation_type[7];
                    AmountIntent = Float.valueOf(donation_prices_int);
                }
                else if (selectedItem.equalsIgnoreCase(donation_type[8])) {
                    tvDesc.setText(donation_desc[7]);
                    donation_prices_int = Integer.parseInt(donation_prices[7]);
                    setDonationPrice(donation_prices_int);
                    setTotalAmount();

                    descIntent = donation_desc[7];
                    typeIntent = donation_type[8];
                    AmountIntent = Float.valueOf(donation_prices_int);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });



        Spinner qua_spinner = findViewById(R.id.dt_qua_spinner);
        ArrayAdapter<CharSequence> qua_adapter = ArrayAdapter.createFromResource(this, R.array.donation_quantity, android.R.layout.simple_spinner_item);
        qua_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qua_spinner.setAdapter(qua_adapter);
        qua_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = qua_spinner.getSelectedItem().toString();


                if (selectedItem.equalsIgnoreCase(donation_qua[0])) {}

                else if (selectedItem.equalsIgnoreCase(donation_qua[1])) {
                    donation_quantity_int = Integer.parseInt(donation_qua[1]);
                    setDonationQuantity(donation_quantity_int);
                    quantityIntent = donation_quantity_int;
                    setTotalAmount();
                }

                else if (selectedItem.equalsIgnoreCase(donation_qua[2])) {
                    donation_quantity_int = Integer.parseInt(donation_qua[2]);
                    setDonationQuantity(donation_quantity_int);
                    quantityIntent = donation_quantity_int;
                    setTotalAmount();
                }

                else if (selectedItem.equalsIgnoreCase(donation_qua[3])) {
                    donation_quantity_int = Integer.parseInt(donation_qua[3]);
                    setDonationQuantity(donation_quantity_int);
                    quantityIntent = donation_quantity_int;
                    setTotalAmount();
                }

                else if (selectedItem.equalsIgnoreCase(donation_qua[4])) {
                    donation_quantity_int = Integer.parseInt(donation_qua[4]);
                    setDonationQuantity(donation_quantity_int);
                    quantityIntent = donation_quantity_int;
                    setTotalAmount();
                }

                else if (selectedItem.equalsIgnoreCase(donation_qua[5])) {
                    donation_quantity_int = Integer.parseInt(donation_qua[5]);
                    setDonationQuantity(donation_quantity_int);
                    quantityIntent = donation_quantity_int;
                    setTotalAmount();
                }

                //Toast.makeText(getApplicationContext(), "You selected " + selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        Spinner loc2_spinner = findViewById(R.id.dt_loc_map_spinner2);
        ArrayAdapter<CharSequence> loctemp_adapter = ArrayAdapter.createFromResource(activity_donate.this, R.array.branchForTwo, android.R.layout.simple_spinner_item);
        loctemp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loc2_spinner.setAdapter(loctemp_adapter);
        loc2_spinner.setEnabled(false);



        loc2_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem2 = loc2_spinner.getSelectedItem().toString();
                setLocation2(selectedItem2);
                //Toast.makeText(getApplicationContext(), "You selected " + selectedItem2, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        Spinner loc_spinner = findViewById(R.id.dt_loc_map_spinner);
        ArrayAdapter<CharSequence> loc_adapter = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
        loc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loc_spinner.setAdapter(loc_adapter);
        loc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = loc_spinner.getSelectedItem().toString();
                setLocation(selectedItem);
                //Toast.makeText(getApplicationContext(), "You selected " + selectedItem, Toast.LENGTH_LONG).show();


                if (selectedItem.equalsIgnoreCase("Please Select")) {
                    loc2_spinner.setEnabled(false);
                }
                else {
                    ArrayAdapter<CharSequence> loc2_adapter;
                    if (selectedItem.equalsIgnoreCase("Istanbul")||selectedItem.equalsIgnoreCase("Ankara")||
                            selectedItem.equalsIgnoreCase("Izmir")||selectedItem.equalsIgnoreCase("Bursa")||
                            selectedItem.equalsIgnoreCase("Antalya")||selectedItem.equalsIgnoreCase("Adana")) {
                        loc2_adapter = ArrayAdapter.createFromResource(activity_donate.this, R.array.branchForThree, android.R.layout.simple_spinner_item);

                    }
                    else {
                        loc2_adapter = ArrayAdapter.createFromResource(activity_donate.this, R.array.branchForTwo, android.R.layout.simple_spinner_item);
                    }
                    loc2_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    loc2_spinner.setAdapter(loc2_adapter);
                    loc2_spinner.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

    }

    public void passToGMap() {
        Intent intent = new Intent(this, activity_gmap.class);
        startActivity(intent);
    }

    public void passToPayment() {
        // AsyncTask'i başlat
        new DatabaseTask().execute();
    }

    private class DatabaseTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String typeId = getTypeID(typeIntent, descIntent);
            int locationId = getLocationIdFromDatabase(getLocation(), getLocation2());

            Intent intent = new Intent(activity_donate.this, activity_payment.class);
            intent.putExtra("type_id", typeId);
            intent.putExtra("location_id", getLocationIdFromDatabase(getLocation(), getLocation2()));
            intent.putExtra("donation_Quantity",donation_quantity);
            intent.putExtra("price_Last", getTotalAmountPayment());
            intent.putExtra("user_Contact", dt_et_cont.getText());

            startActivity(intent);

            return null;
        }
    }

    public String getTypeID(String typeIntent, String descIntent) {
        String typeID = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT type_id FROM DonationTypes WHERE type_name = ? AND description = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, typeIntent);
            preparedStatement.setString(2, descIntent);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                typeID = resultSet.getString("type_id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return typeID;
    }

    public int getLocationIdFromDatabase(String locationIntent, String locationBranch) {
        int locationId = -1;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT location_id FROM Locations WHERE city = ? AND branch = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, locationIntent);
            preparedStatement.setString(2, locationBranch);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                locationId = resultSet.getInt("location_id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return locationId;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static void setLocation(String sItem) {
        selectedItem = sItem;
    }



    public static void setLocation2(String sItem) {
        selectedItem2 = sItem;
    }

    public static String getLocation() {
        return selectedItem;
    }

    public static String getLocation2() {
        return selectedItem2;
    }

    public void setDonationQuantity(int amount) {
        donation_quantity = amount;
    }

    public int getDonationQuantity() {
        return donation_quantity;
    }

    public void setDonationPrice(int amount) {
        donation_price = amount;
    }

    public int getDonationPrice() {
        return donation_price;
    }

    public void setTotalAmount() {
        total_amount = getDonationQuantity() * getDonationPrice();
        tvValue.setText("$" + Integer.toString(total_amount));
    }

    public void setTotalAmountPayment() {
        total_amount_payment = tvValue.getText().toString();
    }

    public static String getTotalAmountPayment() {
        return total_amount_payment;
    }

}