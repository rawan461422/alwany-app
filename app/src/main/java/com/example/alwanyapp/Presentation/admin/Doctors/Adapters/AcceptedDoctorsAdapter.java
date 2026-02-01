package com.example.alwanyapp.Presentation.admin.Doctors.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.alwanyapp.Domain.DoctorDataClass;
import com.example.alwanyapp.R;

import java.util.ArrayList;

public class AcceptedDoctorsAdapter extends RecyclerView.Adapter<AcceptedDoctorsAdapter.helper> {
   onItemClickListener mListener;
    public interface onItemClickListener{
        void block(int position);
    }
    public void onItemClickListener(onItemClickListener mListener){
        this.mListener=mListener;
    }
    private ArrayList<DoctorDataClass> doctors;
    private Context context;

    public AcceptedDoctorsAdapter(ArrayList<DoctorDataClass> doctors, Context context) {
        this.doctors = doctors;
        this.context = context;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_accepted_doctors,parent,false);

        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, @SuppressLint("RecyclerView") int position) {
        holder.userName.setText(doctors.get(position).getName());
        holder.email.setText(doctors.get(position).getEmail());
        holder.phone.setText(doctors.get(position).getPhone());
        if (! doctors.get(position).getImage().isEmpty())
        {
            Glide.with(context).load(doctors.get(position).getImage()).into(holder.profile);
        }
        if (doctors.get(position).getStatus().equals("block"))
        {
            holder.block.setText("فك الحظر");
        }
        else{
            holder.block.setText("حظر");
        }

        holder.block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.block(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class helper extends RecyclerView.ViewHolder{
        TextView userName,email,phone,category;
        Button block;
        ImageView profile;
        public helper(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.item_doctor_email);
            phone=itemView.findViewById(R.id.item_doctor_phone);
            category=itemView.findViewById(R.id.item_doctor_category);
            block=itemView.findViewById(R.id.block);
            profile=itemView.findViewById(R.id.image);

        }
    }


}
