package com.example.example.froggame.model.character

abstract class Character {

    private var left = 0.0f
    private var right: Float = 0.0f

    private var speed: Int = 0
    private var direction: Int = 1

    open fun move() {}

    open fun setLeft(l: Float) {}

    open fun setRight(w: Float) {}

    open fun setSpeed(s: Int) {}

    open fun setDirection(d: Int) {}

    open fun getLeft(): Float {
        return left
    }

    open fun getRight(): Float {
        return right
    }

    open fun getSpeed(): Int {
        return speed
    }

    open fun getDirection(): Int {
        return direction
    }

    open fun isFrogGetOn(lFrog: Float, rFrog: Float): Boolean {
        return true
    }

}