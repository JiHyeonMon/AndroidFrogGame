package com.example.example.froggame.model

import android.util.Log
import com.example.example.froggame.model.character.Frog
import com.example.example.froggame.model.landform.GoalPosition
import com.example.example.froggame.model.landform.Land
import com.example.example.froggame.model.landform.LandForm
import com.example.example.froggame.model.landform.River

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

    val landForm = arrayListOf(GoalPosition(), River(), River(true), Land(), River(), River(true))

    fun gameStart() {
        // 게임 시작
        // 게임 시작 상태인 GAMESTATE.IN_PROGRESS 설정
        state = GAMESTATE.IN_PROGRESS
        jumpCnt = 6

        // 각각의 게임 객체 (개구리, 강(통나무/악어), 땅(뱀/점수판)) 초기화 및 생성
        frog.setFrog()

        for (landform in landForm) {
            landform.setLandForm()
        }
    }

    fun progress() {
        // Action
        // Controller --> Model
        // 강이 흐르게 함.

        for (landform in landForm) {
            if (landform is River) {
                for (character in landform.getGameCharacter()) {
                    character.move()
                }
            }
        }

        frog.move()

    }

    fun frogJump(h: Int) {
        // 개구리 점프가 발생했을 때,
        // 개구리의 Y 좌표 값 h만큼 변경
        frog.jump(h)

        jumpCnt -= 1
        // 점프 카운트 1, 각 단계에 맞는 로직 처리
        //    val landForm = arrayListOf(GoalPosition(), River(), River(true), Land(), River(), River(true))
        when (jumpCnt) {
            5 -> {
                // 강 - 개구리가 올라탔는지 확인
                frog.speed = 0
                isFrogGetOn(landForm[5] as River)
            }
            4 -> {
                // 강 - 개구리가 올라탔는지 확인
                frog.speed = 0
                isFrogGetOn(landForm[4] as River)
            }
            3 -> {
                // 땅 - 뱀과 만났는지 확인
                frog.speed = 0
                checkFrogMeetSnake(landForm[3] as Land)
            }
            2 -> {
                // 강 - 개구리가 올라탔는지 확인
                frog.speed = 0
                isFrogGetOn(landForm[2] as River)
            }
            1 -> {
                // 강 - 개구리가 올라탔는지 확인
                frog.speed = 0
                isFrogGetOn(landForm[1] as River)
            }
            0 -> {
                // 점수판 - 점수를 딸 수 있는지 여부 판단
                frog.speed = 0
                checkScore(landForm[0] as GoalPosition)
            }
        }
    }

    private fun checkFrogMeetSnake(land: Land) {
        // 개구리가 뱀이랑 만났는지 확인한다.
        // 땅에 있는 뱀들의 좌표를 알기위해 land.getSnakes()메소드 호출을 통해 snake객체들의 배열을 받는다.
//        val snakes = land.getGameCharacter()

        // 반복문을 통해 하나하나 뱀들과 개구리와 위치를 확인한다.
        for (snake in land.getGameCharacter()) {

            if (snake.isFrogGetOn(frog.left, frog.left + frog.width)) {
                Log.e("Game - isFrogMeetSnake", "[DEAD] Frog is eaten by Snake")
                gameOver(GAMESTATE.SNAKE)
                break
            }
        }
    }

    private fun isFrogGetOn(river: River) {
        // 개구리가 통나무나 악어에 올라탔는지 판단한다.
        // 어떤 강인지 River를 받는다.
        // 강마다 방향과 속력이 다르기 때문 + 해당 강의 통나무와 악어의 좌표도 필요

        var isGetOn = false

        for (character in river.getGameCharacter()) {
            Log.e(
                "is Frog",
                "${frog.left} ${frog.left+frog.width} ${character.left} ${character.left + character.width}"
            )
            if (character.isFrogGetOn(frog.left, frog.left + frog.width)) {
                Log.e("geton", "getOn $character ${river.getSpeed()} ${river.getDirection()}")
                // 올라탐
                frog.speed = river.getSpeed()
                frog.direction = river.getDirection()
                frog.move()
                isGetOn = true
                break
            }
        }

        if (!isGetOn) {
            Log.e("notgetOn", "getOn no")
            frog.speed = 0
            frog.direction = river.getDirection()
            gameOver(GAMESTATE.DROWN)
        }
    }

    private fun checkScore(goalPosition: GoalPosition) {
        // 개구리가 마지막 단계까지 가서 점프를 했을 때 점수를 얻었는지 확인
        // 점수판이 있는 좌표를 알기위해 land.getBoard() 메서드를 호출해 ScoreBoard 객체들을 배열로 받는다.

        var isScore = false

        for (goal in goalPosition.getGameCharacter()) {
            Log.e("check Score", "${frog.left} ${frog.left+frog.width} ${goal.left} ${goal.left+goal.width}")
            if (goal.isFrogGetOn(frog.left, frog.left + frog.width)) {
                // 점수 획득
                // score + 1 시키고, SUCCESS로 게임 오버
                Log.e("Game - isScore", "[SCORE] SUCCESS")
                score += 1
                isScore = true
                gameOver(GAMESTATE.SCORE)
                break
            }
        }

        if (!isScore) {

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

        // 모든 객체 초기화
        for (landform in landForm) {
            landform.clearLandForm()
        }
    }

    private fun finish() {
        // step 다 쓰고 정말 게임 종료
        // Controller에서 게임 종료 알린다.
        Log.e("gameOver", "[FINISH] - step is $step")
        this.state = GAMESTATE.FINISHED

        // 모든 객체 초기화
        for (landform in landForm) {
            landform.clearLandForm()
        }
    }
}