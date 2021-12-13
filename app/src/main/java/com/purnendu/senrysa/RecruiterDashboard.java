package com.purnendu.senrysa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.purnendu.senrysa.Adapter.JobShowAdapter;
import com.purnendu.senrysa.DataBase.RecruiterRegistrationDb;
import com.purnendu.senrysa.Model.JobModel;

import java.util.ArrayList;

public class RecruiterDashboard extends AppCompatActivity {

    FloatingActionButton createJobPost;
    RecyclerView allJobRecyclerView;
    JobShowAdapter  adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_dashboard);

        createJobPost=findViewById(R.id.createJobPost_fab);
        allJobRecyclerView=findViewById(R.id.allJobRecyclerView);

        RecruiterRegistrationDb db=new RecruiterRegistrationDb(RecruiterDashboard.this);
        Intent intent = getIntent();
        // check intent is null or not
        if(intent != null) {
            String email = intent.getStringExtra("Recruiter Email");
            ArrayList<JobModel> jobList = db.getAllJobPost(email);
            if(!jobList.isEmpty())
            {
                 adapter= new JobShowAdapter(this,jobList);
                allJobRecyclerView.setAdapter(adapter);
                allJobRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            else
            {
                Toast.makeText(RecruiterDashboard.this, "No jobs found", Toast.LENGTH_SHORT).show();
            }


        }








        createJobPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog= new AlertDialog.Builder(RecruiterDashboard.this);
                LayoutInflater inflater=LayoutInflater.from(RecruiterDashboard.this);
                View myView= inflater.inflate(R.layout.job_post_form,null);
                alertDialog.setView(myView);
                AlertDialog dialog=alertDialog.create();
               // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                EditText  jobTitleEditText,jobDescriptionEditText,skillSet1EditText,skillSet2EditText,departmentEditText,experienceEditText;
                Button postButton;

                jobTitleEditText=myView.findViewById(R.id.jobTitleEditText);
                jobDescriptionEditText=myView.findViewById(R.id.jobDescriptionEditText);
                skillSet1EditText=myView.findViewById(R.id.skillSet1EditText);
                skillSet2EditText=myView.findViewById(R.id.skillSet2EditText);
                departmentEditText=myView.findViewById(R.id.departmentEditText);
                experienceEditText=myView.findViewById(R.id.experienceEditText);
                postButton=myView.findViewById(R.id.postButton);


                postButton.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View view) {

                        String jobTitle=jobTitleEditText.getText().toString().trim();
                        String jobDescription=jobDescriptionEditText.getText().toString().trim();
                        String skillSet1=skillSet1EditText.getText().toString().trim();
                        String skillSet2=skillSet2EditText.getText().toString().trim();
                        String department=departmentEditText.getText().toString().trim();
                        String experience=experienceEditText.getText().toString().trim();


                        if(jobTitle.isEmpty())
                        {
                            jobDescriptionEditText.setError("JobTitle is required");
                            return;
                        }

                        if(jobDescription.isEmpty())
                        {
                            jobDescriptionEditText.setError("JobDescription is required");
                            return;
                        }

                        if(skillSet1.isEmpty())
                        {
                            skillSet1EditText.setError("Skill set is required");
                            return;
                        }

                        if(skillSet2.isEmpty())
                        {
                            skillSet2EditText.setError("Skill set is required");
                            return;
                        }

                        if(department.isEmpty())
                        {
                            departmentEditText.setError("Department is required");
                            return;
                        }

                        if(experience.isEmpty())
                        {
                            experienceEditText.setError("Experience is required");
                            return;
                        }

                        Intent intent = getIntent();
                        // check intent is null or not
                        if(intent != null){
                            String email = intent.getStringExtra("Recruiter Email");
                            if(db.insertJobDetails(email,jobTitle,jobDescription,skillSet1,skillSet2,department,Integer.parseInt(experience)))
                            {
                                Toast.makeText(RecruiterDashboard.this, "post created", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                recreate();

                            }
                            else
                            {
                                Toast.makeText(RecruiterDashboard.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(RecruiterDashboard.this, "Recruiter Email Intent is null", Toast.LENGTH_SHORT).show();
                        }






                    }
                });



            }
        });

    }
    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}