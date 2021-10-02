package com.example.example.froggame.model.landform

import com.example.example.froggame.model.character.Snake

class Land : LandForm() {

    private var num = 0
    private val snakes = arrayListOf<Snake>()

    // 게임이 재시작될 때마다 호출
    // 새로 뱀 생성위해 생성될 뱀 숫자 랜덤하게 뽑고, 해당 개수만큼 Snake객체 생성해서 배열에 넣는다.
    override fun setLandForm() {
        num = (1..3).random()
        for (i in 1..num) {
            this.snakes.add(Snake())
        }

        relocate()
    }

    override fun clearLandForm() {
        snakes.clear()
    }

    // 위치를 지정한다.
    // 뱀의 위치 랜덤으로 지정
    private fun relocate() {
        for (i in 0 until num) {
            snakes[i].setLeft((1..700).random().toFloat())
        }
    }

}