package com.eraybd.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BagislarActivity extends AppCompatActivity implements RecyclerViewInterface {



    // ArrayList ile BagisAdlarini ve Açıklamalarını çekebileceğiz.
    ArrayList<BagisModel> bagisModelleri = new ArrayList<>();
    public int donationID;
    public String donate_DESC,donate,donate_CITY,donate_BRANCH;
    public Integer donate_QUANTITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bagislar);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        setUpBagisModelleri();

        BAGIS_RecyclerViewAdapter adapter = new BAGIS_RecyclerViewAdapter(this, bagisModelleri,BagislarActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpBagisModelleri() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Database bağlantısı için gerekli bilgiler
                String url = "jdbc:jtds:sqlserver://192.168.3.2:1433/gazi_proje";
                String username = "gazi";
                String password = "123456";

                Connection connection = null;
                Statement statement = null;
                ResultSet resultSet = null;

                try {
                    // JDBC sürücüsünü yükle
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");

                    // Veritabanına bağlan
                    connection = DriverManager.getConnection(url, username, password);
                    statement = connection.createStatement();

                    // Donations tablosunu sorgula
                    // EN SON BURADA DONATION_ID ÇEKİLMİYORDU
                    String query = "SELECT dt.type_name, dt.description, d.donation_id, d.quantity, l.city, l.branch " +
                            "FROM Donations d " +
                            "INNER JOIN DonationTypes dt ON d.type_id = dt.type_id " +
                            "INNER JOIN Locations l ON d.location_id = l.location_id";

                    resultSet = statement.executeQuery(query);

                    // Sorgudan elde edilen verileri kullanarak bagisModelleri dizisine ekle
                    while (resultSet.next()) {
                        String typeName = resultSet.getString("type_name");
                        String description = resultSet.getString("description");
                        donate_DESC = resultSet.getString("description");
                        donationID = resultSet.getInt("donation_id");
                        donate_CITY  = resultSet.getString("city");
                        donate_BRANCH = resultSet.getString("branch");
                        donate_QUANTITY = resultSet.getInt("quantity");

                        bagisModelleri.add(new BagisModel(typeName, description,donationID));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Kaynakları serbest bırak
                    try {
                        if (resultSet != null)
                            resultSet.close();
                        if (statement != null)
                            statement.close();
                        if (connection != null)
                            connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                // UI thread'ine verileri güncelleme talebi gönder
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BAGIS_RecyclerViewAdapter adapter = new BAGIS_RecyclerViewAdapter(BagislarActivity.this, bagisModelleri, BagislarActivity.this);
                        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(BagislarActivity.this));
                    }
                });
            }
        });

        thread.start();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(BagislarActivity.this,activity_receive.class);
        intent.putExtra("BAGIS_NAME",bagisModelleri.get(position).getBagisTur());
        intent.putExtra("BAGIS_ID",bagisModelleri.get(position).get_ID());
        intent.putExtra("BAGIS_CITY",donate_CITY);
        intent.putExtra("BAGIS_BRANCH",donate_BRANCH);
        intent.putExtra("BAGIS_QUANTITY",donate_QUANTITY);
        intent.putExtra("BAGIS_DESC",donate_DESC);
        startActivity(intent);

    }
}
