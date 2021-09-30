package com.example.example.froggame.model

class Snake {

    private var left: Float = 0f

    fun getLeft(): Float {
        return this.left
    }

    fun getRight(): Float {
        return this.left + 160
    }

    fun setLeft(l: Float) {
        this.left = l
    }
}