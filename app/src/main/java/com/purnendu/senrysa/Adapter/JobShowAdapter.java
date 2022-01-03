package com.purnendu.senrysa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.purnendu.senrysa.AppliedCandidatesList;
import com.purnendu.senrysa.DataBase.Database;
import com.purnendu.senrysa.Model.JobModel;
import com.purnendu.senrysa.R;
import java.util.ArrayList;

public class JobShowAdapter extends RecyclerView.Adapter<JobShowAdapter.MyViewHolder>{


    private final ArrayList<JobModel>jobs;
    private final LayoutInflater layoutInflater;
    private final String fromDashboard;
    private final int userId;
    private final Context context;
    private final Database database;


    public JobShowAdapter(Context context, ArrayList<JobModel> jobs,String fromDashboard,int userId) {
        this.context=context;
        this.jobs = jobs;
        this.fromDashboard=fromDashboard;
        this.userId=userId;
        layoutInflater= LayoutInflater.from(context);
        database=new Database(context);
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

        int jobId=jobs.get(position).getJobId();
        String jobTitle=jobs.get(position).getTitle();



        holder.single_card_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fromDashboard!=null && !fromDashboard.isEmpty())
                {
                    if(fromDashboard.equals("JOB SEEKER DASHBOARD"))
                    {

                        AlertDialog.Builder alertDialog= new AlertDialog.Builder(context);
                        LayoutInflater inflater=LayoutInflater.from(context);
                        View myView= inflater.inflate(R.layout.job_application_dialog,null);
                        alertDialog.setView(myView);
                        AlertDialog dialog=alertDialog.create();

                        TextView title=myView.findViewById(R.id.wantToApplyText);
                        Button apply=myView.findViewById(R.id.applyJobButton);
                        Button cancel=myView.findViewById(R.id.cancelApplyButton);
                        if(database.isAlreadyApplied(jobId,userId))
                        {
                            title.setText("Already Applied for this job");
                        }
                        else
                        {
                            title.setText("Do you want to apply?");
                            apply.setVisibility(View.VISIBLE);
                            cancel.setVisibility(View.VISIBLE);
                        }
                        dialog.show();
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        apply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(database.applyForJob(jobId,userId))
                                {
                                    Toast.makeText(context, "Successfully applied", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }


                            }
                        });
                    }
                    else if(fromDashboard.equals("RECRUITER DASHBOARD"))
                    {
                        Intent intent =new Intent(context, AppliedCandidatesList.class);
                        intent.putExtra("JOB ID",jobId);
                        intent.putExtra("JOB TITLE",jobTitle);
                        context.startActivity(intent);
                    }
                }
            }
        });





    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,jd,ss1,ss2,department,experience;
        CardView single_card_dashboard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.jobTitleTextView);
            jd=itemView.findViewById(R.id.jdTextView);
            ss1=itemView.findViewById(R.id.ss1TextView);
            ss2=itemView.findViewById(R.id.ss2TextView);
            department=itemView.findViewById(R.id.departmentTextView);
            experience=itemView.findViewById(R.id.experienceTextView);
            single_card_dashboard=itemView.findViewById(R.id.single_card_dashboard);
        }
    }
}
