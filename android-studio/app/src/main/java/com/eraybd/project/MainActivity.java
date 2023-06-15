package com.eraybd.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static String userId;
    public static String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView_name = findViewById(R.id.tv_topleft3);

        // Kullanıcının ID'sini al
        userId = getIntent().getStringExtra("userID");
        userName = getIntent().getStringExtra("userName");

        // ID'yi kullanarak istediğiniz işlemleri gerçekleştirin
        if (userId != null) {
            textView_name.setText(userName);
        }



        ImageView imageView_donates = findViewById(R.id.box_image4);
        ImageView imageView_donate = findViewById(R.id.box_image1);
        ImageView imageView_request = findViewById(R.id.box_image2);

        ImageView imageView_map = findViewById(R.id.box_image5);
        ImageView imageView_contact = findViewById(R.id.box_image6);

        imageView_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), activity_donate.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imageView_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), activity_contact.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imageView_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), activity_request.class);
                    intent.putExtra("userID",userId);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        imageView_donates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), BagislarActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        imageView_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    activity_gmap.setMapValueT();
                    Intent intent = new Intent(getApplicationContext(), activity_gmap.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public static void setUserID(String sItem) {
        userId = sItem;
    }
    public static String getUserID() {return userId;}

    public static void setUserName(String sItem) {
        userName = sItem;
    }
    public static String getUserName() {return userName;}


}