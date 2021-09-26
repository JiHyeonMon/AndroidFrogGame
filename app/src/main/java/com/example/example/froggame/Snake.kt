package com.example.example.froggame

import android.content.Context

class Snake(context: Context) : androidx.appcompat.widget.AppCompatImageView(context){

    init {
        setImageResource(R.drawable.snake)
        relocate()
    }

    fun checkFrog(lFrog: Float, rFrog:Float, callback: Callback) {
        if ((this.x<rFrog && this.x+this.width>rFrog) || (this.x+this.width>lFrog && this.x<lFrog)){
            callback.frogDead("snake")
        } else return
    }

    fun relocate() {
        x = (0..1080-width).random().toFloat()
    }
}