package com.example.androidshaper.book_sell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterCetagory extends RecyclerView.Adapter<MyViewHolderCategory> {
    List<CategoryItem> list;
    private Context context;

    public MyAdapterCetagory(List<CategoryItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_rowlayout,parent,false);
        MyViewHolderCategory myViewHolderCategory=new MyViewHolderCategory(view);
        return myViewHolderCategory;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCategory holder, int position) {
        final CategoryItem myItemPosition=list.get(position);
        holder.textViewCategory.setText(myItemPosition.getCategoryName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,CetagoryProductActivity.class);
                intent.putExtra("name",myItemPosition.getCategoryName());
                Activity activity=(Activity) view.getContext();
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
