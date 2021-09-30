package com.example.example.froggame.model

import android.os.Handler
import android.util.Log

class Game {

    enum class GameState { IN_PROGRESS, GAMEOVER, FINISHED }

    lateinit var state: GameState

    var jumpCnt = 6
    var score = 0
    var step = 4

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

    var handler = Handler()
    lateinit var r: Runnable


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

            // why frog.stop() all times?
            // to do reset r - speed 0
            5 -> {
                isFrogGetIn(river4)
            }
            4 -> {
                handler.removeCallbacks(r)
                isFrogGetIn(river3)
            }
            3 -> {
                handler.removeCallbacks(r)
                isFrogMeetSnake()
            }
            2 -> {
                handler.removeCallbacks(r)
                isFrogGetIn(river2)
            }
            1 -> {
                handler.removeCallbacks(r)
                isFrogGetIn(river1)
            }
            0 -> {
                handler.removeCallbacks(r)
                isScore()
            }
        }
    }

    private fun isFrogMeetSnake(): Boolean {
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

    private fun isFrogGetIn(river: River) {
        if (frog.getLeft() >= river.crocodile.getLeft() && frog.getRight() <= river.crocodile.getRight()) {
            val crocodileHead = river.crocodile.getHead()

            // 악어의 머리가 왼쪽
            if (river.crocodile.reverse) {
                if (frog.getLeft() < crocodileHead[1]) {
                    Log.e(
                        "Game - isFrogGetIn",
                        "[DEAD] Frog is eaten by crocodile - The Crocodile's head direction is left (reverse)"
                    )
                    gameOver()
                } else frogMove(river.getSpeed(), river.getDirection())
            } else {
                // 악어 머리가 오른쪽
                if (frog.getRight() > crocodileHead[0]) {
                    Log.e(
                        "Game - isFrogGetIn",
                        "[DEAD] Frog is eaten by crocodile - The Crocodile's head direction is right (default)"
                    )
                    gameOver()
                } else frogMove(river.getSpeed(), river.getDirection())
            }

        } else if (frog.getLeft() >= river.timber1.getLeft() &&
            frog.getRight() <= river.timber1.getRight()
        ) {
            Log.e("Game - isFrogGetIn", "Frog get on the Timber")
            frogMove(river.getSpeed(), river.getDirection())
        } else {
            // 물에 빠짐
            Log.e("Game - isFrogGetIn", "[DEAD] Frog is drown")
            gameOver()
        }
    }

    fun frogMove(speed: Int, direction: Int) {
        handler = Handler()

        r = object : Runnable {
            override fun run() {
                if (frog.getRight() > 1080) {
                    Log.e("Game - frogMove", "[DEAD] Frog bump into Right Wall")
                    gameOver()
                    return
                }
                if (frog.getLeft() < 0) {
                    Log.e("Game - frogMove", "[DEAD] Frog bump into left Wall")
                    gameOver()
                    return
                }
                frog.setLeft(frog.getLeft() + speed * direction)
                handler.postDelayed(this, 10)
            }
        }
        handler.post(r)
    }


    private fun isScore() {
        val boards = land.getBoards()

        Log.e("score", "${boards[0].getLeft()} ${boards[0].getRight()} ${frog.getLeft()} ${frog.getRight()}")
        Log.e("score", "${boards[1].getLeft()} ${boards[1].getRight()} ${frog.getLeft()} ${frog.getRight()}")
        Log.e("score", "${boards[2].getLeft()} ${boards[2].getRight()} ${frog.getLeft()} ${frog.getRight()}")

        if ((boards[0].getLeft() < frog.getLeft() && frog.getRight() < boards[0].getRight())||
            (boards[1].getLeft() < frog.getLeft() && frog.getRight() < boards[1].getRight())||
            (boards[2].getLeft() < frog.getLeft() && frog.getRight() < boards[2].getRight())){
                // 점수 획득
            Log.e("Game - isScore", "[SCORE] SUCCESS")
            score += 1
        } else {
            // 점수판에 못오르고 물에 빠짐
            Log.e("Game - isScore", "[DEAD] Frog is drown")
            gameOver()
        }
    }

    private fun gameOver() {
        step -= 1
        if (step == 0) {
            finish()
        }
        state = GameState.GAMEOVER
        land.clear()

        river1.handler.removeCallbacks(river1.r)
        river2.handler.removeCallbacks(river2.r)
        river3.handler.removeCallbacks(river3.r)
        river4.handler.removeCallbacks(river4.r)
    }

    private fun finish() {
        state = GameState.FINISHED
        handler.removeCallbacks(r)
        river1.handler.removeCallbacks(river1.r)
        river2.handler.removeCallbacks(river2.r)
        river3.handler.removeCallbacks(river3.r)
        river4.handler.removeCallbacks(river4.r)
    }
}