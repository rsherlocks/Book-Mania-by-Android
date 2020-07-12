package com.example.androidshaper.book_sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
   Toolbar toolbarMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomBarId);
        toolbarMainActivity=findViewById(R.id.toolbarMainActivity);
        setSupportActionBar(toolbarMainActivity);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameViewId,new homeSubAcitivity()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment=null;
                if(menuItem.getItemId()==R.id.homeItmeId)
                {
                    fragment=new homeSubAcitivity();
                }
                else if(menuItem.getItemId()==R.id.messegeItmeId)
                {
                    fragment=new messegeSubActivity();
                }
                else if(menuItem.getItemId()==R.id.cartItmeId)
                {
                    fragment=new cartSubActivity();
                }
                else if(menuItem.getItemId()==R.id.profileItmeId)
                {
                    fragment=new profileSubActivity();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameViewId,fragment).commit();


                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_menu,menu);
        return true;
    }
}