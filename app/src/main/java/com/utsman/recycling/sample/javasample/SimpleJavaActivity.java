/*
 * Created by Muhammad Utsman on 9/24/19 2:51 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/24/19 2:49 PM
 */

package com.utsman.recycling.sample.javasample;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.utsman.recycling.javabuilder.BindBuilder;
import com.utsman.recycling.javabuilder.Builder;
import com.utsman.recycling.RecyclingBuilder;
import com.utsman.recycling.adapter.RecyclingAdapter;
import com.utsman.recycling.extentions.Recycling;
import com.utsman.recycling.sample.R;

import java.util.List;

public class SimpleJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);


        new RecyclingBuilder<String>()
                .setLayout(R.layout.simple_item_view)
                .setRecyclerView(recyclerView)
                .building(new Builder() {

                    @Override
                    public void invoke(Recycling recycling, RecyclingAdapter recyclingAdapter, Context context, List list) {




                        /*recycling.bindJava(new BindBuilder() {
                            @Override
                            public void invoke(Binding binding, View view, Integer position, Object item) {
                                String text = (String) item;
                                TextView textView = view.findViewById(R.id.name_item);
                                textView.setText(text);
                            }
                        });*/

                        recycling.bindJava((view, item, position) -> {
                            String text = (String) item;
                            TextView textView = view.findViewById(R.id.name_item);
                            textView.setText(text);
                        });



                        /*for(int i=1;i<=100;i++){
                            recycling.submitItem("item ke " + i);
                        }*/
                    }
                });
    }
}
