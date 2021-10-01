package com.example.example.froggame.model

class Crocodile {

    private var left: Float = 0f
    var width = 446
    var reverse = false

    // 악어의 경우 강의 흐름에 따라 반대로 만들어지는 경우 발생하기에
    // 별도의 생성자를 둬서 역방향인지 확인
    constructor()
    constructor(reverse: Boolean) {
        this.reverse = reverse
    }

    fun getHead(): Array<Float> {
        // 강에서 개구리가 건널때 악어의 머리인지 판단해야 하는 부분이 발생한다.
        // 이 때, 악어의 머리 좌표를 반환한다.

        // 기본은 머리가 오른쪽

        return if (reverse) {
            // reverse가 True --> 머리가 왼쪽
            // 악어의 머리 = 왼쪽 ~ 몸통의 1/4
            arrayOf(left, left + width * 1 / 4)
        } else {
            // 머리가 오른쪽
            // 악어의 머리 = 악어 몸통의 3/4 ~ 오른쪽 끝까지
            arrayOf(left + width * 3 / 4, left + width)
        }
    }

    fun getLeft(): Float {
        return left
    }

    fun getRight(): Float {
        return getLeft() + width
    }

    fun setLeft(l: Float) {
        this.left = l
    }
}