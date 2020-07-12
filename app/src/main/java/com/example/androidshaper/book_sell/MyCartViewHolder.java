package com.example.androidshaper.book_sell;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCartViewHolder extends RecyclerView.ViewHolder {
    TextView textViewCartProductName,textViewCartProductPrice,textViewCartProductQuentity;
    ImageView imageViewCartProduct;
    LinearLayout linearLayoutCartProduct;
    EditText editTextCartVoucher;
    Button buttonVoucherApply;
    public MyCartViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewCartProductName=itemView.findViewById(R.id.textViewCartProductName);
        textViewCartProductPrice=itemView.findViewById(R.id.textViewCartProductPrice);
        textViewCartProductQuentity=itemView.findViewById(R.id.textViewCartProductQuentity);
        imageViewCartProduct=itemView.findViewById(R.id.imageViewCartProduct);
        linearLayoutCartProduct=itemView.findViewById(R.id.linearLayoutCartProduct);



    }
}
