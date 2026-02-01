package com.example.alwanyapp.Presentation.Patient.Doctors;

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

public class UserDoctorsAdapter extends RecyclerView.Adapter<UserDoctorsAdapter.helper> {
   onItemClickListener mListener;
    public interface onItemClickListener{
        void chat(int position);
        void consultations(int position);
    }
    public void onItemClickListener(onItemClickListener mListener){
        this.mListener=mListener;
    }
    private ArrayList<DoctorDataClass> doctors;
    private Context context;

    public UserDoctorsAdapter(ArrayList<DoctorDataClass> doctors, Context context) {
        this.doctors = doctors;
        this.context = context;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_doctors,parent,false);

        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, @SuppressLint("RecyclerView") int position) {
        holder.userName.setText(doctors.get(position).getName());
        holder.category.setText(doctors.get(position).getCategory());
        holder.phone.setText(doctors.get(position).getPhone());
        if (! doctors.get(position).getImage().isEmpty())
        {
            Glide.with(context).load(doctors.get(position).getImage()).into(holder.profile);
        }
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.chat(position);
            }
        });
        holder.consultations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.consultations(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class helper extends RecyclerView.ViewHolder{
        TextView userName,phone,category;
        Button chat,consultations;
        ImageView profile;
        public helper(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            phone=itemView.findViewById(R.id.phone);
            category=itemView.findViewById(R.id.item_doctor_category);
            chat =itemView.findViewById(R.id.chat);
            consultations =itemView.findViewById(R.id.consultations);
            profile=itemView.findViewById(R.id.image);

        }
    }


}
