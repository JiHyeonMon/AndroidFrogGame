package com.example.example.froggame.model

class Land{

    val snake = Snake()

    fun relocate() {
        snake.setLeft((0..1080).random().toFloat())
    }



}