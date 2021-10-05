package com.example.example.froggame.model.character

import android.util.Log

class Goal : Character() {

    init {
        width = 180f
    }

    override fun isFrogGetOn(lFrog: Float, rFrog: Float): Boolean {
        // 완전히 겹쳐야만 점수 인정
        // 보드판에 개구리가 들어가는지 확인

        if (left < lFrog && rFrog < left + width) {
            // 점수 획득
            // score + 1 시키고, SUCCESS로 게임 오버
            Log.e("Game - isScore", "[SCORE] SUCCESS")
            return true
        }
        return false
    }

}