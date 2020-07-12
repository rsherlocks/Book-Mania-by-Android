package com.example.androidshaper.book_sell;

import android.app.ProgressDialog;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class cartSubActivity extends Fragment {
    View vCart;
    RecyclerView recyclerViewCartProduct;
    List<MyItem> listItem;
    MyAdapterCart myAdapterCart;
    ProgressDialog progressDialog;

    String url="https://next.json-generator.com/api/json/get/Vy9UK9pn_";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vCart=inflater.inflate(R.layout.cart_layout,container,false);
        recyclerViewCartProduct=vCart.findViewById(R.id.recyclerViewCart);

        recyclerViewCartProduct.setHasFixedSize(true);
        recyclerViewCartProduct.setNestedScrollingEnabled(false);
        recyclerViewCartProduct.setLayoutManager(new LinearLayoutManager(vCart.getRootView().getContext()));
        listItem=new ArrayList<>();
        if(savedInstanceState==null){
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Loooding...");
            progressDialog.show();

        }
        //listItem.clear();
        ShowCarProduct();
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewCartProduct);

        return vCart;
    }

    private void ShowCarProduct() {
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
                        String id=recive.getString("id");
                        int number=Integer.parseInt(id);
                        if(number>=10&&number<=15)
                        {
                            MyItem Item=new MyItem(recive.getString("id"),recive.getString("name"),recive.getString("cetagory"),
                                    recive.getString("author"),recive.getString("description"),recive.getString("publish"),recive.getString("picture"),
                                    recive.getString("pdf"),recive.getString("price"),recive.getString("sell"));
                            listItem.add(Item);

                        }
//                        if (recive.getString("name").equals(name)) {
//                            MyItem Item=new MyItem(recive.getString("name"),recive.getString("role"));
//                            listItem.add(Item);
//                        }




                    }
                    myAdapterCart= new MyAdapterCart(listItem,getContext());

                    recyclerViewCartProduct.setAdapter(myAdapterCart);

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
//swipe program

    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position=viewHolder.getAdapterPosition();

            switch (direction){
                case ItemTouchHelper.LEFT:
                   final String deletName=listItem.get(position).getName();
                    //final String deletHead= listItem.get(position).getHead();
                    //final String deletImage=listItem.get(position).getImageUrl();

                    myAdapterCart.notifyItemRemoved(position);
                    Toast.makeText(getContext(),deletName+"Cart Delete",Toast.LENGTH_SHORT).show();
                   // final MyItem item=new MyItem(deletHead,deletDesc,deletImage);
                    //Undo Item
//                    Snackbar.make(recyclerView, deletHead,Snackbar.LENGTH_LONG)
//                            .setAction("undo", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    listItem.add(position,item);
//                                    myAdapter.notifyItemInserted(position);
//
//
//                                }
//                            }).show();
                    listItem.remove(position);
                    break;
                case ItemTouchHelper.RIGHT:
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}
