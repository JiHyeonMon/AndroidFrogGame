package com.example.example.froggame.model

import android.os.Handler
import android.util.Log
import com.example.example.froggame.controller.ModelToControllerImpl

class Game {
    // 게임상태를 나타내는 상수
    // 게임 중인지, 점수를 취득했는지, 개구리가 죽었는지 여부 판단이 필요 ( + 어떻게 죽었는지)
    // 해당 여부를 GAMESTATE 상수로 판단
    enum class GAMESTATE { IN_PROGRESS, SCORE, SNAKE, CROCODILE, WALL, DROWN }

    // 현재 게임의 상태를 나타낼 state 변수
    var state: GAMESTATE = GAMESTATE.IN_PROGRESS
    // 개구리가 죽었을 때 Controller에게 알리기 위한 콜백 메서드 호출위해
    lateinit var mModelToControllerImpl : ModelToControllerImpl

    // 개구리가 점프할 때마다 처리 로직이 달라져, jumpCnt 상수를 둬서 각 점프마다 로직 처리
    // 기본 게임 시작시 점수 0, game Step 4로 지정
    var jumpCnt = 6
    var score = 0
    var step = 4

    // < 게임 객체 생성 >
    // 게임에 필요한 Frog(), Land() 객체 생성
    val frog = Frog()
    val land = Land()

    // 강의 방향에 따라 선언 차이 발생
    // River() 와 River(reverse: Boolean)으로 생성자를 두개 둬서 분리
    val river1 = River()
    val river2 = River(true)
    val river3 = River()
    val river4 = River(true)

    // 개구리의 움직임을 game에서 실행
    // 개구리 움직임 thread처리를 위해 runnable 객체 선언
    var handler = Handler()
    lateinit var r: Runnable

    fun gameStart() {
        // 게임 시작
        // 점프 횟수 초기화
        this.jumpCnt = 6
        state = GAMESTATE.IN_PROGRESS

        // 각각의 게임 객체 (개구리, 강(통나무/악어), 땅(뱀/점수판)) 초기화 및 생성
        frog.setFrog()
        river1.setRiver()
        river2.setRiver()
        river3.setRiver()
        river4.setRiver()
        land.setLand()
    }


    fun frogJump(h: Int) {
        // 개구리 점프가 발생했을 때,
        // 개구리의 Y 좌표 값 변경
        frog.jump(h)

        // 점프 카운트 1, 각 단계에 맞는 로직 처리
        jumpCnt -= 1
        when (jumpCnt) {
            5 -> {
                // 강 - 개구리가 올라탔는지 확인
                checkFrogGetOn(river4)
            }
            4 -> {
                // 이전에 강에 올라탄 개구리 --> 속도를 가지고 있을 것. 이를 초기화 위해 개구리 움직이는 handler에 runnable 해제를 시킨다.
                // 강 - 개구리가 올라탔는지 확인
                handler.removeCallbacks(r)
                checkFrogGetOn(river3)
            }
            3 -> {
                // 이전에 강에 올라탄 개구리 --> 속도를 가지고 있을 것. 이를 초기화 위해 개구리 움직이는 handler에 runnable 해제를 시킨다.
                // 땅 - 뱀과 만났는지 확인
                handler.removeCallbacks(r)
                checkFrogMeetSnake()
            }
            2 -> {
                // 강 - 개구리가 올라탔는지 확인
                handler.removeCallbacks(r)
                checkFrogGetOn(river2)
            }
            1 -> {
                // 이전에 강에 올라탄 개구리 --> 속도를 가지고 있을 것. 이를 초기화 위해 개구리 움직이는 handler에 runnable 해제를 시킨다.
                // 강 - 개구리가 올라탔는지 확인
                handler.removeCallbacks(r)
                checkFrogGetOn(river1)
            }
            0 -> {
                // 이전에 강에 올라탄 개구리 --> 속도를 가지고 있을 것. 이를 초기화 위해 개구리 움직이는 handler에 runnable 해제를 시킨다.
                // 점수판 - 점수를 딸 수 있는지 여부 판단
                handler.removeCallbacks(r)
                checkScore()
            }
        }
    }

