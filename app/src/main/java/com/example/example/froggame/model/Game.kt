package com.example.example.froggame.model

class Game() {

    val frog = Frog()
    val land = Land()
    val river = River()

    fun gameStart():Boolean {
        frog.setFrog()


        return true
    }

    fun move() {

    }

    fun isScore() {

    }

    fun isFrogDead() {

    }
}