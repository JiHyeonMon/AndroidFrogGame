package com.example.example.froggame.model

import android.util.Log

class Game {

    enum class GameState { IN_PROGRESS, FINISHED }
    lateinit var state: GameState

    var jumpCnt = 6
    val frog = Frog()
    val land = Land()

    /**
     * 강의 방향
     * River() 와 River(reverse: Boolean)으로 생성자를 두개 둬서 분리
     **/

    val river1 = River()
    val river2 = River(true)
    val river3 = River()
    val river4 = River(true)


    fun gameStart() {
        state = GameState.IN_PROGRESS

        this.jumpCnt = 6
        frog.setFrog()
        river1.setRiver()
        river2.setRiver()
        river3.setRiver()
        river4.setRiver()
        land.setLand()
    }

    fun frogJump(h: Int) {
        frog.jump(h)
        jumpCnt -= 1
        when (jumpCnt) {
            5 -> isFrogGetIn(river4)
            4 -> {
                frog.stop()
                isFrogGetIn(river3)
            }
            3 -> {
                frog.stop()
                isFrogMeetSnake()
            }
            2 -> {
                frog.stop()
                isFrogGetIn(river2)
            }
            1 -> {
                frog.stop()
                isFrogGetIn(river1)
            }
            0 -> {
                frog.stop()
                isScore()
            }
        }
    }

    fun isFrogMeetSnake(): Boolean {
        val snakes = land.getSnakes()
        val snakeNumber = land.getNum()
        for (i in 0 until snakeNumber) {
            if ((snakes[i].getLeft() < frog.getRight() && snakes[i].getRight() > frog.getRight()) || (snakes[i].getRight() > frog.getLeft() && snakes[i].getLeft() < frog.getLeft())) {
                Log.e("Game - isFrogMeetSnake", "[DEAD] Frog is eaten by Snake")
                gameOver()
                return true
            }
        }
        return false
    }

    fun isFrogGetIn(river: River) {
        if (frog.getLeft() >= river.crocodile.getLeft() && frog.getRight() <= river.crocodile.getRight()) {
            val crocodileHead = river.crocodile.getHead()

            // 악어의 머리가 왼쪽
            if (river.crocodile.reverse) {
                if (frog.getLeft()<crocodileHead[1]) {
                    Log.e("Game - isFrogGetIn", "[DEAD] Frog is eaten by crocodile - The Crocodile's head direction is left (reverse)")
                    gameOver()
                } else frog.move(river.getSpeed(), river.getDirection())
            } else {
                // 악어 머리가 오른쪽
                if (frog.getRight()>crocodileHead[0]) {
                    Log.e("Game - isFrogGetIn", "[DEAD] Frog is eaten by crocodile - The Crocodile's head direction is right (default)")
                    gameOver()
                } else frog.move(river.getSpeed(), river.getDirection())
            }

        } else if (frog.getLeft() >= river.timber1.getLeft() &&
            frog.getRight() <= river.timber1.getRight()
        ) {
            Log.e("Game - isFrogGetIn", "Frog get on the Timber")
            frog.move(river.getSpeed(), river.getDirection())
        } else {
            // 물에 빠짐
            Log.e("Game - isFrogGetIn", "[DEAD] Frog is drown")
            gameOver()
        }
    }

    private fun isScore() {
        
    }

    private fun gameOver() {
        frog.move(0,0)
        state = GameState.FINISHED
    }
}