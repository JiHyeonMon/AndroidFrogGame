package com.example.example.froggame.model

import android.os.Handler

class Frog {

    private var y: Float = 1040f
    private var left: Float = 450f

    fun setFrog() {
        this.setLeft(450F)
        this.setY(1040f)
    }

    fun jump(h: Int) {
        this.y -= h
    }

    fun getLeft(): Float {
        return this.left
    }

    fun getRight(): Float {
        return this.left + 150
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