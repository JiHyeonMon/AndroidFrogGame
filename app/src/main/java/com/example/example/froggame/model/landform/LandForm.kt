package com.example.example.froggame.model.landform

import com.example.example.froggame.model.character.Character

abstract class LandForm {
    // 게임에 등장하는 여러 지형 설정

    // 게임 시작 후 지형의 초기화 및 지형 안에 들어갈 캐릭터 설정
    open fun setLandForm(w: Int) {}

    // 게임이 종료되면 해당 지형의 캐릭터들 삭제
    open fun clearLandForm() {}

    // 해당 지형에 위치한 캐릭터들 반환
    // 어떤 캐릭터가 있고 어떤 좌표에 위치하는지 식별할 때 필요
    // 배열로 반환
    open fun getGameCharacter(): ArrayList<Character> {
        return arrayListOf()
    }
}