        package com.eraybd.project;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.TextView;
        import java.util.Calendar;
        import java.util.Random;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.SQLException;

        public class activity_receive extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

            private static Button bt_location;
            private static String selectedItem = "A"; //For .equals function, this cannot be null.
            private static String selectedItem2 = "A"; //For .equals function, this cannot be null.

            EditText etDate;
            Button btDate;
            TextView etID, tvDesc;
            DatePickerDialog.OnDateSetListener setListener;

            private Random random = new Random();
            public String randomNumber = String.valueOf(getRandom().nextInt(999999999));

            private String BAGIS_AD;
            private String BAGIS_CITY;
            private String BAGIS_BRANCH;
            private Integer BAGIS_ID;
            private Integer BAGIS_QUANTITY;

            public String getBAGIS_DESC() {
                return BAGIS_DESC;
            }

            public void setBAGIS_DESC(String BAGIS_DESC) {
                this.BAGIS_DESC = BAGIS_DESC;
            }

            private String BAGIS_DESC;

            TextView donatetype, location_city, location_branch, deliveryID;
            EditText dateofReceipt;

            @Override
            protected void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_receive);
                donatetype = findViewById(R.id.dt_tv_donatetype);
                location_city = findViewById(R.id.dt_tv_city);
                location_branch = findViewById(R.id.dt_tv_branch);
                deliveryID = findViewById(R.id.dt_tv_id);
                dateofReceipt = findViewById(R.id.rc_et_date);
                tvDesc = findViewById(R.id.rc_tv_descp);
                btDate = findViewById(R.id.rc_bt_date);
                etID = findViewById(R.id.dt_tv_id);
                etID.setText(randomNumber);
                etDate = findViewById(R.id.rc_et_date);

                Intent intent = getIntent();
                if (intent != null) {
                    //activity_request sayfasindan kopyalanmis String dizileri degiskene atanir ve set edilir
                    setBAGIS_AD(intent.getStringExtra("BAGIS_NAME"));
                    setBAGIS_ID(intent.getIntExtra("BAGIS_ID", 0));
                    setBAGIS_CITY(intent.getStringExtra("BAGIS_CITY"));
                    setBAGIS_BRANCH(intent.getStringExtra("BAGIS_BRANCH"));
                    setBAGIS_QUANTITY(intent.getIntExtra("BAGIS_QUANTITY", 0));
                    setBAGIS_DESC(intent.getStringExtra("BAGIS_DESC"));
                }

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                dateofReceipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                activity_receive.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();
                    }
                });

                setListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        etDate.setText(date);
                    }
                };

                btDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                activity_receive.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String date = day + "/" + month + "/" + year;
                                etDate.setText(date);
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });

                bt_location = findViewById(R.id.rc_bt_loc);
                bt_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity_gmap.setMapValueF();
                        passToMap();
                    }
                });

                String[] donation_desc = getResources().getStringArray(R.array.donation_desc);
                String[] donation_type = getResources().getStringArray(R.array.donation_type);

                // TextView'lere verileri yükleme
                donatetype.setText(getBAGIS_AD());
                location_city.setText(getBAGIS_CITY());
                location_branch.setText(getBAGIS_BRANCH());
                deliveryID.setText(String.valueOf(randomNumber));
                tvDesc.setText(getBAGIS_DESC());


                // Butona tıklandığında veri ekleme
                Button btnAdd = findViewById(R.id.rc_bt_continue);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the input values
                        String receiveID = String.valueOf(getBAGIS_ID());
                        String donationID = String.valueOf(getBAGIS_ID());
                        String dateOfReceipt = dateofReceipt.getText().toString();
                        String deliveryID = String.valueOf(etID.getText());

                        // Execute the AsyncTask
                        new DatabaseTask().execute(receiveID, donationID, dateOfReceipt, deliveryID);
                        // Start the MainActivity
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        v.getContext().startActivity(intent);
                    }
                });

            }
            private class DatabaseTask extends AsyncTask<String, Void, Void> {

                @Override
                protected Void doInBackground(String... params) {
                    String receiveID = params[0];
                    String donationID = params[1];
                    String dateOfReceipt = params[2];
                    String deliveryID = params[3];

                    // Database connection and insertion code
                    String url = "jdbc:jtds:sqlserver://192.168.3.2:1433;databaseName=gazi_proje;user=gazi;password=123456";
                    String query = "INSERT INTO Receive (donation_id, date_of_receipt, delivery_id) VALUES (?, ?, ?)";

                    try {
                        Connection connection = DriverManager.getConnection(url);
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, donationID);
                        statement.setString(2, dateOfReceipt);
                        statement.setString(3, deliveryID);

                        int rowsAffected = statement.executeUpdate();

                        statement.close();
                        connection.close();

                        // You can return a result if needed
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Handle the exception or show an error message
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    // This method is executed on the UI thread after the background task finishes
                    // You can perform any UI updates or operations here
                }
            }


            public void passToMap() {
                Intent intent = new Intent(this, activity_gmap.class);
                startActivity(intent);
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

            public static String getLocation() {
                return selectedItem;
            }

            public static String getLocation2() {
                return selectedItem2;
            }

            public Random getRandom() {
                return random;
            }

            public void setRandom(Random random) {
                this.random = random;
            }

            public String getBAGIS_AD() {
                return BAGIS_AD;
            }

            public void setBAGIS_AD(String BAGIS_AD) {
                this.BAGIS_AD = BAGIS_AD;
            }

            public String getBAGIS_CITY() {
                return BAGIS_CITY;
            }

            public void setBAGIS_CITY(String BAGIS_CITY) {
                this.BAGIS_CITY = BAGIS_CITY;
            }

            public String getBAGIS_BRANCH() {
                return BAGIS_BRANCH;
            }

            public void setBAGIS_BRANCH(String BAGIS_BRANCH) {
                this.BAGIS_BRANCH = BAGIS_BRANCH;
            }

            public Integer getBAGIS_ID() {
                return BAGIS_ID;
            }

            public void setBAGIS_ID(Integer BAGIS_ID) {
                this.BAGIS_ID = BAGIS_ID;
            }

            public Integer getBAGIS_QUANTITY() {
                return BAGIS_QUANTITY;
            }

            public void setBAGIS_QUANTITY(Integer BAGIS_QUANTITY) {
                this.BAGIS_QUANTITY = BAGIS_QUANTITY;
            }
        }
