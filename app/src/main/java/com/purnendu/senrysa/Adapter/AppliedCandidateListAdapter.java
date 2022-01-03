package com.purnendu.senrysa.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.purnendu.senrysa.Model.JobSeekerDetails;
import com.purnendu.senrysa.R;
import java.util.ArrayList;

public class AppliedCandidateListAdapter extends RecyclerView.Adapter<AppliedCandidateListAdapter.MyViewHolder>{

    private final ArrayList<JobSeekerDetails>arrayList;
    private final LayoutInflater layoutInflater;
    private final Context context;


    public AppliedCandidateListAdapter(ArrayList<JobSeekerDetails> arrayList, Context context) {
        this.arrayList = arrayList;
        layoutInflater= LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AppliedCandidateListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview=layoutInflater.inflate(R.layout.single_applied_candidate,parent,false);
        AppliedCandidateListAdapter.MyViewHolder newholder= new AppliedCandidateListAdapter.MyViewHolder(myview);
        return newholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.appliedCandidateNameTextView.setText(arrayList.get(position).getName());
        Bitmap bm = BitmapFactory.decodeByteArray(arrayList.get(position).getCv(), 0, arrayList.get(position).getCv().length);
        holder.cvImageView.setImageBitmap(bm);

        holder.cvImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView appliedCandidateNameTextView;
        CardView cardView;
        ImageView cvImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            appliedCandidateNameTextView=itemView.findViewById(R.id.appliedCandidateNameTextView);
            cvImageView=itemView.findViewById(R.id.cvImageView);
            cardView=itemView.findViewById(R.id.single_card_applied_candidate);
        }
    }
}
