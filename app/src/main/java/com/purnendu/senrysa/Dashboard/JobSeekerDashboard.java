package com.purnendu.senrysa.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import com.purnendu.senrysa.Adapter.JobShowAdapter;
import com.purnendu.senrysa.DataBase.Database;
import com.purnendu.senrysa.Model.JobModel;
import com.purnendu.senrysa.R;
import java.util.ArrayList;
import java.util.Objects;

public class JobSeekerDashboard extends AppCompatActivity {

    RecyclerView jobSeekerRecyclerView;
    JobShowAdapter  adapter;
    EditText searchEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_dashboard);

        jobSeekerRecyclerView=findViewById(R.id.jobSeekerRecyclerView);
        searchEditText=findViewById(R.id.searchEditText);
        Database db=new Database(JobSeekerDashboard.this);

        Intent intent=getIntent();
        if(intent==null)
            return;
        Bundle bundle = intent.getExtras();
        String jobSeekerEmail = bundle.getString("Job Seeker Email");
        if(jobSeekerEmail==null)
            return;
        if(jobSeekerEmail.isEmpty())
            return;

        String jobSeekerName=db.getJobSeekerNameFromEmail(jobSeekerEmail);
        if(jobSeekerName==null || jobSeekerName.isEmpty())
            return;
        Objects.requireNonNull(getSupportActionBar()).setTitle("Hi "+jobSeekerName);


        int jobSeekerId=db.getJobSeekerIdFromEmail(jobSeekerEmail);
        if(jobSeekerId==-1)
            return;



            ArrayList<JobModel> jobList = db.getAllJobPostFromUser();
            if(!jobList.isEmpty())
            {
                adapter= new JobShowAdapter(this,jobList,"JOB SEEKER DASHBOARD",jobSeekerId);
                jobSeekerRecyclerView.setAdapter(adapter);
                jobSeekerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            else
            {
                Toast.makeText(JobSeekerDashboard.this, "No jobs found", Toast.LENGTH_SHORT).show();
            }




        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                String search=s.toString().trim();
                ArrayList<JobModel> jobList = db.getAllJobPostBySearch(search);
                if(!jobList.isEmpty())
                {
                    adapter= new JobShowAdapter(JobSeekerDashboard.this,jobList,"JOB SEEKER DASHBOARD",jobSeekerId);
                    jobSeekerRecyclerView.setAdapter(adapter);
                    jobSeekerRecyclerView.setLayoutManager(new LinearLayoutManager(JobSeekerDashboard.this));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
        super.onBackPressed();
    }
}