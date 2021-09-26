package com.example.example.froggame

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.Log


class Frog(context: Context) : androidx.appcompat.widget.AppCompatImageView(context),
    Movement {

    var r: Runnable
    private var speed = 0
    lateinit var mNotification: Notification

    init {
        setImageResource(R.drawable.frog)
        setBackgroundColor(Color.WHITE)
        scaleType = ScaleType.CENTER_CROP

        r = object : Runnable {
            override fun run() {
                Log.e("frog Speed", "$speed")
                if (speed > 0) {
                    if (x + width <= 1080) {
                        x += speed
                        if (x + width > 1080F) {
                            dead("right")
                        }
                    }
                } else {
                    if (x >= 0) {
                        x += speed
                        if (x < 0F) {
                            dead("left")
                        }
                    }

                }
                handler.postDelayed(this, 10)
            }
        }
    }

    override fun move(speed: Int) {
        this.speed = speed
        Log.e("in move", "$speed")

        val handler = Handler()
        handler.post(r)
    }

    fun setCallback(notification: Notification) {
        this.mNotification = notification
    }

    fun dead(direction: String) {
        this.mNotification.frogDead(direction)
    }
}