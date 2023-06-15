package com.eraybd.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.eraybd.project.databinding.ActivityRequestBinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class activity_request extends AppCompatActivity {

    public String userId;
    ActivityRequestBinding binding;
    activity_request_listadapter listAdapter;
    ArrayList<activity_request_listdata> dataArrayList = new ArrayList<>();
    public String[] donation_type = new String[0];
    public String[] donation_qua = new String[0];
    public String[] donation_desc = new String[0];
    public String[] cities = new String[0];
    public String[] branchesThree = new String[0];

    public String[] dateRecStrings = new String[0];

    public Integer[] deliveryStrings = new Integer[0];


    private static final String DB_URL = "jdbc:jtds:sqlserver://192.168.3.2:1433/gazi_proje";
    private static final String DB_USER = "gazi";
    private static final String DB_PASSWORD = "123456";

    Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = getIntent().getStringExtra("userID");

        binding = ActivityRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchData();
    }

    private class NetworkTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String query = "SELECT Donations.quantity, DonationTypes.description, Locations.city, Locations.branch, DonationTypes.type_name,Receive.date_of_receipt,Receive.delivery_id " +
                        "FROM Donations " +
                        "INNER JOIN DonationTypes ON Donations.type_id = DonationTypes.type_id " +
                        "INNER JOIN Locations ON Donations.location_id = Locations.location_id " +
                        "INNER JOIN Receive ON Receive.donation_id = Donations.donation_id " +
                        "WHERE Donations.user_id = ?";

                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(userId));
                ResultSet resultSet = statement.executeQuery();

                ArrayList<activity_request_listdata> dataList = new ArrayList<>();

                ArrayList<String> donation_typeList = new ArrayList<>();
                ArrayList<String> donation_quaList = new ArrayList<>();
                ArrayList<String> donation_descList = new ArrayList<>();
                ArrayList<String> citiesList = new ArrayList<>();
                ArrayList<String> branchesThreeList = new ArrayList<>();
                ArrayList<String> date_of_receiptList = new ArrayList<>();
                ArrayList<Integer>  deliverIDList = new ArrayList<>();


                while (resultSet.next()) {
                    String quantity = resultSet.getString("quantity");
                    String description = resultSet.getString("description");
                    String city = resultSet.getString("city");
                    String branch = resultSet.getString("branch");
                    String typeName = resultSet.getString("type_name");
                    String date_of_receipt = resultSet.getString("date_of_receipt");
                    Integer deliverID = resultSet.getInt("delivery_id");

                    donation_typeList.add(typeName);
                    donation_quaList.add(quantity);
                    donation_descList.add(description);
                    citiesList.add(city);
                    branchesThreeList.add(branch);
                    date_of_receiptList.add(date_of_receipt);
                    deliverIDList.add(deliverID);

                    activity_request_listdata listData = new activity_request_listdata(typeName, quantity, "", city, branch,date_of_receipt,deliverID);
                    dataList.add(listData);
                }

                dateRecStrings =date_of_receiptList.toArray(new String[0]);
                deliveryStrings =deliverIDList.toArray(new Integer[0]);
                donation_type = donation_typeList.toArray(new String[0]);
                donation_qua = donation_quaList.toArray(new String[0]);
                donation_desc = donation_descList.toArray(new String[0]);
                cities = citiesList.toArray(new String[0]);
                branchesThree = branchesThreeList.toArray(new String[0]);

                dataArrayList = dataList;

                resultSet.close();
                statement.close();
                conn.close();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (listAdapter == null) {
                listAdapter = new activity_request_listadapter(activity_request.this, dataArrayList);
                binding.listview.setAdapter(listAdapter);
            } else {
                listAdapter.notifyDataSetChanged();
            }

            binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(activity_request.this, activity_request_detailed.class);
                    intent.putExtra("donation_type", donation_type[position]);
                    intent.putExtra("donation_qua", donation_qua[position]);
                    intent.putExtra("donation_desc", donation_desc[position]);
                    intent.putExtra("city", cities[position]);
                    intent.putExtra("branch", branchesThree[position]);
                    intent.putExtra("delivery_id", deliveryStrings[position]);
                    intent.putExtra("date_of_receipt", dateRecStrings[position]);
                    startActivity(intent);
                }
            });
        }
    }

    private void fetchData() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new NetworkTask().execute();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }
}
