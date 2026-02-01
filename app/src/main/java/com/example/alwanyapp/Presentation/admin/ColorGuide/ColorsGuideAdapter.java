package com.example.alwanyapp.Presentation.admin.ColorGuide;

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
import com.example.alwanyapp.Domain.ColorsDataClass;
import com.example.alwanyapp.Domain.ColorsGuide;
import com.example.alwanyapp.R;

import java.util.ArrayList;

public class ColorsGuideAdapter extends RecyclerView.Adapter<ColorsGuideAdapter.helper>{
    private onItemClickListener mListener;
    public interface onItemClickListener
    {
        void delete(int position);
    }
    public void setOnItemClick(onItemClickListener mListener)
    {
        this.mListener=mListener;
    }
    private ArrayList<ColorsGuide> arrayList;
    private Context context;

    public ColorsGuideAdapter(ArrayList<ColorsGuide> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_colors_guide_adapter,parent,false);

        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(arrayList.get(position).getColorName());
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
        TextView name;
        ImageView image;
        Button delete;
        public helper(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.image);
            delete =itemView.findViewById(R.id.delete);

        }
    }
}
