package com.example.example.froggame.model.character

abstract class Character {

    // 게임에 사용되는 캐릭터들은 모두 좌표와 속력과 방향을 가진다.
    open var left: Float = 0.0f
    open var width: Float = 0.0f

    open var speed: Int = 0
    open var direction: Int = 1

    // 캐릭터들은 움직일 수 있다.
    open fun move() {
        // 흐르기 시작
        // 설정된 속력과 방향에 맞춰 움직이게 한다.
        left += speed * direction
    }

    // 개구리를 제외한 게임 캐릭터는 개구리가 올라탔는지 확인하는 절차가 필요하다.
    // 해당 절차를 아래의 메서드에서 구현 (개구리의 위치를 받아 올라탔는지 판별)
    open fun isOverlap(lFrog: Float, rFrog: Float): Boolean {

        // 완전히 개구리와 겹치는지 확인한다.
        if (left <= lFrog && rFrog <= left+width) {
            // 개구리가 완전히 들어오면 true 반환
            return true
        }
        return false
    }

}