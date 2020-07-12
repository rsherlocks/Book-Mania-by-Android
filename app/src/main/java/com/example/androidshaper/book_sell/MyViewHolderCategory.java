package com.example.androidshaper.book_sell;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderCategory extends RecyclerView.ViewHolder {

    TextView textViewCategory;
    public MyViewHolderCategory(@NonNull View itemView) {
        super(itemView);
        textViewCategory=itemView.findViewById(R.id.textViewCategoryName);

    }
}