    private fun checkFrogMeetSnake() {
        // 개구리가 뱀이랑 만났는지 확인한다.
        // 땅에 있는 뱀들의 좌표를 알기위해 land.getSnakes()메소드 호출을 통해 snake객체들의 배열을 받는다.
        val snakes = land.getSnakes()

        // 반복문을 통해 하나하나 뱀들과 개구리와 위치를 확인한다.
        for (snake in snakes) {
            // 일단 뱀과 겹치면 뱀과 닿였다고 판단
            if (
                (snake.getLeft() < frog.getRight() && frog.getRight() < snake.getRight()) || // 1. 개구리의 오른좌표가 뱀 내부에 있는 경우 - 개구리의 오른편이 뱀과 겹침
                (snake.getLeft() < frog.getLeft() && frog.getLeft() < snake.getRight()) || // 2. 개구리의 왼 좌표가 뱀 내부에 있는 경우 - 개구리의 왼편이 뱀과 겹침
                (snake.getLeft() < frog.getLeft() && frog.getRight() < snake.getRight()) // 3. 개구리가 완전히 뱀 안에 들어가는 경우 - 완전히 겹침
            ) {
                // 한마리라도 겹치면 겹쳤다고 판단 후 게임오버!
                Log.e("Game - isFrogMeetSnake", "[DEAD] Frog is eaten by Snake")
                gameOver(GAMESTATE.SNAKE)
                break
            }
        }
    }

    private fun checkFrogGetOn(river: River) {
        // 개구리가 통나무나 악어에 올라탔는지 판단한다.
        // 어떤 강인지 River를 받는다.
            // 강마다 방향과 속력이 다르기 때문 + 해당 강의 통나무와 악어의 좌표도 필요

        // 악어에 올라탔는지 판단 - 완전히 겹치는지 판단
        if (river.crocodile.getLeft() <= frog.getLeft() && frog.getRight() <= river.crocodile.getRight()) {
            // 악어의 머리에 탔는지 판단위해 악어의 머리 좌표를 가지고온다.
                // [머리 시작좌표, 머리 끝 좌표]로 Float 2개가 든 배열
            val crocodileHead = river.crocodile.getHead()

            // 강의 방향에 따라 판단이 달라진다.
            if (river.crocodile.reverse) {
                // 악어의 머리가 왼쪽
                    // 일단 개구리가 올라탔다. 왼쪽의 머리와 겹치는지만 판단
                        // 개구리의 왼좌표가 악어 머리 끝나는 지점보다 왼쪽이면 겹침.
                if (frog.getLeft() < crocodileHead[1]) {
                    // 개구리 악어에게 먹힘
                    Log.e(
                        "Game - isFrogGetIn",
                        "[DEAD] Frog is eaten by crocodile - The Crocodile's head direction is left (reverse)"
                    )
                    gameOver(GAMESTATE.CROCODILE)
                } else frogMove(river.getSpeed(), river.getDirection()) // 개구리 악어에 잘 올라타서 같이 움직임. 강의 속력과 방향에 맞춰 움직이기 위해 매개변수 설정
            } else {
                // 악어 머리가 오른쪽
                    // 일단 개구리 올라탔다. 개구리의 오른쪽이 머리 시작점오다 오른쪽인지 판단
                if (crocodileHead[0] < frog.getRight()) {
                    Log.e(
                        "Game - isFrogGetIn",
                        "[DEAD] Frog is eaten by crocodile - The Crocodile's head direction is right (default)"
                    )
                    gameOver(GAMESTATE.CROCODILE)
                } else frogMove(river.getSpeed(), river.getDirection())
            }

        // 통나무1에 올라탔는지 판단
        } else if (frog.getLeft() >= river.timber1.getLeft() &&
            frog.getRight() <= river.timber1.getRight()
        ) {
            // 올라탔다. 강의 속력과 방향대로 움직인다.
            Log.e("Game - isFrogGetIn", "Frog get on the Timber")
            frogMove(river.getSpeed(), river.getDirection())

        // 통나무2에 올라탔는지 판단
        } else if (frog.getLeft() >= river.timber2.getLeft() &&
            frog.getRight() <= river.timber2.getRight()
        ) {
            // 올라탔다. 강의 속력과 방향대로 움직인다.
            Log.e("Game - isFrogGetIn", "Frog get on the Timber")
            frogMove(river.getSpeed(), river.getDirection())

        // 못올라탐 - 죽음
        } else {
            // 물에 빠지고 게임오버
            Log.e("Game - isFrogGetIn", "[DEAD] Frog is drown")
            gameOver(GAMESTATE.DROWN)
        }
    }

