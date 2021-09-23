package com.example.example.froggame

import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.R
import android.content.res.Resources

import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.ImageView


class Frog(context: Context) : androidx.appcompat.widget.AppCompatImageView(context) {


    init {
//        setImageResource(R.drawable.frog)
    }

    fun jump(location: Float, h: Int) {
        x = location
        y -= h
        invalidate()
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