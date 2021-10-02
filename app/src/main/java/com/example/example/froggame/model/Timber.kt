package com.example.example.froggame.model

class Timber {

    private var left: Float = 0f
    val width = 341

    fun isFrogGetOn(lFrog: Float, rFrog: Float): Boolean {
        // 악어에 올라탔는지 판단 - 완전히 겹치는지 판단
        if (left <= lFrog && rFrog <= left + width) {
            return true
        }
        return false
    }

    fun getLeft(): Float {
        return left
    }

    fun getRight(): Float {
        return getLeft() + width
    }

    fun setLeft(l: Float) {
        this.left = l
    }
}