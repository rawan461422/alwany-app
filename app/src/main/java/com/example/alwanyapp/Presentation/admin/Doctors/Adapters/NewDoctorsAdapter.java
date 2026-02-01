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

public class NewDoctorsAdapter extends RecyclerView.Adapter<NewDoctorsAdapter.helper> {
   onItemClickListener mListener;
    public interface onItemClickListener{
        void reject(int position);
        void accept(int position);
    }
    public void onItemClickListener(onItemClickListener mListener){
        this.mListener=mListener;
    }
    private ArrayList<DoctorDataClass> doctors;
    private Context context;

    public NewDoctorsAdapter(ArrayList<DoctorDataClass> doctors, Context context) {
        this.doctors = doctors;
        this.context = context;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_new_docotrs_adapter,parent,false);

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

        holder.reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.reject(position);
            }
        });
        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.accept(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class helper extends RecyclerView.ViewHolder{
        TextView userName,email,phone,category;
        Button reject_btn,accept_btn;
        ImageView profile;
        public helper(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            email=itemView.findViewById(R.id.item_doctor_email);
            phone=itemView.findViewById(R.id.item_doctor_phone);
            category=itemView.findViewById(R.id.item_doctor_category);
            reject_btn =itemView.findViewById(R.id.reject_btn);
            accept_btn =itemView.findViewById(R.id.accept_btn);
            profile=itemView.findViewById(R.id.image);

        }
    }


}
