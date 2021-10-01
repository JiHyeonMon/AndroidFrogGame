package com.example.example.froggame.controller

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.example.froggame.R
import com.example.example.froggame.model.Game

class MainActivity : AppCompatActivity() {

    private var height = 0
    var jumpCnt = 6

    lateinit var gameModel: Game

    lateinit var score: TextView
    lateinit var lives: TextView
    lateinit var btnJump: Button

    lateinit var layout1: ConstraintLayout
    lateinit var layout2: ConstraintLayout
    lateinit var layout3: ConstraintLayout
    lateinit var layout4: ConstraintLayout
    lateinit var layout5: ConstraintLayout
    lateinit var layout6: ConstraintLayout
    lateinit var layout7: ConstraintLayout
    lateinit var layout: ConstraintLayout

    lateinit var timber1InLayout2: ImageView
    lateinit var timber2InLayout2: ImageView
    lateinit var crocodileInLayout2: ImageView
    lateinit var timber1InLayout3: ImageView
    lateinit var timber2InLayout3: ImageView
    lateinit var crocodileInLayout3: ImageView
    lateinit var timber1InLayout5: ImageView
    lateinit var timber2InLayout5: ImageView
    lateinit var crocodileInLayout5: ImageView
    lateinit var timber1InLayout6: ImageView
    lateinit var timber2InLayout6: ImageView
    lateinit var crocodileInLayout6: ImageView

    lateinit var frogImage: ImageView

    lateinit var handler: Handler
    lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View 초기화 - 앞서 lateinit으로 선언한 View들 init 해줌
        // 화면에 그려질 View 객체들 Id 참조
        initial()

        // GameModel 객체 생성
        // gameStart()를 통해 게임을 시작
        gameModel = Game()
        gameModel.gameStart()

        // Model에서 가져온 데이터들 화면에 보여주기 위한 코드
        // Model --> Controller --> View
        setFrogUI()
        setSnakeUI()
        setScoreBoardUI()
        setCrocodile()

        // run loop
        // Handler를 통해 Controller에서 지속적으로 Action발생시 Model의 데이터 변화시키고, 변화를 관찰하고 값이 변경되면 View도 변경시킨다.
        // View --> Controller --> Model
        // Model --> Controller --> View
        checkUpdate()


