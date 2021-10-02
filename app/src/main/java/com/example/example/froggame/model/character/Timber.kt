package com.example.example.froggame.model.character

import com.example.example.froggame.model.character.Character

class Timber: Character() {

    override fun setRight(w: Float) {
        super.setRight(341F)
    }



    override fun move() {
        super.move()
        // 흐르기 시작
        if (getDirection() > 0) {
            // 순방향 (왼 --> 오) 인 경우
            if (getLeft() > 1080) {
                setLeft(0f)
            }
        } else {
            // 역방향 (오 --> 왼) 인 경우
            if (getRight() < 0) {
                setLeft(getRight() + 150)
            }
        }
        // 통나무1, 2, 악어가 강의 속력과 방향에 맞춰 움직이게 한다.
        setLeft(getLeft() + getSpeed() * getDirection())
    }

    override fun isFrogGetOn(lFrog: Float, rFrog: Float): Boolean {
        // 악어에 올라탔는지 판단 - 완전히 겹치는지 판단
        if (getLeft() <= lFrog && rFrog <= getRight()) {
            return true
        }
        return false
    }


}