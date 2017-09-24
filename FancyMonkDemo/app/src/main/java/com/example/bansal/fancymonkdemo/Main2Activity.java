package com.example.bansal.fancymonkdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    TextView btn1;
    TextView btn2;
    Point p;
    RelativeLayout relativeLayout;
    String emailuser1;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn1 = (TextView) findViewById(R.id.pass_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p != null)
                    showPopup(Main2Activity.this, p);
            }
        });
        btn2 =(TextView) findViewById(R.id.otp_btn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p != null)
                    showPopup1(Main2Activity.this, p);
            }
        });
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_blur);
        intent1 = getIntent();
        emailuser1 = intent1.getStringExtra("data");
    }

    @Override
    protected void onResume() {
        super.onResume();
        relativeLayout.setVisibility(View.GONE);
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];


        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        btn1.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }

    private void showPopup(final Main2Activity context, Point p) {
        int popupWidth = 1000;
        int popupHeight = 850;


        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.activity_password_otp, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        popup.setElevation(50);
        popup.setOutsideTouchable(false);
        relativeLayout.setVisibility(View.VISIBLE);
        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 100;
        int OFFSET_Y = 130;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0,0);
        final TextInputLayout editText =(TextInputLayout) layout.findViewById(R.id.password);
        editText.setVisibility(View.VISIBLE);
        Button btn2 =(Button) layout.findViewById(R.id.pass_sub_btn);
        btn2.setVisibility(View.VISIBLE);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = editText.getEditText().getText().toString().trim();
                if(!pass.matches("")) {
                    popup.dismiss();
                    relativeLayout.setVisibility(View.GONE);
                    Intent intent = new Intent(Main2Activity.this,User1.class);
                    intent.putExtra("email",emailuser1);
                    startActivity(intent);
                }
                else {
                    LayoutInflater li = getLayoutInflater();
                    //Getting the View object as defined in the customtoast.xml file
                    View layout = li.inflate(R.layout.toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));


                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Please Enter Password");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 0, 150);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

    }
    private void showPopup1(final Main2Activity context, Point p) {
        int popupWidth = 1000;
        int popupHeight = 850;

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.activity_password_otp, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        popup.setElevation(50);
        popup.setOutsideTouchable(false);
        relativeLayout.setVisibility(View.VISIBLE);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 100;
        int OFFSET_Y = 130;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0,0);
        final TextInputLayout editText =(TextInputLayout) layout.findViewById(R.id.otp);
        editText.setVisibility(View.VISIBLE);
        Button btn2 =(Button) layout.findViewById(R.id.pass_sub_btn1);
        btn2.setVisibility(View.VISIBLE);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = editText.getEditText().getText().toString().trim();
                if(!otp.matches("")){
                popup.dismiss();
                relativeLayout.setVisibility(View.GONE);
                    Intent intent = new Intent(Main2Activity.this,User1.class);
                    intent.putExtra("email",emailuser1);
                    startActivity(intent);
                }

                else {
                    LayoutInflater li = getLayoutInflater();
                    //Getting the View object as defined in the customtoast.xml file
                    View layout = li.inflate(R.layout.toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));


                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Please Enter Otp");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 0, 150);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

    }

}
