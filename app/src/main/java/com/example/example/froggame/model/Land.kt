package com.example.example.froggame.model

class Land {

    private var num = 0
    private val snake = arrayListOf<Snake>()

    fun relocate() {
        for (i in 0 until num) {
            snake[i].setLeft((1..700).random().toFloat())
        }
    }

    fun setLand() {
        num = (1..3).random()
        for (i in 1..num) {
            this.snake.add(Snake())
        }

        relocate()
    }

    fun getNum(): Int {
        return this.num
    }

    fun getSnakes(): ArrayList<Snake> {
        return snake
    }


}