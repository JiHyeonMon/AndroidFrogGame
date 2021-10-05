package com.example.example.froggame.model.character

class Frog : Character() {

    init {
        width = 120f
    }

    var y: Float = 0f

    fun setFrog(w: Int, h: Int) {
        // 개구리 처음 위치 지정
        // 화면의 가로상의 중간에 위치할 수 있게 설정
        // 총 6개의 지형을 지나 위치할 수 있게 설정
        left = (w/2-width/2)
        y = (h*6/10).toFloat()
    }

}