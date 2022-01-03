package com.purnendu.senrysa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.purnendu.senrysa.Dashboard.JobSeekerDashboard;
import com.purnendu.senrysa.Dashboard.RecruiterDashboard;
import com.purnendu.senrysa.DataBase.Database;

public class LoginActivity extends AppCompatActivity {


    EditText emailLoginRecruiterEditText, passwordLoginRecruiterEditText;
    Button loginRecruiterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_login);

        emailLoginRecruiterEditText = findViewById(R.id.emailLoginRecruiterEditText);
        passwordLoginRecruiterEditText = findViewById(R.id.passwordLoginRecruiterEditText);
        loginRecruiterButton = findViewById(R.id.loginRecruiterButton);

        Database database = new Database(LoginActivity.this);


        loginRecruiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailLoginRecruiterEditText.getText().toString().trim();
                String password = passwordLoginRecruiterEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    emailLoginRecruiterEditText.setError("Email is required");
                    return;
                }

                if (password.isEmpty()) {
                    passwordLoginRecruiterEditText.setError("Password is required");
                    return;
                }

                Intent intent = getIntent();
                if (intent != null) {
                    Bundle bundle = intent.getExtras();
                    String type = bundle.getString("type");

                    if (type != null && !type.isEmpty()) {
                        if (type.equals("RECRUITER")) {
                            if (database.validateRecruiter(email, password)) {

                                if (!(database.validateJobSeeker(email, password))) {
                                    Intent i = new Intent(LoginActivity.this, RecruiterDashboard.class);
                                    i.putExtra("Recruiter Email", email);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Pls login as recruiter", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Give correct credentials", Toast.LENGTH_SHORT).show();
                            }
                        } else if (type.equals("JOB SEEKER")) {
                            if (database.validateJobSeeker(email, password)) {

                                if (!(database.validateRecruiter(email, password))) {
                                    Intent i = new Intent(LoginActivity.this, JobSeekerDashboard.class);
                                    i.putExtra("Job Seeker Email", email);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Pls login as user", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Give correct credentials", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Type has some problem", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });
    }

}