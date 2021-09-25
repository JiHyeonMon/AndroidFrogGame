package com.example.example.froggame

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.Toast

class Snake(context: Context) : androidx.appcompat.widget.AppCompatImageView(context){

    init {
        setImageResource(R.drawable.snake)
//        setBackgroundColor(Color.BLACK)
        setSnake()
    }

    fun checkFrog(lFrog: Float, rFrog:Float, callback: Callback) {
        if ((this.x<rFrog && this.x+this.width>rFrog) || (this.x+this.width>lFrog && this.x<lFrog)){
            callback.callback()
        } else return
    }

    fun setSnake() {
        x = (0..1080).random().toFloat()
    }
}