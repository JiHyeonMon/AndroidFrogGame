package com.example.example.froggame.model

class Crocodile {

    private var left: Float = 0f
    var width = 446
    var reverse = false

    constructor()
    constructor(reverse: Boolean) {
        this.reverse = reverse
    }

    fun getHead(): Array<Float> {
        // 기본은 머리가 오른쪽

        return if (reverse) {
            // 머리가 왼쪽
            // 악어의 머리 = 왼쪽 ~ 몸통의 1/3
            arrayOf(left, left + width * 1 / 4)
        } else {
            // 머리가 오른쪽
            // 악어의 머리 = 악어 몸통의 2/3 ~ 오른쪽 끝까지
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