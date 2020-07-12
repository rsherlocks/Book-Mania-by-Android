package com.example.androidshaper.book_sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidshaper.book_sell.sampledata.BuyActivityNew;
import com.example.androidshaper.book_sell.sampledata.PayProceessActivity;
import com.squareup.picasso.Picasso;

public class BuyActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linearLayoutAdressChekout,linearLayoutBillChekout;
    EditText editTextPhoneChekout,editTextEmailCheckout;
    TextView textViewAdressChekout,textViewPersonNameCheckout,textViewProductName,textViewProductPrice,textViewProductQuentity,textViewBuyPrice;
    Toolbar toolbarChekout;
    ImageView imageViewProduct;
    Button buttonProcess;
    //
    Bundle bundleA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        linearLayoutAdressChekout=findViewById(R.id.checkoutEditAdress);
        linearLayoutBillChekout=findViewById(R.id.checkoutBill);
        textViewAdressChekout=findViewById(R.id.checkoutPersonAdress);
        textViewPersonNameCheckout=findViewById(R.id.checkoutPersonName);
        editTextPhoneChekout=findViewById(R.id.chekoutPhn);
        editTextEmailCheckout=findViewById(R.id.checkoutEmail);
        textViewProductPrice=findViewById(R.id.checkoutPoductPrice);
        textViewProductName=findViewById(R.id.checkoutProductName);
        textViewProductQuentity=findViewById(R.id.checkoutProductQuentity);
        toolbarChekout=findViewById(R.id.toolbarcheckout);
        textViewBuyPrice=findViewById(R.id.textViewBuyTotal);
        imageViewProduct=findViewById(R.id.image_checkout);
        buttonProcess=findViewById(R.id.buttonBuyProccess);
        //
        buttonProcess.setOnClickListener(this);
        //
        setSupportActionBar(toolbarChekout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        this.bundleA=bundle;
        //

        linearLayoutAdressChekout.setOnClickListener(this);
        linearLayoutBillChekout.setOnClickListener(this);
        ShowSavingAdress();
        ShowProduct();

    }

    private void ShowProduct() {
        float total=0;
        Picasso.get().load(bundleA.getString("picture")).into(imageViewProduct);
        textViewProductName.setText(bundleA.getString("name"));
        textViewProductPrice.setText("BDT "+bundleA.getString("price"));
        textViewProductQuentity.setText("Qty: "+bundleA.getString("quentity"));
        String price=bundleA.getString("price");
        String order=bundleA.getString("quentity");
        float number=Float.valueOf(order);
         float priceNew=Float.valueOf(price);
         total=(priceNew*number);
         textViewBuyPrice.setText("BDT "+total);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this,"onRestart",Toast.LENGTH_SHORT).show();
        ShowSavingAdress();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show();
//        ShowSavingAdress();
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(this,"onStart",Toast.LENGTH_SHORT).show();
//        ShowSavingAdress();
//    }

    private void ShowSavingAdress() {
        SharedPreferences sharedPreferences=getSharedPreferences("ShipingAdress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();


        if(sharedPreferences.contains("namekey")&&sharedPreferences.contains("phonekey")&&sharedPreferences.contains("adresskey")&& sharedPreferences.contains("setdefult"))
        {
            if (sharedPreferences.getString("setdefult","off").toString().equals("on"))
            {
                textViewPersonNameCheckout.setText(sharedPreferences.getString("namekey",""));
                textViewAdressChekout.setText(sharedPreferences.getString("adresskey",""));
                editTextPhoneChekout.setText(sharedPreferences.getString("phonekey",""));
                editTextEmailCheckout.setText(sharedPreferences.getString("emailkey",""));

            }
            else {
                textViewPersonNameCheckout.setText(sharedPreferences.getString("namekey",""));
                textViewAdressChekout.setText(sharedPreferences.getString("adresskey",""));
                editTextPhoneChekout.setText(sharedPreferences.getString("phonekey",""));
                editTextEmailCheckout.setText(sharedPreferences.getString("emailkey",""));

                editor.clear().commit();


            }



        }
        if(sharedPreferences.contains("phonekey")||sharedPreferences.contains("emailkey"))
        {
            editTextPhoneChekout.setText(sharedPreferences.getString("phonekey",""));
            editTextEmailCheckout.setText(sharedPreferences.getString("emailkey",""));

        }
        else
        {
//            String check=sharedPreferences.getString("namekey","nodata");
//            Toast.makeText(this,check,Toast.LENGTH_SHORT).show();
            textViewPersonNameCheckout.setText("");
          textViewAdressChekout.setText("");
          editTextPhoneChekout.setText("");
          editTextEmailCheckout.setText("");

        }



    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this, BuyActivityNew.class);
        String personeName=textViewPersonNameCheckout.getText().toString();
        String personAdress=textViewAdressChekout.getText().toString();
        String personPhone=editTextPhoneChekout.getText().toString();
        if (view.getId()==R.id.checkoutEditAdress)
        {

            startActivity(intent);
        }
        else if(view.getId()==R.id.checkoutBill)
        {

            SharedPreferences sharedPreferences=getSharedPreferences("ShipingAdress", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            if (personPhone.isEmpty())
            {
                Toast.makeText(this,"Empty Field",Toast.LENGTH_SHORT).show();
            }
            else
            {
                editor.putString("phonekey",personPhone);
                editor.putString("emailkey",editTextEmailCheckout.getText().toString());
                editor.commit();
                Toast.makeText(this,"Save Phone && Email",Toast.LENGTH_SHORT).show();

            }


        }
        else if(view.getId()==R.id.buttonBuyProccess)
        {
            Intent buyProccess=new Intent(this, PayProceessActivity.class);

            if (personeName.isEmpty()||personAdress.isEmpty()||personPhone.isEmpty())
            {
                Toast.makeText(this,"Please Fillup All Field",Toast.LENGTH_SHORT).show();
            }
            else
            {
                buyProccess.putExtra("name",personeName);
                buyProccess.putExtra("adress",personAdress);
                buyProccess.putExtra("phone",personPhone);
                startActivity(buyProccess);

            }

        }

    }
}