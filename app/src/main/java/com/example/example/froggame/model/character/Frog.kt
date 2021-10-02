package com.example.example.froggame.model.character

class Frog : Character() {

    private var y: Float = 0f
    var width = 120

    fun setFrog() {
        // 개구리 처음 위치 지정
        setLeft(500F)
        y = 1060f
    }

    fun jump(h: Int) {
        // 개구리가 점프할 경우
        // 개구리의 y값을 바꿔준다.
        this.y -= h
    }

    fun getY(): Float {
        return y
    }

}