package com.example.example.froggame.model.character

import com.example.example.froggame.model.GameConfig

class Frog : Character() {

    var y: Float = 0f

    fun setFrog(w: Int, h: Int) {
        // 개구리 처음 위치 지정
        // 화면의 가로상의 중간에 위치할 수 있게 설정
        // 총 6개의 지형을 지나 위치할 수 있게 설정
        left = ((w/2-GameConfig.FROG_WIDTH/2).toFloat())
        y = (h*6/10).toFloat()
    }

}