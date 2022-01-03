package com.purnendu.senrysa.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.purnendu.senrysa.DataBase.Database;
import com.purnendu.senrysa.MainActivity;
import com.purnendu.senrysa.R;

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


        Database database =new Database(RecruiterRegistrationPage.this);

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

                if(database.isJobSeeker(email))
                {
                    Toast.makeText(RecruiterRegistrationPage.this, "You are a jobSeeker", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (database.insertRecruiterDetails(name, password, email)) {
                        Toast.makeText(RecruiterRegistrationPage.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RecruiterRegistrationPage.this, MainActivity.class);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(RecruiterRegistrationPage.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}