package com.example.androidshaper.book_sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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

import static java.security.AccessController.getContext;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView recyclerViewCategory;
    ProgressDialog progressDialog;
    MyAdapterCetagory myAdapterCetagory;
    List<CategoryItem> listItem;
    Toolbar toolbarCategory;
    String url="https://next.json-generator.com/api/json/get/EJ7KvQPT_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerViewCategory=findViewById(R.id.recyclerViewCetagory);
        toolbarCategory=findViewById(R.id.toolbarCategory);
        setSupportActionBar(toolbarCategory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerViewCategory.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        recyclerViewCategory.setLayoutManager(gridLayoutManager);
        listItem=new ArrayList<>();
        if(savedInstanceState==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Loooding...");
            progressDialog.show();

        }
        ShowCategory();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowCategory() {
        final StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("MyCategory");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject recive=jsonArray.getJSONObject(i);

                            CategoryItem Item=new CategoryItem(recive.getString("cid"),recive.getString("cname"));
                            listItem.add(Item);






                    }
                    myAdapterCetagory= new MyAdapterCetagory(listItem,getApplicationContext());

                    recyclerViewCategory.setAdapter(myAdapterCetagory);

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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}