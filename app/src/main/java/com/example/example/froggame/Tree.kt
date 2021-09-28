package com.example.example.froggame

import android.content.Context
import android.os.Handler

class Tree(context: Context) : androidx.appcompat.widget.AppCompatImageView(context), Movement {
    private var speed = 0

    init {
        setImageResource(R.drawable.tree)
        scaleType = ScaleType.CENTER_CROP
    }

    fun getSpeed(): Int = this.speed

    override fun move(speed: Int) {
        this.speed = speed

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (speed > 0) {
                    x += speed
                    if (x > 1080) {
                        x = 0F - width
                    }
                } else {
                    x += speed
                    if (x + width < 0) {
                        x = 1080F
                    }
                }
                handler.postDelayed(this, 10)
            }
        }, 1000)
    }
}