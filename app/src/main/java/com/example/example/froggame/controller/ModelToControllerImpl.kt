package com.example.example.froggame.controller

import com.example.example.froggame.model.Game

interface ModelToControllerImpl {
    fun gameOver(status: Game.GAMESTATE)
    fun gameFinish()
}