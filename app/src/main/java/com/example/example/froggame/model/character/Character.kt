package com.example.example.froggame.model.character

abstract class Character {

    open var left: Float = 0.0f
    open var width: Float = 0.0f
    open var right: Float = left+width


    open var speed: Int = 0
    open var direction: Int = 1

    open fun move() {}

    open fun isFrogGetOn(lFrog: Float, rFrog: Float): Boolean {
        return true
    }

}