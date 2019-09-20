/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:55 AM
 */

package com.utsman.recycling.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.utsman.recycling.*
import com.utsman.recycling.core.Pexel
import com.utsman.recycling.core.load
import com.utsman.recycling.extentions.Recycling
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_view.view.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this)[PexelViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_recycler_view.setupAdapter<Pexel>(R.layout.item_view) { adapter, context, list ->

            bind { itemView, position, item ->
                itemView.img_view.load(item?.src?.small)
                itemView.setOnClickListener {
                    Toast.makeText(context, "wee ${adapter.itemCount} - ${list.size} - $position", Toast.LENGTH_SHORT).show()
                }
            }

            addLoader(R.layout.item_loader) {
                idLoader = R.id.progress_circular
                idTextError = R.id.error_text_view
            }

            val layoutManager = GridLayoutManager(this@MainActivity, 2)
            setLayoutManager(layoutManager)
            fixGridSpan(2)

            setupData(this, 1)

            onPagingListener(layoutManager) { page, itemCount ->
                setupData(this@setupAdapter, page+1)
            }
        }

    }


    private fun setupData(recycling: Recycling<Pexel>, page: Int) {
        viewModel.getCuratedPhoto(20, page).observe(this, Observer {
            recycling.submitList(it)
        })

        viewModel.getNetworkState().observe(this, Observer {
            recycling.submitNetworkState(it)
        })
    }
}