package com.example.example.froggame

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.widget.Toast


class Frog(context: Context) : androidx.appcompat.widget.AppCompatImageView(context) {

    var r: Runnable
    private var frogSpeed = 0
    lateinit var mCallback: Callback

    init {

        setImageResource(R.drawable.frog)
        setBackgroundColor(Color.WHITE)
        scaleType = ScaleType.CENTER_CROP

        r = object : Runnable {
            override fun run() {
                Log.e("frog Speed", "$frogSpeed")
                if (frogSpeed > 0) {
                    if (x + width <= 1080) {
                        x += frogSpeed
                        if (x + width > 1080F) {
                            Toast.makeText(context, "[1-2] Frog Dead - The End", Toast.LENGTH_SHORT)
                                .show()
                            dead()
                        }
                    }

                } else {
                    if (x >= 0) {
                        x += frogSpeed
                        if (x < 0F) {
                            Toast.makeText(context, "[1-1] Frog Dead - The End", Toast.LENGTH_SHORT)
                                .show()
                            dead()
                        }
                    }

                }
                handler.postDelayed(this, 10)
            }


        }
    }

    fun move(speed: Int) {
        this.frogSpeed = speed
        Log.e("in move", "$frogSpeed")

        val handler = Handler()
        handler.post(r)
    }

    fun setCallback(callback: Callback) {
        this.mCallback = callback
    }

    fun dead() {
        this.mCallback.dead()
    }
}