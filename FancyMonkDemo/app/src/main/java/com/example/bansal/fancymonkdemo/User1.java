package com.example.bansal.fancymonkdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;

public class User1 extends AppCompatActivity {
    Intent intent;
    TextView email;
    KenBurnsView kenBurnsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user1);
        intent = getIntent();
        email = (TextView)findViewById(R.id.email_user);
        String nameUSer = intent.getStringExtra("email");
        email.setText(nameUSer);
        kenBurnsView = (KenBurnsView) findViewById(R.id.ken);
    }
}
