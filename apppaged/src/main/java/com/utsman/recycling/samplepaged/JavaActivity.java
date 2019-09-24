/*
 * Created by Muhammad Utsman on 9/24/19 4:29 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/24/19 4:29 PM
 */

package com.utsman.recycling.samplepaged;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.utsman.recycling.core.Pexel;
import com.utsman.recycling.paged.RecyclingBuilder;

import static com.utsman.recycling.core.AppKt.loadImg;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PexelViewModel pexelViewModel = ViewModelProviders.of(this).get(PexelViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        new RecyclingBuilder<Pexel>()
                .setLayout(R.layout.item_view)
                .setRecyclerView(recyclerView)
                .build((recycling, adapter, context, list) ->{

                    recyclerView.setLayoutManager(gridLayoutManager);
                    recycling.fixGridSpan(2);

                    recycling.bind((bind, view, position, pexel) -> {

                        ImageView imageView = view.findViewById(R.id.img_view);
                        loadImg(imageView, pexel.getSrc().getSmall());

                        return null;
                    });


                    recycling.addLoader(R.layout.item_loader, loaderIdentifierId -> {
                        loaderIdentifierId.setIdLoader(R.id.progress_circular);
                        loaderIdentifierId.setIdTextError(R.id.error_text_view);
                        return null;
                    });

                    pexelViewModel.getCuratedPhoto().observe(this, recycling::submitList);
                    pexelViewModel.getLoader().observe(this, recycling::submitNetwork);

                    return true;
                });
    }
}