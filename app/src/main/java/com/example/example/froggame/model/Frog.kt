package com.example.example.froggame.model

class Frog {

    private var y: Float = 0f
    private var left: Float = 0f
    private var right: Float = 0f

    fun setFrog() {
        val frog = Frog()
        frog.setLeft(450F)
        frog.setY(1055F)
    }

    fun jump(h: Int) {
        this.y -= h
    }

    fun getLeft(): Float {
        return this.left
    }

    fun getRight(): Float {
        return this.right
    }

    fun setLeft(l: Float) {
        this.left = l
    }

    fun setY(h: Float) {
        this.y = h
    }

}