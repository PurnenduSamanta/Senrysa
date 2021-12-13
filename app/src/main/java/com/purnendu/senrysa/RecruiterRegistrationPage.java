package com.purnendu.senrysa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.purnendu.senrysa.DataBase.RecruiterRegistrationDb;

public class RecruiterRegistrationPage extends AppCompatActivity {



    EditText nameEditText,passwordEditText,emailEditText;
    Button  RegisteredButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_registration_page);

        nameEditText=findViewById(R.id.name_editText);
        passwordEditText=findViewById(R.id.password_editText);
        emailEditText=findViewById(R.id.email_EditText);
        RegisteredButton=findViewById(R.id.registered_button);


        RecruiterRegistrationDb recruiterRegistrationDb=new RecruiterRegistrationDb(RecruiterRegistrationPage.this);

        //Doing Registration
        RegisteredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name=nameEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();
                String email=emailEditText.getText().toString().trim();


                if(name.isEmpty())
                {
                    nameEditText.setError("Name is required");
                    return;
                }

                if(password.isEmpty())
                {
                    passwordEditText.setError("Password is required");
                    return;
                }

                if(email.isEmpty())
                {
                    emailEditText.setError("Email is required");
                    return;
                }


                Intent intent = getIntent();
                // check intent is null or not
                if(intent != null) {
                    int type = intent.getIntExtra("type", 0);

                    if (recruiterRegistrationDb.insertRecruiterDetails(name, password, email, type)) {
                        Toast.makeText(RecruiterRegistrationPage.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RecruiterRegistrationPage.this, MainActivity.class);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(RecruiterRegistrationPage.this, "UnSuccessful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RecruiterRegistrationPage.this, "intent is null", Toast.LENGTH_SHORT).show();
                }













            }
        });




    }
}