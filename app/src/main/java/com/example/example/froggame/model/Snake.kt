package com.example.example.froggame.model

class Snake {

    private var left: Float = 0f
    private var right: Float = 0f

    fun getLeft(): Float {
        return this.left
    }

    fun getRight(): Float {
        return this.right
    }

    fun setLeft(l: Float) {
        this.left = l
    }

    fun getRight(r: Float) {
        this.right = r
    }
}