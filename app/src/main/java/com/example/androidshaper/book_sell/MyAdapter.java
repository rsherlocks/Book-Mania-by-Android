package com.example.androidshaper.book_sell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<MyItem> list;
    private Context context;

    public MyAdapter(List<MyItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rowlayout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final MyItem myItemPosition=list.get(position);
       String price=myItemPosition.getPrice();
       String priceFinal="BDT "+price;
       holder.textViewPrice.setText(priceFinal);
       holder.textViewName.setText(myItemPosition.getName());
        Picasso.get().load(myItemPosition.getPicture()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,myItemPosition.getName(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,ProductDetailsActivity.class);
                intent.putExtra("id",myItemPosition.getId());
                intent.putExtra("name",myItemPosition.getName());
                intent.putExtra("description",myItemPosition.getDescription());
                intent.putExtra("category",myItemPosition.getCetagory());
                intent.putExtra("author",myItemPosition.getAuthor());
                intent.putExtra("publish",myItemPosition.getPublish());
                intent.putExtra("price",myItemPosition.getPrice());
                intent.putExtra("picture",myItemPosition.getPicture());
                intent.putExtra("pdf",myItemPosition.getPdf());
                intent.putExtra("sell",myItemPosition.getSell());

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
