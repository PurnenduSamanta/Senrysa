package com.purnendu.senrysa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.purnendu.senrysa.DataBase.RecruiterRegistrationDb;

import java.util.Locale;

public class RecruiterLoginActivity extends AppCompatActivity {


    EditText emailLoginRecruiterEditText,passwordLoginRecruiterEditText;
    Button  loginRecruiterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_login);

        emailLoginRecruiterEditText=findViewById(R.id.emailLoginRecruiterEditText);
        passwordLoginRecruiterEditText=findViewById(R.id.passwordLoginRecruiterEditText);
        loginRecruiterButton=findViewById(R.id.loginRecruiterButton);

        RecruiterRegistrationDb db=new RecruiterRegistrationDb(RecruiterLoginActivity.this);

        loginRecruiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=emailLoginRecruiterEditText.getText().toString().trim();
                String password=passwordLoginRecruiterEditText.getText().toString().trim();

                if(email.isEmpty())
                {
                    emailLoginRecruiterEditText.setError("Email is required");
                    return;
                }

                if(password.isEmpty())
                {
                    passwordLoginRecruiterEditText.setError("Password is required");
                    return;
                }

                Intent intent=getIntent();
                if(intent != null) {
                    Bundle bundle = intent.getExtras();
                    int type=bundle.getInt("type");


                    if (db.validateEmailAddress(email, password)) {

                        //System.out.println(db.isRecruiter(email));
                        Intent i=null;
                        if(type==10)
                        {
                             i = new Intent(RecruiterLoginActivity.this, RecruiterDashboard.class);
                            i.putExtra("Recruiter Email", email);
                        }
                        else if(type==20)
                        {
                            i = new Intent(RecruiterLoginActivity.this, JobSeekerDashboard.class);
                            i.putExtra("Recruiter Email", email);
                        }

                        startActivity(i);
                    } else {
                        Toast.makeText(RecruiterLoginActivity.this, "Something Wrong Happened", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

}