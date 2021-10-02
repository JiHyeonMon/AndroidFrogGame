package com.example.example.froggame.model.character

import android.util.Log
import com.example.example.froggame.model.Game

class Goal: Character() {

//    val width = 180
    init {
        width = 180f
    }

    override fun isFrogGetOn(lFrog: Float, rFrog: Float): Boolean {
        // 총 세개의 점수판이 있다. - 완전히 겹쳐야만 점수 인정
        // 각 보드판에 개구리가 들어가는지 확인
//        if ((boards[0].getLeft() < frog.getLeft() && frog.getRight() < boards[0].getRight()) ||
//            (boards[1].getLeft() < frog.getLeft() && frog.getRight() < boards[1].getRight()) ||
//            (boards[2].getLeft() < frog.getLeft() && frog.getRight() < boards[2].getRight())
//        ) {
//            // 점수 획득
//            // score + 1 시키고, SUCCESS로 게임 오버
//            Log.e("Game - isScore", "[SCORE] SUCCESS")
//            score += 1
//            gameOver(Game.GAMESTATE.SCORE)
//        } else {
//            // 점수판에 못오르고 물에 빠짐 - 게임 오버
//            Log.e("Game - isScore", "[DEAD] Frog is drown")
//            gameOver(Game.GAMESTATE.DROWN)
//        }

        if (left<lFrog && rFrog<right) {
            // 점수 획득
            // score + 1 시키고, SUCCESS로 게임 오버
            Log.e("Game - isScore", "[SCORE] SUCCESS")
            return true
        }
        return false
    }

}