package com.example.example.froggame.model

class Snake {

    private var left: Float = 0f
    val width = 160

    fun getLeft(): Float {
        return this.left
    }

    fun getRight(): Float {
        return this.left + width
    }

    fun setLeft(l: Float) {
        this.left = l
    }
}