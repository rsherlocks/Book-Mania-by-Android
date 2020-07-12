package com.example.androidshaper.book_sell.sampledata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.androidshaper.book_sell.R;

public class PayProceessActivity extends AppCompatActivity {
    TextView textViewPay;
    Bundle bundleA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_proceess);
        textViewPay=findViewById(R.id.textViewPaying);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        this.bundleA=bundle;
        ShowDetails();

    }

    private void ShowDetails() {
        textViewPay.setText(bundleA.getString("name")+bundleA.getString("adress")+bundleA.getString("phone"));
    }
}