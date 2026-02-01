package com.example.alwanyapp.Presentation.auth.Informations;

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
import com.example.alwanyapp.Domain.InfoDataClass;
import com.example.alwanyapp.R;

import java.util.ArrayList;

public class UserInformationsAdapter extends RecyclerView.Adapter<UserInformationsAdapter.helper>{
    private onItemClickListener mListener;
    public interface onItemClickListener
    {
        void View(int position);
    }
    public void setOnItemClick(onItemClickListener mListener)
    {
        this.mListener=mListener;
    }
    private ArrayList<InfoDataClass> arrayList;
    private Context context;

    public UserInformationsAdapter(ArrayList<InfoDataClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_info,parent,false);

        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(arrayList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.View(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class helper extends RecyclerView.ViewHolder{
        TextView name;
        public helper(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.info1);
        }
    }
}
