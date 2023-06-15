package com.eraybd.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.eraybd.project.databinding.ActivityRequestDetailedBinding;

public class activity_request_detailed extends AppCompatActivity {

    ActivityRequestDetailedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        if (intent != null) {

            //activity_request sayfasindan kopyalanmis String dizileri degiskene atanir ve set edilir
            String name = intent.getStringExtra("donation_type");
            String donation_qua = intent.getStringExtra("donation_qua");
            String donation_desc = intent.getStringExtra("donation_desc");
            String cities = intent.getStringExtra("city");
            String branches = intent.getStringExtra("branch");
            Integer delivery_id = intent.getIntExtra("delivery_id",0);
            String date = intent.getStringExtra("date_of_receipt");

            binding.donatetypeName.setText(name);
            binding.quantityName.setText(donation_qua);
            binding.includesName.setText(donation_desc);
            binding.cityName.setText(cities);
            binding.branchName.setText(branches);
            binding.deliveryId.setText(delivery_id.toString());
            binding.deliveryId2.setText(date);
        }
    }
}