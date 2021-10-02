package com.example.example.froggame.model.landform

import com.example.example.froggame.model.character.Character
import com.example.example.froggame.model.character.Goal

class GoalPosition: LandForm() {

    lateinit var goals: ArrayList<Character>

    // 게임이 재시작될 때마다 호출
    // 새로 뱀 생성위해 생성될 뱀 숫자 랜덤하게 뽑고, 해당 개수만큼 Snake객체 생성해서 배열에 넣는다.
    override fun setLandForm() {
        goals = arrayListOf(Goal(), Goal(), Goal())
        relocate()
    }

    override fun getGameCharacter(): ArrayList<Character> {
        return goals
    }

    override fun clearLandForm() {
        goals.clear()
    }

    // 위치를 지정한다.
    // 뱀의 위치 랜덤으로 지정
    // 점수판의 위치로 조금 거리를 띄워 랜덤으로 지정
    private fun relocate() {
        goals[0].left = (0..100).random().toFloat()
        goals[1].left = (300..400).random().toFloat()
        goals[2].left = (600..900).random().toFloat()
    }

}