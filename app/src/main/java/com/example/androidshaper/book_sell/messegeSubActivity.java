package com.example.androidshaper.book_sell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class messegeSubActivity extends Fragment {
    View vMessege;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vMessege=inflater.inflate(R.layout.messege_layout,container,false);

        return vMessege;
    }
}
