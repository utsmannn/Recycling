/*
 * Created by Muhammad Utsman on 9/24/19 2:51 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/24/19 2:49 PM
 */

package com.utsman.recycling.sample.javasample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.utsman.recycling.RecyclingBuilder;
import com.utsman.recycling.sample.R;

public class SimpleJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);

        new RecyclingBuilder<String>()
                .setLayout(R.layout.simple_item_view)
                .setRecyclerView(recyclerView)
                .build((recycling, adapter, context, item) -> {
                    recycling.bind((bind, view, position, item1) -> {
                        TextView textView = view.findViewById(R.id.name_item);
                        textView.setText(item1);
                        return null;
                    });

                    for(int i=1;i<=100;i++){
                        recycling.submitItem("item ke " + i);
                    }

                    return true;
                });
    }
}
