/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utsman.recycling.*
import com.utsman.recycling.core.Pexel
import com.utsman.recycling.core.load
import com.utsman.recycling.core.toast
import com.utsman.recycling.extentions.LoaderIdentifierId
import com.utsman.recycling.extentions.Setup
import com.utsman.recycling.listener.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_view.view.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this)[PexelViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val identifierId = LoaderIdentifierId.Builder()
                .setLayoutRes(R.layout.item_loader)
                .setIdProgressLoader(R.id.progress_circular)
                .setIdTextViewError(R.id.error_text_view)
                .build()

        main_recycler_view.setupAdapter<Pexel>(R.layout.item_view) {

            bind {
                itemView.img_view.load(item?.src?.small)
                itemView.setOnClickListener {
                    toast("Click on $position")
                }
            }



            val layoutManager = GridLayoutManager(this@MainActivity, 2)
            setLayoutManager(layoutManager)
            fixGridSpan(2)

            setupData(this, 1)
            main_recycler_view.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    toast("$page, $totalItemsCount")
                    setupData(this@setupAdapter, page+1)
                }

            })
        }

    }


    private fun setupData(setup: Setup<Pexel>, page: Int) {
        viewModel.getCuratedPhoto(20, page).observe(this, Observer {
            setup.submitList(it)
        })

        /*viewModel.getNetworkState().observe(this, Observer {
            setup.submitNetworkState(it)
        })*/
    }
}