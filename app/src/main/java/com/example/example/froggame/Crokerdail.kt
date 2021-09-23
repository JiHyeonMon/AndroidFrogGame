package com.example.example.froggame

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Handler
import android.view.animation.AnimationUtils

class Crokerdail(context: Context, reverse: Boolean) :
    androidx.appcompat.widget.AppCompatImageView(context) {

    init {
        setImageResource(R.drawable.crokerdail)
        if (reverse) {
            rotationY = 180F
        }
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
}