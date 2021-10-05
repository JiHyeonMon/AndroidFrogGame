package com.example.example.froggame.model.landform

import com.example.example.froggame.model.character.Character
import com.example.example.froggame.model.character.Crocodile
import com.example.example.froggame.model.character.Timber

class River : LandForm {

    // 강은 속력와 방향을 가진다.
    // 해당 변수들 생성
    // 강의 direction은 기본 1 (오른쪽)
    private var speed: Int = 0
    private var direction: Int = 1

    // 강에 떠다닐 통나무와 악어 생성
    private lateinit var gameCharacter: ArrayList<Character>

    //     강을 만들 때 기본적으로 통나무 2개, 악어 두 마리 생성됨.
    //     이때 좌표값을 겹치지 않게 설정위해서 초기 값을 지정해준다.
    //     각 객체와 겹치지 않게 100 정도씩 떨어뜨리린다.
    constructor() {
        gameCharacter = arrayListOf(
            Timber(0f, 0, 0),
            Crocodile(0f, 0, 0),
            Timber(0f, 0, 0),
            Crocodile(0f, 0, 0)
        )
        gameCharacter[1].left = gameCharacter[0].left - gameCharacter[1].width - 100
        gameCharacter[2].left = gameCharacter[1].left - gameCharacter[2].width - 100
        gameCharacter[3].left = gameCharacter[2].left - gameCharacter[3].width - 100
    }

    //     강의 흐름이 반대인 경우 (오른쪽->왼쪽으로 흐르는 경우)
    //     각 통나무, 악어 객체 생성하고 위치 설정
    //     direction을 -1로 설정한다.
    constructor(reverse: Boolean) {
        gameCharacter = arrayListOf(
            Timber(1080f, 0, 0),
            Crocodile(0f, 0, 0, reverse),
            Timber(0f, 0, 0),
            Crocodile(0f, 0, 0, reverse)
        )

        gameCharacter[1].left = gameCharacter[0].left + gameCharacter[0].width + 100
        gameCharacter[2].left = gameCharacter[1].left + gameCharacter[1].width + 100
        gameCharacter[3].left = gameCharacter[2].left + gameCharacter[2].width + 100


        this.direction = -1

    }

    override fun setLandForm(w: Int) {
        // 강의 속력을 랜덤으로 설정
        this.speed = (2..7).random()

        // 강에 위치한 악어와 통나무에게 강의 속력과 방향 부여해서 강마다 캐릭터들이 다른 속도와 방향을 가지고 움직일 수 있게 한다.
        for (chracter in gameCharacter) {
            chracter.speed = speed
            chracter.direction = direction
        }
    }

    override fun getGameCharacter(): ArrayList<Character> {
        return gameCharacter
    }

    override fun clearLandForm() {
        // 게임 종료시 움직임 멈춘다.
        this.speed = 0
        this.direction = 0
    }

    // 개구리가 강에 올라탔을 때 해당 속력과 속도로 움직이기 위해 getSpeed(), getDirection() 메서드 제공
    fun getSpeed(): Int {
        return this.speed
    }

    fun getDirection(): Int {
        return this.direction
    }


}