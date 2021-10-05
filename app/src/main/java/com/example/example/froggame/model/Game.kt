package com.example.example.froggame.model

import android.util.Log
import com.example.example.froggame.model.character.Crocodile
import com.example.example.froggame.model.character.Frog
import com.example.example.froggame.model.landform.Destination
import com.example.example.froggame.model.landform.Land
import com.example.example.froggame.model.landform.River

class Game(width: Int, height: Int) {
    // 게임상태를 나타내는 상수
    // 게임 중인지, 점수를 취득했는지, 개구리가 죽었는지 여부 판단이 필요 ( + 어떻게 죽었는지 )
    // 해당 여부를 GAMESTATE 상수로 판단
    enum class GAMESTATE { IN_PROGRESS, SCORE, SNAKE, CROCODILE, WALL, DROWN, FINISHED }

    // 기기 화면마다 width, height 차이가 날 경우, 개구리의 초기 위치 및 점프 높이에 영향이 미친다.
    // 게임 시작과 함께 매개변수로 화면의 가로, 세로 높이를 받아와 개구리의 점프 높이를 설정한다.
    private val screenWidth = width
    private val screenHeight = height
    private val jumpHeight = height / 10.toFloat()

    // 현재 게임의 상태를 나타낼 state 변수
    var state: GAMESTATE = GAMESTATE.IN_PROGRESS

    // 기본 게임 시작시 점수 0, game Step 6로 지정
    var score = 0
    var step = 6

    // 개구리가 점프할 수 있는 카운트 값 6으로 설정
    var jumpCnt = 6

    // < 게임 객체 생성 >
    // 게임에 필요한 개구리 객체 선언
    val frog = Frog()

    // 게임에 필요한 지형을 배열로 선언
    val landForm = arrayListOf(Destination(), River(), River(true), Land(), River(), River(true))


    /***********************************************************************************************
     * Public Method
     */
    fun gameStart() {
        Log.e("게임 시작", "start")
        // 게임 시작
        // 게임 시작 상태인 GAMESTATE.IN_PROGRESS 설정, 개구리 점프 카운트 6으로 설정
        state = GAMESTATE.IN_PROGRESS
        jumpCnt = 6

        // 각각의 게임 객체 (개구리, 강(통나무/악어), 육지(뱀), 목적지(목표지점)) 초기화 및 생성
        frog.setFrog(screenWidth, screenHeight)
        // 개구리 처음 속도는 0
        frog.speed = 0

        // 기존에 배열로 선언해둔 지형을 반복문을 통해 확인하며 해당 지형에 맞는 게임 캐릭터들 생성 및 초기화
        for (landform in landForm) {
            landform.setLandForm(screenWidth)
        }
    }

    fun progress() {
        // Action
        // Controller --> Model
        // 강이 흐르게 함.
        for (landform in landForm) {
            if (landform is River) {
                // 지형이 강인 곳에 위치한 캐릭터들 값 가져와 반복문을 통해 움직일 수 있게 한다.
                for (character in landform.getGameCharacter()) {
                    character.move()
                }
            }
        }

        // 개구리도 자신의 속도에 맞게 움직인다. (움직이지 않는 육지나 목적지에선 속도가 0)
        frog.move()

        // 개구리가 움직이다 벽에 닿일 경우, 게임 오버
        if (frog.left + frog.width > screenWidth || frog.left < 0) {
            Log.e("Game - progress", "[DEAD] Frog bump into Wall")
            gameOver(GAMESTATE.WALL)
        }

    }

    fun frogJump() {
        // 개구리 점프가 발생했을 때,
        // 개구리의 Y 좌표 값 jumpHeight만큼 변경
        frog.y -= jumpHeight

        jumpCnt -= 1
        // 점프 카운트 1, 각 단계에 맞는 로직 처리
        when (jumpCnt) {
            5 -> {
                // 강 - 개구리가 올라탔는지 확인
                isFrogGetOn(landForm[5] as River)
            }
            4 -> {
                // 강 - 개구리가 올라탔는지 확인
                isFrogGetOn(landForm[4] as River)
            }
            3 -> {
                // 땅 - 뱀과 만났는지 확인
                frog.speed = 0
                checkFrogMeetSnake(landForm[3] as Land)
            }
            2 -> {
                // 강 - 개구리가 올라탔는지 확인
                isFrogGetOn(landForm[2] as River)
            }
            1 -> {
                // 강 - 개구리가 올라탔는지 확인
                isFrogGetOn(landForm[1] as River)
            }
            0 -> {
                // 점수판 - 점수를 딸 수 있는지 여부 판단
                frog.speed = 0
                checkScore(landForm[0] as Destination)
            }
        }
    }

