package com.example.example.froggame

import android.content.Context
import android.widget.ImageView
import android.widget.Toast

class Snake(context: Context) : androidx.appcompat.widget.AppCompatImageView(context), MainActivity.check {

    init {
        setImageResource(R.drawable.snake)
        x = (0..1080).random().toFloat()
    }

    override fun check(xF: Int, wF:Int) {
        if (xF>x){
            Toast.makeText(context, "frog is bigger", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(context, "$xF  $x", Toast.LENGTH_SHORT).show()

    }


}