package com.example.example.froggame.model

class Timber {

    private var left: Float = 0f
    val width = 341

    fun getLeft(): Float {
        return left
    }

    fun getRight(): Float {
        return left + width
    }

    fun setLeft(l: Float) {
        this.left = l
    }
}