    /***********************************************************************************************
     * Private Method
     */
    private fun checkFrogMeetSnake(land: Land) {
        // 개구리가 뱀이랑 만났는지 확인한다.
        // 땅에 있는 뱀들의 좌표를 알기위해 land.getGameCharacter()메소드 호출을 통해 snake객체들의 배열을 받는다.

        for (snake in land.getGameCharacter()) {
            // 반복문을 통해 하나하나 뱀들과 개구리와 위치를 확인한다.
            if (snake.isFrogGetOn(frog.left, frog.left + frog.width)) {
                // 뱀에 닿이면 뱀에게 먹혀 gameOver
                Log.e("Game - isFrogMeetSnake", "[DEAD] Frog is eaten by Snake")
                gameOver(GAMESTATE.SNAKE)
                break
            }
        }
    }

    private fun isFrogGetOn(river: River) {
        // 개구리가 통나무나 악어에 올라탔는지 판단한다.
        // 어떤 강인지 River를 받는다. 강마다 방향과 속력이 다르기 때문 + 해당 강의 통나무와 악어의 좌표도 필요

        for (character in river.getGameCharacter()) {
            // 해당 강에 있는 캐릭터들을 반복문을 통해 모두 확인한다.

            if (character.isFrogGetOn(frog.left, frog.left + frog.width)) {
                if (character is Crocodile) {
                    // 악어에 올라탔을 경우, 악어의 머리에 위치했냐에 따라 계속 진행 될 수도, 개구리가 죽고 게임 새로 시작될 수도 있다.
                    if (character.isCrocodileHead(frog.left, frog.left + frog.width)) {
                        // 만약 개구리가 악어의 머리에 탔을 경우, 개구리는 악어에 먹혀 죽는다.
                        Log.e("Game - isFrogGetOn", "[DEAD] Frog is eaten by Crocodile")
                        gameOver(GAMESTATE.CROCODILE)
                        return
                    }
                }

                // 통나무나 악어에 제대로 올라탔다.
                // 개구리의 속력과 방향을 강의 속력과 방향과 맞추고 함께 움직인다.
                frog.speed = river.getSpeed()
                frog.direction = river.getDirection()
                frog.move()

                // 하나라도 올라탔을 경우 return을 통해 해당 함수 종료시킨다.
                return
            }
        }

        // 반복문을 돌며 해당 강의 캐릭터들 다 확인했지만, 못올라탔다면 개구리는 물에 빠져 죽는다.
        frog.speed = 0
        gameOver(GAMESTATE.DROWN)

    }

    private fun checkScore(destination: Destination) {
        // 개구리가 마지막 단계까지 가서 점프를 했을 때 점수를 얻었는지 확인
        // 점수판이 있는 좌표를 알기위해 destination.getGameCharacter() 메서드를 호출해 목적지에 위치한 Goal 객체들을 배열로 받는다.

        for (goal in destination.getGameCharacter()) {
            if (goal.isFrogGetOn(frog.left, frog.left + frog.width)) {
                // 완전히 올라가 점수 획득
                // score + 1 시키고, SUCCESS로 게임 오버
                Log.e("Game - isScore", "[SCORE] SUCCESS")
                score += 1
                gameOver(GAMESTATE.SCORE)
                return
            }
        }

        // 점수판에 못 올랐을 경우 - 게임 오버
        Log.e("Game - isScore", "[DEAD] Frog is drown")
        gameOver(GAMESTATE.DROWN)

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

        // Goal이 있는 Destination, 뱀이 있는 Land는 완전 clear를 통해 새로 위치를 할당받아 위치시킬 수 있게 clearLandForm을 호출해준다.
        landForm[0].clearLandForm()
        landForm[3].clearLandForm()
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