/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/17/19 6:47 AM
 */

package com.utsman.recycling.core

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

fun logi(msg: String) = Log.i("anjay", msg)
fun loge(msg: String) = Log.e("anjay", msg)

fun ImageView.load(url: String?) = Picasso.get().load(url).into(this)
fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun loadImg(imgView: ImageView, url: String?) = Picasso.get().load(url).into(imgView)