package com.eraybd.project;


import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import android.location.Location;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class activity_gmap extends AppCompatActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap mMap, mMapBranches;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    private static final String DB_URL = "jdbc:jtds:sqlserver://192.168.3.2:1433/gazi_proje";
    private static final String DB_USER = "gazi";
    private static final String DB_PASSWORD = "123456";

    private static boolean map = false;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this); // Initialize the variable

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            LatLng latLng = new LatLng(latitude, longitude);

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);

                            mMap.addMarker(markerOptions);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        } else {
                            Toast.makeText(activity_gmap.this, "Location not available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);

        String[] locations = getResources().getStringArray(R.array.location);

        String[] branches = new String[3];
            branches[0] = "Branch 1";
            branches[1] = "Branch 2";
            branches[2] = "Branch 3";


           LatLng[] centerAll = new LatLng[locations.length];
            centerAll[0] = new LatLng(38.5462, 35.0);
            centerAll[1] = new LatLng(41.003693349654865, 28.9308985325552);
            centerAll[2] = new LatLng(39.95079898548676, 32.81577445398295);
            centerAll[3] = new LatLng(38.433625291105876, 27.145036227351422);
            centerAll[4] = new LatLng(40.21275215038775, 29.06053965200241);
            centerAll[5] = new LatLng(36.909112368821724, 30.717706561665686);
            centerAll[6] = new LatLng(36.997783983604066, 35.316794746942456);
            centerAll[7] = new LatLng(37.86956517652147, 32.49294636638942);
            centerAll[8] = new LatLng(37.0631334252607, 37.37726785593412);
            centerAll[9] = new LatLng(37.16322031697473, 38.796717382591076);
            centerAll[10] = new LatLng(36.80344918904754, 34.607536607173834);
            centerAll[11] = new LatLng(37.9291494189562, 40.202502768805736);
            centerAll[12] = new LatLng(38.72792542015061, 35.48700746724388);
            centerAll[13] = new LatLng(39.76931974836146, 30.51944343837406);
            centerAll[14] = new LatLng(41.284629862572935, 36.33309564407637);
            centerAll[15] = new LatLng(37.78108689575632, 29.079740321689343);
            centerAll[16] = new LatLng(37.58177305535651, 36.91742776376926);
            centerAll[17] = new LatLng(38.35572775461467, 38.322992150317255);
            centerAll[18] = new LatLng(39.90337611070463, 41.272539304700764);
            centerAll[19] = new LatLng(38.49830648810753, 43.365418297576156);
            centerAll[20] = new LatLng(37.89066421322417, 41.13566422926805);

        LatLng temp = new LatLng(0,0);

        LatLng[] latlnglist = new LatLng[locations.length];
            latlnglist[0] = new LatLng(41.06117820058356, 28.909503760589427);
            latlnglist[1] = new LatLng(39.93955052616844, 32.82216953650539);
            latlnglist[2] = new LatLng(38.42509672076776, 27.146628702849075);
            latlnglist[3] = new LatLng(40.200911705418356, 29.060298795888638);
            latlnglist[4] = new LatLng(36.88674049604239, 30.697577131350723);
            latlnglist[5] = new LatLng(37.0000, 35.3213);
            latlnglist[6] = new LatLng(37.8714, 32.4846);
            latlnglist[7] = new LatLng(37.0662, 37.3833);
            latlnglist[8] = new LatLng(37.1591, 38.7969);
            latlnglist[9] = new LatLng(36.8000, 34.6333);
            latlnglist[10] = new LatLng(37.9144, 40.2306);
            latlnglist[11] = new LatLng(38.7333, 35.4833);
            latlnglist[12] = new LatLng(39.7667, 30.5250);
            latlnglist[13] = new LatLng(41.2867, 36.3303);
            latlnglist[14] = new LatLng(37.7765, 29.0864);
            latlnglist[15] = new LatLng(37.5847, 36.9263);
            latlnglist[16] = new LatLng(38.3552, 38.3095);
            latlnglist[17] = new LatLng(39.9056, 41.2759);
            latlnglist[18] = new LatLng(38.5017, 43.3725);
            latlnglist[19] = new LatLng(37.8812, 41.1351);

        LatLng[] InIstanbul = new LatLng[3];
        LatLng[] InAnkara = new LatLng[3];
        LatLng[] InIzmir = new LatLng[3];
        LatLng[] InBursa = new LatLng[3];
        LatLng[] InAntalya = new LatLng[3];
        LatLng[] InAdana = new LatLng[3];
        LatLng[] InKonya = new LatLng[2];
        LatLng[] InGaziantep = new LatLng[2];
        LatLng[] InSanliurfa = new LatLng[2];
        LatLng[] InMersin = new LatLng[2];
        LatLng[] InDiyarbakir = new LatLng[2];
        LatLng[] InKayseri = new LatLng[2];
        LatLng[] InEskisehir = new LatLng[2];
        LatLng[] InSamsun = new LatLng[2];
        LatLng[] InDenizli = new LatLng[2];
        LatLng[] InKahramanmaras = new LatLng[2];
        LatLng[] InMalatya = new LatLng[2];
        LatLng[] InErzurum = new LatLng[2];
        LatLng[] InVan = new LatLng[2];
        LatLng[] InBatman = new LatLng[2];
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                    Statement stmt = connection.createStatement();

                    statement = connection.createStatement();

                    String sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Istanbul'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);


                    int i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InIstanbul[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Ankara'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InAnkara[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////

                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Izmir'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InIzmir[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Bursa'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InBursa[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Antalya'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InAntalya[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Adana'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InAdana[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Konya'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InKonya[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Gaziantep'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InGaziantep[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Şanlıurfa'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InSanliurfa[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Mersin'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InMersin[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Diyarbakır'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InDiyarbakir[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Kayseri'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InKayseri[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Eskişehir'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InEskisehir[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Samsun'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InSamsun[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Denizli'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InDenizli[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Kahramanmaraş'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InKahramanmaras[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Malatya'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InMalatya[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Erzurum'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InErzurum[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Van'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InVan[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////
                    sql = "SELECT latitude, longitude FROM Locations WHERE city = 'Batman'";

                    // Sorguyu çalıştır ve sonuçları al
                    resultSet = statement.executeQuery(sql);

                    i=0;
                    while (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        InBatman[i] = new LatLng(latitude, longitude);
                        i++;
                    }
                    ////////////////////////////////////////////////////////////////////////


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


       LatLng[][] InAll = new LatLng[20][];
            InAll[0] = InIstanbul;
            InAll[1] = InAnkara;
            InAll[2] = InIzmir;
            InAll[3] = InBursa;
            InAll[4] = InAntalya;
            InAll[5] = InAdana;
            InAll[6] = InKonya;
            InAll[7] = InGaziantep;
            InAll[8] = InSanliurfa;
            InAll[9] = InMersin;
            InAll[10] = InDiyarbakir;
            InAll[11] = InKayseri;
            InAll[12] = InEskisehir;
            InAll[13] = InSamsun;
            InAll[14] = InDenizli;
            InAll[15] = InKahramanmaras;
            InAll[16] = InMalatya;
            InAll[17] = InErzurum;
            InAll[18] = InVan;
            InAll[19] = InBatman;

        //mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));

        if (map) {
            for (int i = 0; i < InAll.length - 1; i++) {

                String cityName = locations[i];

                if (i < 6) {
                    for (int j = 0; j < 3; j++) {
                        mMap.addMarker(new MarkerOptions().position(InAll[i][j]).title(branches[j] + ", " + cityName));
                    }
                }
                else {
                    for (int j = 0; j < 2; j++) {
                        mMap.addMarker(new MarkerOptions().position(InAll[i][j]).title(branches[j] + ", " + cityName));
                    }
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((centerAll[0]), 5));
        }

        else {

        for (int i = 0; i < latlnglist.length; i++) {
            if (activity_donate.getLocation().equalsIgnoreCase(locations[0]) ||
                activity_receive.getLocation().equalsIgnoreCase(locations[0])) {
                for (int j = 0; j < latlnglist.length - 1; j++) {
                    mMap.addMarker(new MarkerOptions().position(latlnglist[j]).title("Acceptance Point, " + locations[j+1]));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((centerAll[0]), 5));
            }
            else {
                if (activity_donate.getLocation().equalsIgnoreCase(locations[i]) ||
                    activity_receive.getLocation().equalsIgnoreCase(locations[i])) {
                    String cityName = locations[i];

                    if (i < 7) {
                        for (int j = 0; j < 3; j++) {
                            mMap.addMarker(new MarkerOptions().position(InAll[i - 1][j]).title(branches[j] + ", " + cityName));
                        }
                    }
                    else {
                        for (int j = 0; j < 2; j++) {
                            mMap.addMarker(new MarkerOptions().position(InAll[i - 1][j]).title(branches[j] + ", " + cityName));
                        }
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerAll[i], 10));
                }
            }
        }
        }
    }

    //For Current Location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
            else {
                Toast.makeText(this, "Location permission is denied, please allow the permission.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void setMapValueT() {
        map = true;
    }

    public static void setMapValueF() {
        map = false;
    }
}