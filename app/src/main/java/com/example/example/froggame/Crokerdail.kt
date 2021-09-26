package com.example.example.froggame

import android.content.Context
import android.os.Handler
import android.widget.Toast

class Crokerdail(context: Context, reverse: Boolean) :
    androidx.appcompat.widget.AppCompatImageView(context) {
    private var speed = 0
    private var reverse: Boolean = false

    init {
        setImageResource(R.drawable.crokerdail)
        if (reverse) {
            this.reverse = true
            rotationY = 180F
        }
    }
    fun getSpeed(): Int = this.speed

    fun move(speed: Int) {
        this.speed = speed
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


    fun isHead(lFrog: Float, rFrog: Float): Boolean {
        if (this.reverse) {
            // isTrue - 머리가 왼쪽
            if (lFrog>=this.x && lFrog<this.x+this.width/3) return true
        } else {
            // 머리가 오른쪽
            if (rFrog<this.x+this.width && rFrog>this.width*2/3) return true
        }
        return false
    }
}