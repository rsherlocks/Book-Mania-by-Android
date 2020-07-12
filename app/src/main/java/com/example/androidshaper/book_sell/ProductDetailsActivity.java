package com.example.androidshaper.book_sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.androidshaper.book_sell.sampledata.BuyActivityNew;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewPrice,textViewDescription,textViewCategory,textViewSell,textViewName,textViewPublish,textViewAuthor;
    ImageView imageViewPreview;
    Button buttonPreview;
    Toolbar toolbarProductDetails;
    LinearLayout linearLayoutStore,linearLayoutContact,linearLayoutCart,linearLayoutBuy;
    //

    //
    String id;
    String name;
    String category;
    String author;
    String description;
    String publish;
    String picture;
    String pdf;
    String price;
    String sell;
    //
    Bundle bundleA;
    //
    RecyclerView recyclerViewRelatedBook;
    MyAdapter myAdapter;
    List<MyItem> listItem;
    ProgressDialog progressDialog;
    String url="https://next.json-generator.com/api/json/get/Vy9UK9pn_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        textViewPrice=findViewById(R.id.textViewPrice);
        textViewDescription=findViewById(R.id.textViewDescription);
        textViewSell=findViewById(R.id.textViewSell);
        textViewName=findViewById(R.id.textViewName);
        textViewCategory=findViewById(R.id.textViewCategory);
        textViewPublish=findViewById(R.id.textViewPublish);
        textViewAuthor=findViewById(R.id.textViewAuthor);
        imageViewPreview=findViewById(R.id.imagePreview);
        buttonPreview=findViewById(R.id.buttonPreview);
        linearLayoutStore=findViewById(R.id.linear_layout_store);
        linearLayoutContact=findViewById(R.id.linear_layout_contact);
        linearLayoutCart=findViewById(R.id.linear_layout_cart);
        linearLayoutBuy=findViewById(R.id.linear_layout_buy);
        //
        linearLayoutBuy.setOnClickListener(this);
        linearLayoutCart.setOnClickListener(this);
        //
        toolbarProductDetails=findViewById(R.id.toolbarProductDetails);
        setSupportActionBar(toolbarProductDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        this.bundleA=bundle;
        getSupportActionBar().setTitle(bundleA.getString("name"));
        //
        recyclerViewRelatedBook=findViewById(R.id.recyclerViewRelatedBook);
        recyclerViewRelatedBook.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(),1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        recyclerViewRelatedBook.setLayoutManager(gridLayoutManager);
        listItem=new ArrayList<>();
//        if(savedInstanceState==null){
//            progressDialog=new ProgressDialog(this);
//            progressDialog.setMessage("Loooding...");
//            progressDialog.show();
//
//        }
        ShowRelatedProduct();
        //
        buttonPreview.setOnClickListener(this);
        //Show Details
        ShowDetails();
    }

    private void ShowRelatedProduct() {
        final StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("MyBook");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject recive=jsonArray.getJSONObject(i);
                       if (recive.getString("cetagory").equals(bundleA.getString("category")) && !recive.getString("name").equals(bundleA.getString("name"))) {
//                            MyItem Item=new MyItem(recive.getString("name"),recive.getString("role"));
//                            listItem.add(Item);
                        MyItem Item=new MyItem(recive.getString("id"),recive.getString("name"),recive.getString("cetagory"),
                                recive.getString("author"),recive.getString("description"),recive.getString("publish"),recive.getString("picture"),
                                recive.getString("pdf"),recive.getString("price"),recive.getString("sell"));
                        listItem.add(Item);
                        }



                    }
                    myAdapter=new MyAdapter(listItem,getApplicationContext());

                    recyclerViewRelatedBook.setAdapter(myAdapter);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Server error",Toast.LENGTH_SHORT).show();
            }
        }


        );
        RequestQueue quque= Volley.newRequestQueue(getApplicationContext());
        quque.add(stringRequest);

    }

    private void ShowDetails() {
        picture=bundleA.getString("picture");
        Picasso.get().load(picture).into(imageViewPreview);

        //
        price="BDT "+bundleA.getString("price");
        textViewPrice.setText(price);

        //
        textViewDescription.setText(bundleA.getString("description"));
        textViewSell.setText(bundleA.getString("sell")+"%");
        textViewName.setText("Name: "+bundleA.getString("name"));
        textViewCategory.setText("Category: "+bundleA.getString("category"));
        textViewPublish.setText("Publish: "+bundleA.getString("publish"));
        textViewAuthor.setText("Author: "+bundleA.getString("author"));

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.buttonPreview)
        {
            Intent intent=new Intent(getApplicationContext(),PdfShow.class);
            intent.putExtra("url",bundleA.getString("pdf"));
            intent.putExtra("name",bundleA.getString("name"));
            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }
        else if(view.getId()==R.id.linear_layout_buy)
        {
            final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this,R.style.BottomSheet);
            View bottomSheet= LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_dailog_buy,(LinearLayout)findViewById(R.id.bottomsheet_buy));
            bottomSheetDialog.setContentView(bottomSheet);
            bottomSheetDialog.show();


            bottomSheetDialog.setCanceledOnTouchOutside(true);
            final TextView textViewCartPrice=bottomSheetDialog.findViewById(R.id.tectViewCartPrice);
            final TextView textViewCartName=bottomSheetDialog.findViewById(R.id.textViewCartName);
            final TextView textViewCartCategory=bottomSheetDialog.findViewById(R.id.textViewCategory);
            final TextView textViewCartDes=bottomSheetDialog.findViewById(R.id.textViewCartDetails);
            final  ImageView imageViewCart=bottomSheetDialog.findViewById(R.id.imageCart);
