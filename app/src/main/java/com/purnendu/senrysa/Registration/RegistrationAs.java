package com.purnendu.senrysa.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.purnendu.senrysa.R;

public class RegistrationAs extends AppCompatActivity {

    Button RegisterAsRecruiter,RegisterAsJobSeeker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraion_as);
        RegisterAsJobSeeker=findViewById(R.id.RegisterAsJobSeeker_button);
        RegisterAsRecruiter=findViewById(R.id.RegisterAsRecruiter_button);

        //opening registrationPage of recruiter
        RegisterAsRecruiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(RegistrationAs.this, RecruiterRegistrationPage.class);
                startActivity(intent);
            }
        });

        //opening registrationPage of jobSeeker
        RegisterAsJobSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(RegistrationAs.this,JobSeekerRegistrationPage.class);
                startActivity(intent);

            }
        });
    }
}