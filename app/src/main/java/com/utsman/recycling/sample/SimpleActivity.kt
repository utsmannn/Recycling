/*
 * Created by Muhammad Utsman on 8/31/19 12:30 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/31/19 12:30 AM
 */

package com.utsman.recycling.sample

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.utsman.recycling.setupAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.simple_item_view.view.*

class SimpleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listData = listOf("satu", "dua", "tiga", "empat")
        val listLoop = listOf("lima", "enem", "tujuh")

        main_recycler_view.setupAdapter<String>(R.layout.simple_item_view) {adapter, context, list ->
            bind { itemView, position, item ->
                itemView.name_item.text = item
            }

            submitList(listData)

            Handler().postDelayed({
                listLoop.map { item ->
                    submitItem(item)
                }
            }, 1000)

        }
    }
}