package com.example.alwanyapp.Presentation.admin.Hospitals;

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
import com.example.alwanyapp.Domain.HelperAppsDataClass;
import com.example.alwanyapp.Domain.HospitalDataClass;
import com.example.alwanyapp.R;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.helper>{
    private onItemClickListener mListener;
    public interface onItemClickListener
    {
        void delete(int position);
        void location(int position);
    }
    public void setOnItemClick(onItemClickListener mListener)
    {
        this.mListener=mListener;
    }
    private ArrayList<HospitalDataClass> arrayList;
    private Context context;

    public HospitalAdapter(ArrayList<HospitalDataClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_hospitals_adapter,parent,false);

        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.phone.setText(arrayList.get(position).getPhoneNumber());
        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.image);
        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.location(position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class helper extends RecyclerView.ViewHolder{
        TextView name,phone;
        ImageView image,location;
        Button delete;
        public helper(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            image =itemView.findViewById(R.id.image);
            location =itemView.findViewById(R.id.location);
            delete =itemView.findViewById(R.id.delete);
        }
    }
}
