package com.purnendu.senrysa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button loginAsRecruiter,loginAsJobSeeker;
    TextView registration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginAsRecruiter=findViewById(R.id.loginAsRecruiter_button);
        loginAsJobSeeker=findViewById(R.id.loginAsJobSeeker_button);
        registration=findViewById(R.id.registration);

        //For logging user
        loginAsJobSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RecruiterLoginActivity.class);
                intent.putExtra("type",20);
                startActivity(intent);
            }
        });


        //For logging Admin
        loginAsRecruiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,RecruiterLoginActivity.class);
                intent.putExtra("type",10);
                startActivity(intent);
            }
        });

        //Registration
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this, RegistrationAs.class);
                startActivity(intent);
            }
        });



    }
}