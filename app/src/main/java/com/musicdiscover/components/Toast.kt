package com.musicdiscover.components

import android.content.Context
import android.widget.Toast
import com.musicdiscover.R

class Toast() {
    fun makeAndShow(context: Context, text: Int, time: Int){
        Toast.makeText(
            context,
            text,
            time
        ).show()
    }
}