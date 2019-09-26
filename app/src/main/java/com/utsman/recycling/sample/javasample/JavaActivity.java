/*
 * Created by Muhammad Utsman on 9/24/19 2:51 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/24/19 2:37 PM
 */

package com.utsman.recycling.sample.javasample;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utsman.recycling.RecyclingBuilder;
import com.utsman.recycling.core.Pexel;
import com.utsman.recycling.sample.PexelViewModel;
import com.utsman.recycling.sample.R;

import kotlin.Unit;

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
                .build((recycling, adapter, context, list) -> {

                    recyclerView.setLayoutManager(gridLayoutManager);
                    recycling.fixGridSpan(2);

                    /*recycling.bind((binding, view, position, pexel) -> {
                        // bind your item view
                        ImageView imageView = view.findViewById(R.id.img_view);
                        loadImg(imageView, pexel.getSrc().getSmall());

                        return Unit.INSTANCE;
                    });*/

                    // add network loader
                    recycling.addLoader(R.layout.item_loader, loaderIdentifierId -> {
                        loaderIdentifierId.setIdLoader(R.id.progress_circular);
                        loaderIdentifierId.setIdTextError(R.id.error_text_view);
                        return Unit.INSTANCE;
                    });

                    pexelViewModel.getCuratedPhoto(30, 1).observe(this, recycling::submitList);
                    pexelViewModel.getNetworkState().observe(this, recycling::submitNetworkState);

                    // paging helper
                    recycling.onPagingListener(gridLayoutManager, (endlessScrollListener, page, itemCount) -> {
                        pexelViewModel.getCuratedPhoto(30,  page+1).observe(this, recycling::submitList);
                        return Unit.INSTANCE;
                    });

                    return true;
                });
    }
}
