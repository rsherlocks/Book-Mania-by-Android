package com.example.androidshaper.book_sell;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class homeSubAcitivity extends Fragment implements View.OnClickListener {
    View vHome;
    LinearLayout layoutCate,layoutGift,layoutCamp,layoutOrder;
    ImageSlider imageSlider;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<MyItem> listItem;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    String url="https://next.json-generator.com/api/json/get/Vy9UK9pn_";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vHome=inflater.inflate(R.layout.home_layout,container,false);

        layoutCate=vHome.findViewById(R.id.layoutCategorie);
        layoutGift=vHome.findViewById(R.id.layoutGift);
        layoutCamp=vHome.findViewById(R.id.layoutCamp);
        layoutOrder=vHome.findViewById(R.id.layoutOrder);
        layoutCate.setOnClickListener(this);
        layoutGift.setOnClickListener(this);
        layoutCamp.setOnClickListener(this);
        layoutOrder.setOnClickListener(this);
        // Add sliding Image
        imageSlider=vHome.findViewById(R.id.sliderImage);
        ShowImage();
        //
        swipeRefreshLayout=vHome.findViewById(R.id.swipeRefresh);
        //

        //
        recyclerView=vHome.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(vHome.getRootView().getContext(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);
        listItem=new ArrayList<>();
        if(savedInstanceState==null){
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Loooding...");
            progressDialog.show();

        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItem.clear();
                loadData();
                //myAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loadData();
        return vHome;
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
                        MyItem Item=new MyItem(recive.getString("id"),recive.getString("name"),recive.getString("cetagory"),
                                recive.getString("author"),recive.getString("description"),recive.getString("publish"),recive.getString("picture"),
                                recive.getString("pdf"),recive.getString("price"),recive.getString("sell"));
                        listItem.add(Item);


                    }
                    myAdapter=new MyAdapter(listItem,getContext());

                    recyclerView.setAdapter(myAdapter);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Server error",Toast.LENGTH_SHORT).show();
            }
        }


        );
        RequestQueue quque= Volley.newRequestQueue(getContext());
        quque.add(stringRequest);
    }

    private void ShowImage() {
        final StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            private int idnumber;

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    final JSONArray jsonArray=jsonObject.getJSONArray("MyBook");
                    List<SlideModel> slideModels=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject recive=jsonArray.getJSONObject(i);
                        String id=recive.getString("id");
                        String imageurl=recive.getString("picture");
                        String text=recive.getString("name");
                        int number=Integer.parseInt(id);
                        this.idnumber=number;

                        if(number>=4 && number<=8)
                        {

                            slideModels.add(new SlideModel(imageurl));
                        }

                    }
                    imageSlider.setImageList(slideModels,true);
                    imageSlider.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemSelected(int i) {
                            if(i==0)
                            {
                                try {
                                    JSONObject recive=jsonArray.getJSONObject(4);
                                    String text=recive.getString("name");
                                    Intent intent=new Intent(getContext(),ProductDetailsActivity.class);
                                    intent.putExtra("id",recive.getString("id"));
                                    intent.putExtra("name",recive.getString("name"));
                                    intent.putExtra("description",recive.getString("description"));
                                    intent.putExtra("category",recive.getString("cetagory"));
                                    intent.putExtra("author",recive.getString("author"));
                                    intent.putExtra("publish",recive.getString("publish"));
                                    intent.putExtra("price",recive.getString("price"));
                                    intent.putExtra("picture",recive.getString("picture"));
                                    intent.putExtra("pdf",recive.getString("pdf"));
                                    intent.putExtra("sell",recive.getString("sell"));
                                    Activity activity=(Activity) getContext();
                                    activity.startActivity(intent);
                                    activity.overridePendingTransition(R.anim.bottom_in_slide,R.anim.slide_stay);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            else if(i==1)
                            {
                                try {
                                    JSONObject recive=jsonArray.getJSONObject(5);
                                    String text=recive.getString("name");
                                    Intent intent=new Intent(getContext(),ProductDetailsActivity.class);
                                    intent.putExtra("id",recive.getString("id"));
                                    intent.putExtra("name",recive.getString("name"));
                                    intent.putExtra("description",recive.getString("description"));
                                    intent.putExtra("category",recive.getString("cetagory"));
                                    intent.putExtra("author",recive.getString("author"));
                                    intent.putExtra("publish",recive.getString("publish"));
                                    intent.putExtra("price",recive.getString("price"));
                                    intent.putExtra("picture",recive.getString("picture"));
                                    intent.putExtra("pdf",recive.getString("pdf"));
                                    intent.putExtra("sell",recive.getString("sell"));
                                    //getContext().startActivity(intent);
                                    Activity activity=(Activity) getContext();
                                    activity.startActivity(intent);
                                    activity.overridePendingTransition(R.anim.bottom_in_slide,R.anim.slide_stay);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            else if(i==2)
                            {
                                try {
                                    JSONObject recive=jsonArray.getJSONObject(6);
                                    String text=recive.getString("name");
                                    Intent intent=new Intent(getContext(),ProductDetailsActivity.class);
                                    intent.putExtra("id",recive.getString("id"));
                                    intent.putExtra("name",recive.getString("name"));
                                    intent.putExtra("description",recive.getString("description"));
                                    intent.putExtra("category",recive.getString("cetagory"));
                                    intent.putExtra("author",recive.getString("author"));
                                    intent.putExtra("publish",recive.getString("publish"));
                                    intent.putExtra("price",recive.getString("price"));
                                    intent.putExtra("picture",recive.getString("picture"));
                                    intent.putExtra("pdf",recive.getString("pdf"));
                                    intent.putExtra("sell",recive.getString("sell"));
                                    //getContext().startActivity(intent);
                                    Activity activity=(Activity) getContext();
                                    activity.startActivity(intent);
                                    activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(i==3)
                            {
                                try {
                                    JSONObject recive=jsonArray.getJSONObject(7);
                                    String text=recive.getString("name");
                                    Intent intent=new Intent(getContext(),ProductDetailsActivity.class);
                                    intent.putExtra("id",recive.getString("id"));
                                    intent.putExtra("name",recive.getString("name"));
                                    intent.putExtra("description",recive.getString("description"));
                                    intent.putExtra("category",recive.getString("cetagory"));
                                    intent.putExtra("author",recive.getString("author"));
                                    intent.putExtra("publish",recive.getString("publish"));
                                    intent.putExtra("price",recive.getString("price"));
                                    intent.putExtra("picture",recive.getString("picture"));
                                    intent.putExtra("pdf",recive.getString("pdf"));
                                    intent.putExtra("sell",recive.getString("sell"));
                                    Activity activity=(Activity) getContext();
                                    activity.startActivity(intent);
                                    activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(i==4)
                            {
                                try {
                                    JSONObject recive=jsonArray.getJSONObject(8);
                                    String text=recive.getString("name");
                                    Intent intent=new Intent(getContext(),ProductDetailsActivity.class);
                                    intent.putExtra("id",recive.getString("id"));
                                    intent.putExtra("name",recive.getString("name"));
                                    intent.putExtra("description",recive.getString("description"));
                                    intent.putExtra("category",recive.getString("cetagory"));
                                    intent.putExtra("author",recive.getString("author"));
                                    intent.putExtra("publish",recive.getString("publish"));
                                    intent.putExtra("price",recive.getString("price"));
                                    intent.putExtra("picture",recive.getString("picture"));
                                    intent.putExtra("pdf",recive.getString("pdf"));
                                    intent.putExtra("sell",recive.getString("sell"));
//                                    getContext().startActivity(intent);
                                    Activity activity=(Activity) getContext();
                                    activity.startActivity(intent);
                                    activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });


                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"Server error",Toast.LENGTH_SHORT).show();
            }
        }


        );
        RequestQueue quque= Volley.newRequestQueue(getContext());
        quque.add(stringRequest);



    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.layoutCategorie)
        {
            Intent intent=new Intent(getContext(),CategoryActivity.class);
            Activity activity=(Activity) getContext();
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        }

    }
}
