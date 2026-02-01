package com.example.alwanyapp.Presentation.Doctor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alwanyapp.Domain.ConsultationsDataClass;
import com.example.alwanyapp.R;

import java.util.ArrayList;

public class DoctorConsultationsAdapter extends RecyclerView.Adapter<DoctorConsultationsAdapter.helper> {
    onItemClickListener mListener;

    public interface onItemClickListener {
        void replay(int position);
    }

    public void onItemClickListener(onItemClickListener mListener) {
        this.mListener = mListener;
    }

    private ArrayList<ConsultationsDataClass> arrayList;
    private Context context;

    public DoctorConsultationsAdapter(ArrayList<ConsultationsDataClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_consultations_adapter, parent, false);

        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.userName.setText(arrayList.get(position).getUserName());
        holder.date.setText(arrayList.get(position).getDate());
        if (!arrayList.get(position).getUserImage().isEmpty()) {
            Glide.with(context).load(arrayList.get(position).getUserImage()).into(holder.image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.replay(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class helper extends RecyclerView.ViewHolder {
        TextView title, userName, date;
        ImageView image;

        public helper(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            userName = itemView.findViewById(R.id.userName);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
        }
    }


}
