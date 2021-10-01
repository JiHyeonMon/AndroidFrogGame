package com.example.example.froggame.model

class Frog {

    private var y: Float = 0f
    private var left: Float = 0f
    val width = 120

    fun setFrog() {
        // 개구리 처음 위치 지정
        this.setLeft(500f)
        this.setY(1060f)
    }

    fun jump(h: Int) {
        // 개구리가 점프할 경우
        // 개구리의 y값을 바꿔준다.
        this.y -= h
    }

    fun getLeft(): Float {
        return this.left
    }

    fun getRight(): Float {
        return this.left + width
    }

    fun getY(): Float {
        return this.y
    }

    fun setLeft(l: Float) {
        this.left = l
    }

    private fun setY(h: Float) {
        this.y = h
    }

}