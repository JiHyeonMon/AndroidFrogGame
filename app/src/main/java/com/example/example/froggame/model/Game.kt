package com.example.example.froggame.model

import android.util.Log

class Game {
    // 게임상태를 나타내는 상수
    // 게임 중인지, 점수를 취득했는지, 개구리가 죽었는지 여부 판단이 필요 ( + 어떻게 죽었는지)
    // 해당 여부를 GAMESTATE 상수로 판단
    enum class GAMESTATE { IN_PROGRESS, SCORE, SNAKE, CROCODILE, WALL, DROWN, FINISHED }

    // 현재 게임의 상태를 나타낼 state 변수
    var state: GAMESTATE = GAMESTATE.IN_PROGRESS

    // 기본 게임 시작시 점수 0, game Step 4로 지정
    var score = 0
    var step = 6
    var jumpCnt = 6

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

    // 개구리가 움직일 frogMove()에서 어떤 강인지 식별할 변수 - 강의 정보 가지고 있음.
    // 개구리가 점프함에 따라 통나무나 악어에 올라타서 강과 함께 움직일지, 빠져죽을지 여부
    // 제대로 올라탔을 때, 어떤 강인지 (흐를 수 있게) 명시
    // 물에 빠지면 null 값
    var frogFlowOnRiver: River? = null

    fun gameStart() {
        // 게임 시작
        // 게임 시작 상태인 GAMESTATE.IN_PROGRESS 설정
        state = GAMESTATE.IN_PROGRESS
        jumpCnt = 6

        // 개구리가 움직이는지 판단하는 값 - 강에 있는지 유무
        // 맨 처음 개구리는 강에 있지 않다. null --> 움직이지 않는다.
        frogFlowOnRiver = null

        // 각각의 게임 객체 (개구리, 강(통나무/악어), 땅(뱀/점수판)) 초기화 및 생성
        frog.setFrog()
        river1.setRiver()
        river2.setRiver()
        river3.setRiver()
        river4.setRiver()
        land.setLand()
    }

    fun progress() {
        // Action
        // Controller --> Model
        // 강이 흐르게 함.
        river1.flow()
        river2.flow()
        river3.flow()
        river4.flow()

        // 개구리가 강 위의 객체에 제대로 올라타서 움직이는가
        // frogFlowOnRiver값이 있다면 제대로 올라탄 강이 있는거
        if (frogFlowOnRiver != null) {
            frogMove(frogFlowOnRiver)
        }
    }

    fun frogJump(h: Int) {
        // 개구리 점프가 발생했을 때,
        // 개구리의 Y 좌표 값 h만큼 변경
        frog.jump(h)

        jumpCnt -= 1
        // 점프 카운트 1, 각 단계에 맞는 로직 처리
        when (jumpCnt) {
            5 -> {
                // 강 - 개구리가 올라탔는지 확인
                isFrogGetOn(river4)
            }
            4 -> {
                // 강 - 개구리가 올라탔는지 확인
                isFrogGetOn(river3)
            }
            3 -> {
                // 땅 - 뱀과 만났는지 확인
                frogFlowOnRiver = null
                checkFrogMeetSnake()
            }
            2 -> {
                // 강 - 개구리가 올라탔는지 확인
                isFrogGetOn(river2)
            }
            1 -> {
                // 강 - 개구리가 올라탔는지 확인
                isFrogGetOn(river1)
            }
            0 -> {
                // 점수판 - 점수를 딸 수 있는지 여부 판단
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

    private fun isFrogGetOn(river: River): River? {
        // 개구리가 통나무나 악어에 올라탔는지 판단한다.
        // 어떤 강인지 River를 받는다.
        // 강마다 방향과 속력이 다르기 때문 + 해당 강의 통나무와 악어의 좌표도 필요

        // 악어에 올라탔는지 판단 - 완전히 겹치는지 판단
        if (river.crocodile.isFrogGetOn(frog.getLeft(), frog.getRight())) {
            // 악어에 제대로 올라탔다.
            if (river.crocodile.isCrocodileHead(frog.getLeft(), frog.getRight())) {
                // 악어 머리냐
                gameOver(GAMESTATE.CROCODILE)
            } else {
                frogFlowOnRiver = river
                frogMove(river)
            }
            // 통나무1에 올라탔는지 판단
        } else if (river.timber1.isFrogGetOn(frog.getLeft(), frog.getRight())) {
            // 올라탔다. 강의 속력과 방향대로 움직인다.
            Log.e("Game - isFrogGetIn", "Frog get on the Timber")
            frogFlowOnRiver = river
            frogMove(river)

            // 통나무2에 올라탔는지 판단
        } else if (river.timber2.isFrogGetOn(frog.getLeft(), frog.getRight())) {
            // 올라탔다. 강의 속력과 방향대로 움직인다.
            Log.e("Game - isFrogGetIn", "Frog get on the Timber")
            frogFlowOnRiver = river
            frogMove(river)

        } else {
            // 못올라탐 - 죽음
            // 물에 빠지고 게임오버
            Log.e("Game - isFrogGetIn", "[DEAD] Frog is drown")
            gameOver(GAMESTATE.DROWN)
        }
        return null
    }

    fun frogMove(river: River?) {
        // 개구리 첫 시작과 뱀이 있는 땅 부분에서는 개구리가 움직이지 않는다.
        // 강에 있지 않기에 null 값이 들어온다.
        // null 이 아닐 경우! 개구리 움직인다.
        if (river != null) {
            // 개구리가 강의 통나무나 악어에 제대로 올라타서 움직인다.
            // 개구리를 강의 속도에 맞춰 움직인다.
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
            frog.setLeft(frog.getLeft() + river.getSpeed() * river.getDirection())
        }
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

    private fun gameOver(state: GAMESTATE) {
        // 게임 오버
        // 재시작위한 준비
        // 점수를 얻고 게임오버 됐을 수도, 개구리가 죽어 게임오버 됐을 수도 있다. 매개변수로 받은 GameOver값을 통해 확인
        // GameOver 값을 보고 어떤 이유로 죽었는지 Controller에게 알려줌. (SUCCESS면 점수 취득 - step 깎지 않는다.)
        if (state != GAMESTATE.SCORE) step -= 1
        this.state = state

        // 만약 step 이 0이면 정말 게임 끝!
        // finish 호출해 게임을 완전히 끝낸다.
        if (step == 0) {
            finish()
        }
        land.clear()
    }

    private fun finish() {
        // step 다 쓰고 정말 게임 종료
        // Controller에서 게임 종료 알린다.
        Log.e("gameOver", "[FINISH] - step is $step")
        this.state = GAMESTATE.FINISHED

        // 모든 객체 초기화
        land.clear()
        river1.clear()
        river2.clear()
        river3.clear()
        river4.clear()
    }
}