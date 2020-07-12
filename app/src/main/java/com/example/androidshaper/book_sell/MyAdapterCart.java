package com.example.androidshaper.book_sell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterCart extends RecyclerView.Adapter<MyCartViewHolder> {

    List<MyItem> list;
    private Context context;

    public MyAdapterCart(List<MyItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_lowlayout,parent,false);
        MyCartViewHolder myCartViewHolder=new MyCartViewHolder(view);
        return myCartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartViewHolder holder, int position) {
        final MyItem myItemPosition=list.get(position);
        holder.textViewCartProductName.setText(myItemPosition.getName());
        holder.textViewCartProductPrice.setText("BDT "+myItemPosition.getPrice());
        holder.textViewCartProductQuentity.setText("Qty: "+"1");
        Picasso.get().load(myItemPosition.getPicture()).into(holder.imageViewCartProduct);
        holder.linearLayoutCartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,BuyActivity.class);
                intent.putExtra("name",myItemPosition.getName());
                intent.putExtra("price",myItemPosition.getPrice());
                intent.putExtra("picture",myItemPosition.getPicture());
                intent.putExtra("quentity","1");
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
