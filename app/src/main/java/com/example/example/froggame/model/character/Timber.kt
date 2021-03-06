package com.example.example.froggame.model.character

import android.util.Log

class Timber(l: Float, s: Int, d: Int): Character() {

    init {
        this.left = l
        this.width = 400f
        this.speed = s
        this.direction = d
    }

    override fun move() {
        // 흐르기 시작
        if (direction > 0) {
            // 순방향 (왼 --> 오) 인 경우
            if (left > 1440) {
                left = 0f - width - 100
            }
        } else {
            // 역방향 (오 --> 왼) 인 경우
            if (left+width < 0) {
                left = 1440f + 100
            }
        }
        // 통나무1, 2, 악어가 강의 속력과 방향에 맞춰 움직이게 한다.
        left += speed * direction
    }
}