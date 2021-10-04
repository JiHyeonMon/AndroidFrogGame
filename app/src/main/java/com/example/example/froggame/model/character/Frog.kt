package com.example.example.froggame.model.character

class Frog : Character() {

    init {
        width = 120f
    }

    private var y: Float = 0f

    override fun move() {
        // 흐르기 시작
//        if (direction > 0) {
//            // 순방향 (왼 --> 오) 인 경우
//            if (left > 1080) {
//                left = 0f - width - 150
//            }
//        } else {
//            // 역방향 (오 --> 왼) 인 경우
//            if (left+width < 0) {
//                left = 1080f
//            }
//        }
        // 통나무1, 2, 악어가 강의 속력과 방향에 맞춰 움직이게 한다.
        left += speed * direction
    }

    fun setFrog() {
        // 개구리 처음 위치 지정
        left = 500F

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