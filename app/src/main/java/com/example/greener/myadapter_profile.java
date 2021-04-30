package com.example.greener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter_profile extends RecyclerView.Adapter<myadapter_profile.myviewholder>
{
    ArrayList<DataModel> dataholder_profile;

    public myadapter_profile(ArrayList<DataModel> dataholder_profile) {
        this.dataholder_profile = dataholder_profile;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_profile,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position)
    {
        holder.img.setImageResource(dataholder_profile.get(position).getImage());
        holder.header.setText(dataholder_profile.get(position).getHeader());
        holder.desc.setText(dataholder_profile.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return dataholder_profile.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView header,desc;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.img1);
            header=itemView.findViewById(R.id.t11);
            desc=itemView.findViewById(R.id.t2);
        }
    }
}