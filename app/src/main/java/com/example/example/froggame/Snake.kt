package com.example.example.froggame

import android.content.Context

class Snake(context: Context) : androidx.appcompat.widget.AppCompatImageView(context){

    lateinit var mNotification: Notification

    init {
        setImageResource(R.drawable.snake)
        relocate()
    }

    fun checkFrog(lFrog: Float, rFrog:Float) {
        if ((this.x<rFrog && this.x+this.width>rFrog) || (this.x+this.width>lFrog && this.x<lFrog)){
            dead("snake")
        } else return
    }

    fun relocate() {
        x = (0..1080-width).random().toFloat()
    }

    fun setCallback(notification: Notification) {
        this.mNotification = notification
    }

    fun dead(reason: String) {
        this.mNotification.frogDead(reason)
    }
}