//            final EditText editTextRole=bottomSheetDialog.findViewById(R.id.edtRole);
            final ElegantNumberButton elegantNumberButton=bottomSheetDialog.findViewById(R.id.myButton);
            textViewCartPrice.setText("BDT "+bundleA.getString("price"));
            textViewCartName.setText("Name: "+bundleA.getString("name"));
            textViewCartCategory.setText("Category: "+bundleA.getString("category"));
            textViewCartDes.setText(bundleA.getString("description"));
            Picasso.get().load(bundleA.getString("picture")).into(imageViewCart);


            Button buttonAdd=bottomSheetDialog.findViewById(R.id.buttonAdd);
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                    final String number=elegantNumberButton.getNumber();

                    Intent intent=new Intent(getApplicationContext(), BuyActivity.class);
                    intent.putExtra("name",bundleA.getString("name"));
                    intent.putExtra("price",bundleA.getString("price"));
                    intent.putExtra("picture",bundleA.getString("picture"));
                    intent.putExtra("quentity",number);
                    int m=Integer.valueOf(number);
                    if (m>0)
                    {
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please add your quentity",Toast.LENGTH_SHORT).show();

                    }


//                    String name=editTextName.getText().toString();
//                    String role=editTextRole.getText().toString();
//
//                    if (name.equals("rakib")&&role.equals("student"))
//                    {
//                        final AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
//                        builder.setTitle(name);
//                        builder.setMessage(role);
//                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//
//                            }
//                        });
//                        AlertDialog alertDialog=builder.create();
//                        bottomSheetDialog.dismiss();
//                        alertDialog.show();
//                    }else {
//                        Toast.makeText(getApplicationContext(),"Cancel Click",Toast.LENGTH_SHORT).show();
//                    }
                }
            });
            bottomSheetDialog.show();



        }
        else if (view.getId()==R.id.linear_layout_cart)
        {
            final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this,R.style.BottomSheet);
            View bottomSheet= LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_dailog_cart,(LinearLayout)findViewById(R.id.bottomsheet_cart));
            bottomSheetDialog.setContentView(bottomSheet);
            bottomSheetDialog.show();


            bottomSheetDialog.setCanceledOnTouchOutside(true);
            final TextView textViewCartPrice=bottomSheetDialog.findViewById(R.id.tectViewCartPrice);
           final TextView textViewCartName=bottomSheetDialog.findViewById(R.id.textViewCartName);
         final TextView textViewCartCategory=bottomSheetDialog.findViewById(R.id.textViewCategory);
         final TextView textViewCartDes=bottomSheetDialog.findViewById(R.id.textViewCartDetails);
           final  ImageView imageViewCart=bottomSheetDialog.findViewById(R.id.imageCart);
//            final EditText editTextRole=bottomSheetDialog.findViewById(R.id.edtRole);
            final ElegantNumberButton elegantNumberButton=bottomSheetDialog.findViewById(R.id.myButton);
            textViewCartPrice.setText("BDT "+bundleA.getString("price"));
            textViewCartName.setText("Name: "+bundleA.getString("name"));
            textViewCartCategory.setText("Category: "+bundleA.getString("category"));
            textViewCartDes.setText(bundleA.getString("description"));
            Picasso.get().load(bundleA.getString("picture")).into(imageViewCart);


            Button buttonAdd=bottomSheetDialog.findViewById(R.id.buttonAdd);
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String number=elegantNumberButton.getNumber();
                    //Toast.makeText(getApplicationContext(),number,Toast.LENGTH_SHORT).show();
                    int m=Integer.valueOf(number);
                    if (m>0)
                    {
                       bottomSheetDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Add Cart Item",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please add your quentity",Toast.LENGTH_SHORT).show();

                    }
//                    String name=editTextName.getText().toString();
//                    String role=editTextRole.getText().toString();
//
//                    if (name.equals("rakib")&&role.equals("student"))
//                    {
//                        final AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
//                        builder.setTitle(name);
//                        builder.setMessage(role);
//                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//
//                            }
//                        });
//                        AlertDialog alertDialog=builder.create();
//                        bottomSheetDialog.dismiss();
//                        alertDialog.show();
//                    }else {
//                        Toast.makeText(getApplicationContext(),"Cancel Click",Toast.LENGTH_SHORT).show();
//                    }
                }
            });
            bottomSheetDialog.show();

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}