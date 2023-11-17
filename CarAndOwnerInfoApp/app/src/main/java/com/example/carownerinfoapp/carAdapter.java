package com.example.carownerinfoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class carAdapter extends RecyclerView.Adapter<carAdapter.ViewHolder>
{
    private final ArrayList<car> cars;

    itemClicked activity;
    public interface itemClicked
    {
        void onItemClicked(int index);
    }
    public carAdapter(Context context,ArrayList<car> list)
    {
        cars=list;
        activity=(itemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivMake;
        TextView tvModel,tvOwner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMake=itemView.findViewById(R.id.ivMake);
            tvModel=itemView.findViewById(R.id.tvModel);
            tvOwner=itemView.findViewById(R.id.tvOwner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    activity.onItemClicked(cars.indexOf((car)view.getTag()));
                }
            });
        }
    }
    @NonNull
    @Override
    public carAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull carAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(cars.get(position));

        holder.tvOwner.setText(cars.get(position).getModel());
        if(cars.get(position).getMake().equals("Volkswagen"))
        {
            holder.ivMake.setImageResource(R.drawable.volkswagen);
        }
        else if(cars.get(position).getMake().equals("Nissan"))
        {
            holder.ivMake.setImageResource(R.drawable.nissan);
        }
        else if(cars.get(position).getMake().equals("Mercedes"))
        {
            holder.ivMake.setImageResource(R.drawable.mercedes);
        }
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}

