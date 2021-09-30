package com.example.example.froggame.model

import android.util.Log

class Land {

    private var num = 0
    private val snakes = arrayListOf<Snake>()
    private var boards = arrayOf(ScoreBoard(), ScoreBoard(), ScoreBoard())

    fun relocate() {
        for (i in 0 until num) {
            snakes[i].setLeft((1..700).random().toFloat())
        }
        boards[0].setLeft((0..300).random().toFloat())
        boards[1].setLeft((300..600).random().toFloat())
        boards[2].setLeft((600..900).random().toFloat())
    }

    fun setLand() {
        num = (1..3).random()
        for (i in 1..num) {
            this.snakes.add(Snake())
        }
        Log.e("snake", "$num")

        relocate()
    }

    fun getNum(): Int {
        return this.num
    }

    fun getSnakes(): ArrayList<Snake> {
        return snakes
    }

    fun getBoards(): Array<ScoreBoard> {
        return boards
    }

    fun clear() {
        snakes.clear()
    }


}