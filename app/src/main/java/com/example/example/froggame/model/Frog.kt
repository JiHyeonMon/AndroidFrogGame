package com.example.example.froggame.model

import android.os.Handler

class Frog {

    private var y: Float = 1040f
    private var left: Float = 450f

    lateinit var r : Runnable
    var handler = Handler()

    fun setFrog() {
        this.setLeft(450F)
        this.setY(1040f)
    }

    fun jump(h: Int) {
        this.y -= h
    }

    fun move(speed: Int, direction: Int) {
        r = object : Runnable {
            override fun run() {
                setLeft(getLeft() + speed * direction)
                handler.postDelayed(this, 10)
            }
        }
        handler.post(r)
    }

    fun stop() {
        handler.removeCallbacks(r)
    }

    fun getLeft(): Float {
        return this.left
    }

    fun getRight(): Float {
        return this.left + 158
    }

    fun getY(): Float {
        return this.y
    }

    fun setLeft(l: Float) {
        this.left = l
    }

    fun setY(h: Float) {
        this.y = h
    }

}