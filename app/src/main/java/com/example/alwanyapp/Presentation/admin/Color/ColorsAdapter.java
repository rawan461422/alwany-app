package com.example.alwanyapp.Presentation.admin.Color;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alwanyapp.Domain.ColorsDataClass;
import com.example.alwanyapp.Domain.HelperAppsDataClass;
import com.example.alwanyapp.R;

import java.util.ArrayList;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.helper>{
    private onItemClickListener mListener;
    public interface onItemClickListener
    {
        void delete(int position);
    }
    public void setOnItemClick(onItemClickListener mListener)
    {
        this.mListener=mListener;
    }
    private ArrayList<ColorsDataClass> arrayList;
    private Context context;

    public ColorsAdapter(ArrayList<ColorsDataClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_colors_adapter,parent,false);

        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.image);
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
        ImageView image;
        Button delete;
        public helper(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            delete =itemView.findViewById(R.id.delete);
        }
    }
}