    private fun frogMove(speed: Int, direction: Int) {
        // 개구리가 강의 통나무나 악어에 제대로 올라타서 움직인다.
        // handler를 통해 runnable 10ms 마다 실행시켜 개구리를 강의 속도에 맞춰 움직인다.
        handler = Handler()
        r = object : Runnable {
            override fun run() {
                // 개구리가 오른쪽 벽에 닿임 - 죽음 - 게임오버
                if (frog.getRight() > 1080) {
                    Log.e("Game - frogMove", "[DEAD] Frog bump into Right Wall")
                    gameOver(GAMESTATE.WALL)
                    return
                }
                // 개구리가 왼쪽 벽에 닿임 - 죽음 - 게임오버
                if (frog.getLeft() < 0) {
                    Log.e("Game - frogMove", "[DEAD] Frog bump into left Wall")
                    gameOver(GAMESTATE.WALL)
                    return
                }
                // 강의 속력과 방향에 맞춰 이동한다.
                frog.setLeft(frog.getLeft() + speed * direction)
                handler.postDelayed(this, 10)
            }
        }
        handler.post(r)
    }


    private fun checkScore() {
        // 개구리가 마지막 단계까지 가서 점프를 했을 때 점수를 얻었는지 확인
        // 점수판이 있는 좌표를 알기위해 land.getBoard() 메서드를 호출해 ScoreBoard 객체들을 배열로 받는다.
        val boards = land.getBoards()

        // 총 세개의 점수판이 있다. - 완전히 겹쳐야만 점수 인정
        // 각 보드판에 개구리가 들어가는지 확인
        if ((boards[0].getLeft() < frog.getLeft() && frog.getRight() < boards[0].getRight()) ||
            (boards[1].getLeft() < frog.getLeft() && frog.getRight() < boards[1].getRight()) ||
            (boards[2].getLeft() < frog.getLeft() && frog.getRight() < boards[2].getRight())
        ) {
            // 점수 획득
                // score + 1 시키고, SUCCESS로 게임 오버
            Log.e("Game - isScore", "[SCORE] SUCCESS")
            score += 1
            gameOver(GAMESTATE.SCORE)
        } else {
            // 점수판에 못오르고 물에 빠짐 - 게임 오버
            Log.e("Game - isScore", "[DEAD] Frog is drown")
            gameOver(GAMESTATE.DROWN)
        }
    }

    fun setGameOverCallback(modelToControllerImpl: ModelToControllerImpl) {
        mModelToControllerImpl = modelToControllerImpl
    }

    private fun gameOver(state: GAMESTATE) {
        // 게임 오버
        // 재시작위한 준비
        // 점수를 얻고 게임오버 됐을 수도, 개구리가 죽어 게임오버 됐을 수도 있다. 매개변수로 받은 GameOver값을 통해 확인
        // GameOver 값을 보고 어떤 이유로 죽었는지 Controller에게 알려줌. (SUCCESS면 점수 취득 - step 깎지 않는다.)
        if (state != GAMESTATE.SCORE) step -= 1
        mModelToControllerImpl.gameOver(state)


        // 만약 step 이 0이면 정말 게임 끝!
        // finish 호출해 게임을 완전히 끝낸다.
        if (step == 0) {
            finish()
        }
        // 실행 중이던 runnable 종료 및 뱀 초기화.
        river1.handler.removeCallbacks(river1.r)
        river2.handler.removeCallbacks(river2.r)
        river3.handler.removeCallbacks(river3.r)
        river4.handler.removeCallbacks(river4.r)
        land.clear()
    }

    private fun finish() {
        // step 다 쓰고 정말 게임 종료
        // Controller에서 게임 종료 알린다.
        Log.e("gameOver", "[FINISH] - step is $state")
        mModelToControllerImpl.gameFinish()

        // 모든 runnable 해제 및 뱀 초기화.
        handler.removeCallbacks(r)
        river1.handler.removeCallbacks(river1.r)
        river2.handler.removeCallbacks(river2.r)
        river3.handler.removeCallbacks(river3.r)
        river4.handler.removeCallbacks(river4.r)
        land.clear()
    }
}