package com.purnendu.senrysa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purnendu.senrysa.Model.JobModel;
import com.purnendu.senrysa.R;

import java.util.ArrayList;

public class JobShowAdapter extends RecyclerView.Adapter<JobShowAdapter.MyViewHolder>{


    private final Context c;
    private ArrayList<JobModel>jobs=new ArrayList<>();
    private final LayoutInflater layoutInflater;


    public JobShowAdapter(Context c, ArrayList<JobModel> jobs) {
        this.c = c;
        this.jobs = jobs;
        layoutInflater= LayoutInflater.from(this.c);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview=layoutInflater.inflate(R.layout.single_job_post_recruiter_page,parent,false);
        JobShowAdapter.MyViewHolder newholder= new MyViewHolder(myview);
        return newholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(jobs.get(position).getTitle());
        holder.jd.setText(jobs.get(position).getJd());
        holder.department.setText(jobs.get(position).getDepartment());
        holder.ss1.setText(jobs.get(position).getSs1());
        holder.ss2.setText(jobs.get(position).getSs2());
        String experience=String.valueOf(jobs.get(position).getExperience());
        holder.experience.setText(experience);
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,jd,ss1,ss2,department,experience;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.jobTitleTextView);
            jd=itemView.findViewById(R.id.jdTextView);
            ss1=itemView.findViewById(R.id.ss1TextView);
            ss2=itemView.findViewById(R.id.ss2TextView);
            department=itemView.findViewById(R.id.departmentTextView);
            experience=itemView.findViewById(R.id.experienceTextView);
        }
    }
}
