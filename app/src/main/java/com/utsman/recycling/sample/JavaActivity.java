/*
 * Created by Muhammad Utsman on 9/24/19 10:47 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/24/19 10:47 AM
 */

package com.utsman.recycling.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);


    }
}
