package com.example.androidshaper.book_sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CetagoryProductActivity extends AppCompatActivity {
    RecyclerView recyclerViewCategoryProduct;
    ProgressDialog progressDialog;
    MyAdapter myAdapter;
    List<MyItem> listItem;
    Toolbar toolbarCategoryProduct;
    Bundle bundleA;
    String url="https://next.json-generator.com/api/json/get/Vy9UK9pn_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetagory_product);
        toolbarCategoryProduct=findViewById(R.id.toolbarCategoryProduct);
        recyclerViewCategoryProduct=findViewById(R.id.recyclerViewCetagoryProduct);
        //
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        this.bundleA=bundle;
        //
        setSupportActionBar(toolbarCategoryProduct);
        getSupportActionBar().setTitle(bundle.getString("name")+" Books");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerViewCategoryProduct.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        recyclerViewCategoryProduct.setLayoutManager(gridLayoutManager);
        listItem=new ArrayList<>();
        if(savedInstanceState==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Loooding...");
            progressDialog.show();

        }

        loadData();
    }

    private void loadData() {
        final StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("MyBook");
                    //String name="amin rahman";
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject recive=jsonArray.getJSONObject(i);
//                        if (recive.getString("name").equals(name)) {
//                            MyItem Item=new MyItem(recive.getString("name"),recive.getString("role"));
//                            listItem.add(Item);
//                        }
                        if (recive.getString("cetagory").equals(bundleA.getString("name").toString()))
                        {
                            MyItem Item=new MyItem(recive.getString("id"),recive.getString("name"),recive.getString("cetagory"),
                                    recive.getString("author"),recive.getString("description"),recive.getString("publish"),recive.getString("picture"),
                                    recive.getString("pdf"),recive.getString("price"),recive.getString("sell"));
                            listItem.add(Item);
                        }



                    }
                    myAdapter=new MyAdapter(listItem,getApplicationContext());

                    recyclerViewCategoryProduct.setAdapter(myAdapter);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Server error",Toast.LENGTH_SHORT).show();
            }
        }


        );
        RequestQueue quque= Volley.newRequestQueue(getApplicationContext());
        quque.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}