        // 사용자가 화면에서 jump 버튼을 누르면 Controller가 Model에 점프 알리고 Model은 값을 변경시킨다.
        // View --> Controller --> Model
        btnJump.setOnClickListener {
            // 개구리가 움직임
            // Controller --> Model
            gameModel.frogJump(height)

            jumpCnt -= 1
            // 점프 카운트 1, 각 단계에 맞는 로직 처리
            when (jumpCnt) {
                5 -> {
                    // 강 - 개구리가 올라탔는지 확인
                    gameModel.isFrogGetOn(gameModel.river4)
                }
                4 -> {
                    // 강 - 개구리가 올라탔는지 확인
                    gameModel.isFrogGetOn(gameModel.river3)
                }
                3 -> {
                    // 땅 - 뱀과 만났는지 확인
                    gameModel.frogFlowOnRiver = null
                    gameModel.checkFrogMeetSnake()
                }
                2 -> {
                    // 강 - 개구리가 올라탔는지 확인
                    gameModel.isFrogGetOn(gameModel.river2)
                }
                1 -> {
                    // 강 - 개구리가 올라탔는지 확인
                    gameModel.isFrogGetOn(gameModel.river1)
                }
                0 -> {
                    // 점수판 - 점수를 딸 수 있는지 여부 판단
                    gameModel.checkScore()
                }
            }
        }
    }

    private fun setFrogUI() {
        // 화면에 Frog 그리는 부분
        // Frog를 그릴 ViewGroup
        val gameLayout = findViewById<View>(R.id.layout) as ConstraintLayout

        // ImageView를 하나 만든다. 개구리 그림을 넣고 크기를 지정해준다.
        frogImage = ImageView(this)
        frogImage.setBackgroundResource(R.drawable.frog)
        frogImage.scaleType = ImageView.ScaleType.FIT_XY
        frogImage.layoutParams = ViewGroup.LayoutParams(120, 120)

        // ViewGroup에 만든 개구리 ImageView를 addView 시켜준다.
        gameLayout.addView(frogImage)

        // 이제 화면에 개구리가 그려진다.
        // Model에서 개구리 위치를 받아와서 해당 위치로 개구리를 위치시킨다.
        // Model --> Controller --> View
        frogImage.x = gameModel.frog.getLeft()
        frogImage.y = gameModel.frog.getY()
    }

    private fun setSnakeUI() {
        // 화면에 뱀을 그리는 부분
        // 뱀을 넣어줄  ViewGroup
        val snakeLayout = findViewById<View>(R.id.layout4) as ConstraintLayout

        // 개구리는 한마리 였지만 뱀은 다수이기 때문에 Model에서 getSnake() 메서드를 통해 배열로 Snake를 넘긴다.
        // Snake 들의 정보가 담긴 배열을 snakes에 넣는다.
        val snakes = gameModel.land.getSnakes()

        // 받은 snakes만큼 반복문을 돌며 화면에 그려준다.
        // ImageView를 생성하여 뱀 그림을 넣고 Model에서 위치 값을 가져와서 해당 위치로 위치시킨다.
        // Viewgroup에 addView함으로써 실제로 화면상에 보이게 된다.
        for (item in snakes) {
            val snake = ImageView(this)
            snake.setBackgroundResource(R.drawable.snake)
            snake.x = item.getLeft()

            snakeLayout.addView(snake, 180, 180)
        }
    }

    private fun setScoreBoardUI() {
        // 화면에 점수판을 그리는 부분
        // 점수판을 넣어줄 ViewGroup
        val scoreBoardLayout = findViewById<View>(R.id.layout1) as ConstraintLayout

        // Model에서 ScoreBoard 배열값을 가져와 boards에 넣는다.
        val boards = gameModel.land.getBoards()

        // 받은 boards만큼 반복문을 돌며 화면에 그려준다.
        // ImageView를 생성하여 점수판 그림을 넣고 Model에서 위치 값을 가져와서 해당 위치로 위치시킨다.
        // Viewgroup에 addView함으로써 실제로 화면상에 보이게 된다.
        for (element in boards) {
            val panel = ImageView(this)
            panel.setBackgroundResource(R.drawable.circle)
            panel.x = element.getLeft()

            scoreBoardLayout.addView(panel, 180, 180)
        }
    }

    private fun setCrocodile() {
        // 강의 방향성에 따라 악어의 방향도 달라진다.
        // 구한 이미지가 머리가 오른쪽을 향한 이미지이므로 강의 방향이 반대인 부분에선 crocodile의 ImageView의 방향을 바꿔준다.
        // Model --> Controller --> View
        if (gameModel.river2.crocodile.reverse) {
            crocodileInLayout3.rotationY = 180f
        }
        if (gameModel.river4.crocodile.reverse) {
            crocodileInLayout6.rotationY = 180f
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // 개구리가 점프를 할 높이를 layout의 높이만큼으로 설정
        // 화면에 focus 얻을 때 높이를 구하여 높이를 저장
        height = layout2.measuredHeight
    }

    private fun initial() {
        // 화면을 그리는데 사용되는 View 및 ViewGroup들
        layout1 = findViewById(R.id.layout1)
        layout2 = findViewById(R.id.layout2)
        layout3 = findViewById(R.id.layout3)
        layout4 = findViewById(R.id.layout4)
        layout5 = findViewById(R.id.layout5)
        layout6 = findViewById(R.id.layout6)
        layout7 = findViewById(R.id.layout7)
        layout = findViewById(R.id.layout)
        btnJump = findViewById(R.id.btnJump)

        timber1InLayout2 = findViewById(R.id.timber1InLayout2)
        timber2InLayout2 = findViewById(R.id.timber2InLayout2)
        crocodileInLayout2 = findViewById(R.id.crocodileInLayout2)
        timber1InLayout3 = findViewById(R.id.timber1InLayout3)
        timber2InLayout3 = findViewById(R.id.timber2InLayout3)
        crocodileInLayout3 = findViewById(R.id.crocodileInLayout3)
        timber1InLayout5 = findViewById(R.id.timber1InLayout5)
        timber2InLayout5 = findViewById(R.id.timber2InLayout5)
        crocodileInLayout5 = findViewById(R.id.crocodileInLayout5)
        timber1InLayout6 = findViewById(R.id.timber1InLayout6)
        timber2InLayout6 = findViewById(R.id.timber2InLayout6)
        crocodileInLayout6 = findViewById(R.id.crocodileInLayout6)

        score = findViewById(R.id.textScore)
        lives = findViewById(R.id.textLives)
    }

    private fun checkUpdate() {
        // 10ms 간격으로 model값을 가져와서 View를 업데이트 시킨다.
        // 지속적 업데이트 및 데이터 관찰이 필요 - runnable 객체 생성, handler통해 10ms로 지속적 업데이트
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {

                // Controller --> Model
                // 강이 흐르게 함.
                gameModel.river1.flow()
                gameModel.river2.flow()
                gameModel.river3.flow()
                gameModel.river4.flow()

                // 개구리가 강 위의 객체에 제대로 올라타서 움직이는가
                // frogFlowOnRiver값이 있다면 제대로 올라탄 강이 있는거
                if (gameModel.frogFlowOnRiver != null) {
                    gameModel.frogMove(gameModel.frogFlowOnRiver)
                }

                // Model --> Controller --> View
                // << Model 값 변화를 Controller에 가져와서 View 에 반영 >>
                // 개구리가 점프를 한 경우, 개구리의 Y 값이 변했을 텐데 해당 변경된 값을 가져와 View를 업데이트.
                // 개구리가 통나무나 악어에 올라탄 경우, 움직이며 좌표가 변한다. 해당 좌표를 추적하며 View에서의 개구리를 업데이트 시킨다.
                frogImage.y = gameModel.frog.getY()
                frogImage.x = gameModel.frog.getLeft()

                // 아래 통나무와 악어들도 계속 움직인다.
                // 움직임을 지속적으로 관찰해 화면에 반영한다.
                // timber
                timber1InLayout2.x = gameModel.river1.timber1.getLeft()
                timber2InLayout2.x = gameModel.river1.timber2.getLeft()
                timber1InLayout3.x = gameModel.river2.timber1.getLeft()
                timber2InLayout3.x = gameModel.river2.timber2.getLeft()
                timber1InLayout5.x = gameModel.river3.timber1.getLeft()
                timber2InLayout5.x = gameModel.river3.timber2.getLeft()
                timber1InLayout6.x = gameModel.river4.timber1.getLeft()
                timber2InLayout6.x = gameModel.river4.timber2.getLeft()

                // crocodile
                crocodileInLayout2.x = gameModel.river1.crocodile.getLeft()
                crocodileInLayout3.x = gameModel.river2.crocodile.getLeft()
                crocodileInLayout5.x = gameModel.river3.crocodile.getLeft()
                crocodileInLayout6.x = gameModel.river4.crocodile.getLeft()

                // 게임 오버 (점수획득/개구리 죽음)일 때 score, lives 값 변한다.
                // Model의 점수, step 값이 변경되면 View 에서 반영한다.
                score.text = gameModel.score.toString()
                lives.text = gameModel.step.toString()

                // 게임오버인지 game.state로 확인
                if (gameModel.state != Game.GAMESTATE.IN_PROGRESS) {
                    gameOver()
                }

                handler.postDelayed(this, 10)
            }
        }
        handler.post(runnable)
    }

    fun gameOver() {
        // Model에서 개구리 상태 변했을 때 알린다.
        when (gameModel.state) {
            Game.GAMESTATE.SCORE -> {
                Toast.makeText(this@MainActivity, "점수 획득! ", Toast.LENGTH_SHORT).show()
            }
            Game.GAMESTATE.SNAKE -> {
                Toast.makeText(this@MainActivity, "[DEAD] 개구리 뱀에게 먹힘 ", Toast.LENGTH_SHORT).show()
            }
            Game.GAMESTATE.CROCODILE -> {
                Toast.makeText(this@MainActivity, "[DEAD] 개구리 악어에게 먹힘", Toast.LENGTH_SHORT).show()
            }
            Game.GAMESTATE.WALL -> {
                Toast.makeText(this@MainActivity, "[DEAD] 개구리 벽에 박치기", Toast.LENGTH_SHORT).show()
            }
            Game.GAMESTATE.DROWN -> {
                Toast.makeText(this@MainActivity, "[DEAD] 개구리 물에 빠짐", Toast.LENGTH_SHORT).show()
            }
            Game.GAMESTATE.FINISHED -> gameFinish()
            else -> Toast.makeText(this@MainActivity, "[ERROR] 알 수 없는 개구리의 죽음 ", Toast.LENGTH_SHORT).show()
        }

        // state가 FINISHED가 아니면 재시작을 한다.
        // 게임 재시작을 위한 로직.
        // 개구리, 뱀, 점수판 다시 그려야 함.
        // 기존에 동적으로 addView로 개구리와 뱀과 점수판을 추가했다. 게임 새로 시작하면 새로 값을 받을 테니 ViewGroup에서 addView 된 View들을 지워준다.
        layout.removeView(frogImage)
        layout4.removeAllViews()
        layout1.removeAllViews()

        // 게임 재시작
        gameModel.gameStart()
        jumpCnt = 6
        setFrogUI() // 개구리 생성, 처음 위치로
        setSnakeUI() // 뱀 새로운 위치, 개수로 새로 데이터 받아와 그리기
        setScoreBoardUI() // 점수판 새로운 위치로 새로 데이터 받아와 그리기
    }

    fun gameFinish() {
        // 완전히 게임 끝!
        // 게임 끝났다는 토스트 메시지
        Toast.makeText(this@MainActivity, "게임 끝 - 지금까지 개구리 게임이었습니다. :) ", Toast.LENGTH_LONG)
            .show()

        // 움직이던 핸들러 runnable 제거
        handler.removeCallbacks(runnable)
    }

}