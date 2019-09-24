/*
 * Created by Muhammad Utsman on 9/24/19 2:52 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/24/19 2:52 PM
 */

package com.utsman.recycling.samplepaged.javasample;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utsman.recycling.core.Pexel;
import com.utsman.recycling.paged.RecyclingBuilder;
import com.utsman.recycling.samplepaged.PexelViewModel;
import com.utsman.recycling.samplepaged.R;

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
