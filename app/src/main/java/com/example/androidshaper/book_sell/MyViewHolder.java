package com.example.androidshaper.book_sell;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName,textViewPrice;
    ImageView imageView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageItemShow);
        textViewName=itemView.findViewById(R.id.textItemName);
        textViewPrice=itemView.findViewById(R.id.textItemPrice);


    }
}
