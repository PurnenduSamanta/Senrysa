package com.purnendu.senrysa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.purnendu.senrysa.Adapter.AppliedCandidateListAdapter;
import com.purnendu.senrysa.Adapter.JobShowAdapter;
import com.purnendu.senrysa.DataBase.Database;
import com.purnendu.senrysa.Model.JobSeekerDetails;

import java.util.ArrayList;
import java.util.Objects;

public class AppliedCandidatesList extends AppCompatActivity {


    RecyclerView recyclerView;
    AppliedCandidateListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_candidates_list);

        Database database=new Database(AppliedCandidatesList.this);
        recyclerView=findViewById(R.id.candidateListRecyclerView);
        Intent intent=getIntent();
        if(intent==null)
            return;
        Bundle bundle = intent.getExtras();
        int jobId = bundle.getInt("JOB ID");
        String jobTitle=bundle.getString("JOB TITLE");
        if(jobTitle==null || jobTitle.isEmpty())
            return;
        Objects.requireNonNull(getSupportActionBar()).setTitle(jobTitle);

        ArrayList<JobSeekerDetails>list=database.getAppliedJobSeeker(jobId);
        if(list==null)
            return;
        if(list.isEmpty())
            Toast.makeText(AppliedCandidatesList.this, "No application found", Toast.LENGTH_SHORT).show();

        adapter= new AppliedCandidateListAdapter(list,AppliedCandidatesList.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}