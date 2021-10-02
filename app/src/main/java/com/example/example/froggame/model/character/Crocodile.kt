package com.example.example.froggame.model.character

import android.util.Log
import com.example.example.froggame.model.character.Character

class Crocodile: Character {

    var width = 446
    var reverse = false

    // 악어의 경우 강의 흐름에 따라 반대로 만들어지는 경우 발생하기에
    // 별도의 생성자를 둬서 역방향인지 확인
    constructor()
    constructor(reverse: Boolean) {
        this.reverse = reverse
    }

    override fun setRight(w: Float) {
        super.setRight(446F)
    }

    override fun isFrogGetOn(lFrog: Float, rFrog: Float): Boolean {
        // 악어에 올라탔는지 판단 - 완전히 겹치는지 판단
        if (getLeft() <= lFrog && rFrog <= getRight()) {
            return true
        }
        return false
    }

    fun isCrocodileHead(lFrog: Float, rFrog: Float): Boolean {
        // 강에서 개구리가 건널때 악어의 머리인지 판단해야 하는 부분이 발생한다.
        // 기본은 머리가 오른쪽

        // 강의 방향에 따라 판단이 달라진다.
        if (this.reverse) {
            // 악어의 머리가 왼쪽
            // 일단 개구리가 올라탔다. 왼쪽의 머리와 겹치는지만 판단
            // 개구리의 왼좌표가 악어 머리 끝나는 지점보다 왼쪽이면 겹침.
            return if (lFrog < getLeft() + width * 1 / 4) {
                // 개구리 악어에게 먹힘
                Log.e(
                    "Game - isFrogGetIn",
                    "[DEAD] Frog is eaten by crocodile - The Crocodile's head direction is left (reverse)"
                )
                true
            } else false // 개구리 악어에 잘 올라타서 같이 움직임. 강의 속력과 방향에 맞춰 움직이기 위해 매개변수 설정
        } else {
            // 악어 머리가 오른쪽
            // 일단 개구리 올라탔다. 개구리의 오른쪽이 머리 시작점오다 오른쪽인지 판단
            return if (getLeft() + width * 3 / 4 < rFrog) {
                Log.e(
                    "Game - isFrogGetIn",
                    "[DEAD] Frog is eaten by crocodile - The Crocodile's head direction is right (default)"
                )
                true
            } else false
        }
    }
}