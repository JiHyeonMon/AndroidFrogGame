package com.example.example.froggame.model.landform

import com.example.example.froggame.model.character.Character
import com.example.example.froggame.model.character.Goal

class Destination : LandForm() {

    lateinit var goals: ArrayList<Character>

    // 게임이 재시작될 때마다 호출
    // 해당 Destination지형에서는 3개의 Goal이 존재한다.
    override fun setLandForm(w: Int) {
        goals = arrayListOf(Goal(), Goal(), Goal())

        // 위치 랜덤으로 지정
        // 점수판의 위치로 조금 거리를 띄워 랜덤으로 지정
        goals[0].left = (0..100).random().toFloat()
        goals[1].left = (w / 3..w / 3 + 100).random().toFloat()
        goals[2].left = (w * 2 / 3..w * 2 / 3 + 100).random().toFloat()
    }

    override fun getGameCharacter(): ArrayList<Character> {
        return goals
    }

    override fun clearLandForm() {
        goals.clear()
    }

}