package com.purnendu.senrysa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.purnendu.senrysa.Adapter.JobShowAdapter;
import com.purnendu.senrysa.DataBase.RecruiterRegistrationDb;
import com.purnendu.senrysa.Model.JobModel;

import java.util.ArrayList;

public class JobSeekerDashboard extends AppCompatActivity {

    RecyclerView jobSeekerRecyclerView;
    JobShowAdapter  adapter;
    EditText searchEditText;
    ImageView searchImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_dashboard);

        jobSeekerRecyclerView=findViewById(R.id.jobSeekerRecyclerView);
        searchEditText=findViewById(R.id.searchEditText);
        searchImageView=findViewById(R.id.searchImageView);
        RecruiterRegistrationDb db=new RecruiterRegistrationDb(JobSeekerDashboard.this);

        Intent intent = getIntent();
        // check intent is null or not
        if(intent != null) {
            ArrayList<JobModel> jobList = db.getAllJobPostFromUser();
            if(!jobList.isEmpty())
            {
                adapter= new JobShowAdapter(this,jobList);
                jobSeekerRecyclerView.setAdapter(adapter);
                jobSeekerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            else
            {
                Toast.makeText(JobSeekerDashboard.this, "No jobs found", Toast.LENGTH_SHORT).show();
            }
        }


        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search=searchEditText.getText().toString().trim();
                if(search.isEmpty())
                {
                    searchEditText.setError("Nothing Found");
                    return;
                }

                Intent intent = getIntent();
                // check intent is null or not
                if(intent != null) {
                    ArrayList<JobModel> jobList = db.getAllJobPostBySearch(search);
                    if(!jobList.isEmpty())
                    {
                        adapter= new JobShowAdapter(JobSeekerDashboard.this,jobList);
                        jobSeekerRecyclerView.setAdapter(adapter);
                        jobSeekerRecyclerView.setLayoutManager(new LinearLayoutManager(JobSeekerDashboard.this));
                    }
                    else
                    {
                        Toast.makeText(JobSeekerDashboard.this, "No jobs found", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
        super.onBackPressed();
    }
}