package com.example.bansal.fancymonkdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;

import de.hdodenhof.circleimageview.CircleImageView;

public class User extends AppCompatActivity {
    Intent intent;
    KenBurnsView kenBurnsView;
    TextView name;

    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        kenBurnsView =(KenBurnsView) findViewById(R.id.ken);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        intent = getIntent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        String nameUser = intent.getStringExtra("data1");
        name.setText(nameUser);
        String emailUser = intent.getStringExtra("data");
        email.setText(emailUser);
    }
}
