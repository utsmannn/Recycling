/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.samplepaged

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.utsman.recycling.core.Pexel
import com.utsman.recycling.core.load
import com.utsman.recycling.core.toast
import com.utsman.recycling.paged.extentions.LoaderIdentifierId
import com.utsman.recycling.paged.setupAdapterPaged
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_view.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this)[PexelViewModel::class.java]

        val identifierId = LoaderIdentifierId.Builder()
            .setLoaderRes(R.layout.item_loader)
            .setIdProgressLoader(R.id.progress_circular)
            .setIdTextViewError(R.id.error_text_view)
            .build()


        main_recycler_view.setupAdapterPaged<Pexel>(R.layout.item_view, identifierId) { adapter, context, list ->

            bind { itemView, position, item ->
                itemView.img_view.load(item?.src?.small)
                itemView.setOnClickListener {
                    context.toast("Click on $position - ${adapter.itemCount} - ${list?.size}")
                }
            }

            val layoutManager = GridLayoutManager(context, 2)
            setLayoutManager(layoutManager)
            fixGridSpan(2)

            viewModel.getCuratedPhoto().observe(context as LifecycleOwner, Observer {
                submitList(it)

                /*it.map { item ->
                    submitItem(item)
                }*/
            })

            viewModel.getLoader().observe(context as LifecycleOwner, Observer {
                submitNetwork(it)
            })
        }
    }
}