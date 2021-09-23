package com.example.example.froggame

import android.content.Context
import android.os.Handler

class Tree(context: Context) : androidx.appcompat.widget.AppCompatImageView(context) {

    init {
        setImageResource(R.drawable.tree)
        scaleType = ScaleType.CENTER_CROP
    }

    fun move(speed: Int) {
        val handler = Handler()

        val r: Runnable = object : Runnable {
            override fun run() {
                if (speed > 0) {
                    x += speed
                    if (x > 1080) {
                        x = 0F - width
                    }
                } else {
                    x += speed
                    if (x+width < 0) {
                        x = 1080F
                    }
                }

                handler.postDelayed(this, 10)
            }
        }

        handler.postDelayed(r, 1000)

    }

//    fun check(frog_x: Int, frog_w: Int) {
//        if (frog_x > x &&)
//    }

}