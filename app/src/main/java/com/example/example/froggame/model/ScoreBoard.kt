package com.example.example.froggame.model

class ScoreBoard {

    private var left: Float = 0f

    fun setLeft(l: Float) {
        this.left = l
    }

    fun getLeft(): Float {
        return left
    }

    fun getRight(): Float {
        return left + 180
    }
}