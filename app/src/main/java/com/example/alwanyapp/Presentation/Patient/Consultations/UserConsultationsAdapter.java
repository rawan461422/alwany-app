package com.example.alwanyapp.Presentation.Patient.Consultations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwanyapp.Domain.ConsultationsDataClass;
import com.example.alwanyapp.R;

import java.util.ArrayList;

public class UserConsultationsAdapter extends RecyclerView.Adapter<UserConsultationsAdapter.helper> {
    onItemClickListener mListener;

    public interface onItemClickListener {
        void replay(int position);
    }

    public void onItemClickListener(onItemClickListener mListener) {
        this.mListener = mListener;
    }

    private ArrayList<ConsultationsDataClass> arrayList;
    private Context context;

    public UserConsultationsAdapter(ArrayList<ConsultationsDataClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_consultations, parent, false);

        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.content.setText(arrayList.get(position).getContent());
        holder.date.setText(arrayList.get(position).getDate());
        holder.replay.setOnClickListener(new View.OnClickListener() {
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
        TextView title, content, date;
        Button replay;

        public helper(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
            replay = itemView.findViewById(R.id.replay);
        }
    }


}
