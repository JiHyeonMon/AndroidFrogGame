package com.example.example.froggame.controller

import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.example.example.froggame.R
import com.example.example.froggame.databinding.ActivityMainBinding
import com.example.example.froggame.model.Game
import com.example.example.froggame.model.GameConfig
import com.example.example.froggame.model.character.Timber
import com.example.example.froggame.model.landform.Destination
import com.example.example.froggame.model.landform.Land
import com.example.example.froggame.model.landform.River


class MainActivity : AppCompatActivity() {

    // 기기마다 화면 크기가 다르기 때문에 개구리의 초기 위치, 점프 높이가 다 다르다.
    // 앱이 시작되면 기기 화면의 크기를 구하고 Model에 넘겨 game character들 설정에 사용한다.
    private var screenWidth = 0
    private var screenHeight = 0

    // 실제 게임과 관련된 데이터를 가지고 있는 모델 선언
    lateinit var gameModel: Game

    // Model을 지속적으로 관찰하고 View를 업데이트 시키기 위한 runnable객체와 Handler
    lateinit var handler: Handler
    lateinit var runnable: Runnable

    // View 객체를 참조하기 위해 ViewBinding 사용
    // binding 객체 선언
    private lateinit var binding: ActivityMainBinding

    // View에 사용되는 객체 참조
    lateinit var frogImage: ImageView
    lateinit var layouts: ArrayList<ViewGroup>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Reference View to use ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 기기의 화면 크기를 windowManager를 통해 받아온다.
        // screenHeight와 screenWidth 값을 설정한다.
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenHeight = size.x
        screenWidth = size.y

        // 화면상에 게임 캐릭터들을 동적으로 추가하기 위해 캐릭터들이 들어갈 layout을 배열로 선언
        layouts = arrayListOf(
            binding.goalLayout,
            binding.riverLayout1,
            binding.riverLayout2,
            binding.landLayout,
            binding.riverLayout3,
            binding.riverLayout4
        )

        // 게임 생성
        gameModel = Game(screenHeight, screenWidth)
        // 게임 시작
        gameModel.gameStart()

        // Model에서 게임이 시작되고 게임 캐릭터들이 생성된다.
        // initialUI()에서 Model에 생성된 캐릭터 데이터를 확인하고 View에 추가한다.
        initialUI()

        // 사용자가 화면의 '점프' 버튼을 클릭시 개구리가 점프를 한다.
        // View에서 이벤트 발생시 Controller에서 Model에 알려 적절히 데이터 처리를 한다.
        binding.btnJump.setOnClickListener {
            // 개구리가 점프해야 한다.
            // Controller --> Model
            gameModel.frogJump()
        }

        // Model 값을 지속적으로 받아와 View에 업데이트 한다.
        checkUpdate()

    }

    private fun initialUI() {
        // 게임이 시작되면 Model에서 데이터를 받아와 화면상의 캐릭터들을 그려준다.
        setFrogUI()
        setGameCharacterUI()
    }

    private fun setFrogUI() {
        // 화면에 Frog 그리는 부분

        // Frog를 그릴 ViewGroup
        val gameLayout = findViewById<View>(R.id.layout) as ConstraintLayout

        // ImageView를 하나 만든다. 개구리 그림을 넣고 크기를 지정해준다.
        frogImage = ImageView(this)
        frogImage.setBackgroundResource(R.drawable.frog)
        frogImage.scaleType = ImageView.ScaleType.FIT_XY
        frogImage.layoutParams = ViewGroup.LayoutParams(GameConfig.FROG_WIDTH, GameConfig.FROG_HEIGHT)

        // ViewGroup에 만든 개구리 ImageView를 addView 시켜준다.
        gameLayout.addView(frogImage)
    }

    private fun setGameCharacterUI() {
        // 개구리를 제외한 게임 캐릭터들을 그리는 부분
        // Model에서 지형 리스트 데이터를 가져와 반복문을 돌며 점수판, 통나무, 악어, 뱀을 각각 알맞은 지형에 그려준다.

        for (i in 0 until gameModel.landForm.size) {
            // View에서의 layout 배열과 Model에서 landForm 배열을 미리 같은 순서로 세팅해서 같은 i 인덱스에 같은 지형이 나오도록 초기 설정 (Destination -> river -> river -> land -> river -> river)

            when (gameModel.landForm[i]){
                is Destination -> {
                    // 목적지 Layout - Goal Position 그리기

                    // Model에서 배열로 Goal 데이터를 받아온다.
                    val goals = gameModel.landForm[i].getGameCharacter()

                    // 받은 goals만큼 반복문을 돌며 화면에 그려준다.
                    // ImageView를 생성하여 점수판 그림을 넣고 Model에서 위치 값을 가져와서 해당 위치로 위치시킨다.
                    // Viewgroup에 addView함으로써 실제로 화면상에 보이게 된다.
                    for (goal in goals) {
                        val panel = ImageView(this)
                        panel.setBackgroundResource(R.drawable.circle)
                        panel.x = goal.left

                        layouts[i].addView(panel, 180, 180)
                    }
                }
                is Land -> {
                    // 육지 Layout - 뱀 그리기

                    // Model에서 배열로 뱀 데이터를 받아온다.
                    val snakes = gameModel.landForm[i].getGameCharacter()

                    // 받은 snakes만큼 반복문을 돌며 화면에 그려준다.
                    // ImageView를 생성하여 뱀 그림을 넣고 Model에서 위치 값을 가져와서 해당 위치로 위치시킨다.
                    // Viewgroup에 addView함으로써 실제로 화면상에 보이게 된다.
                    for (item in snakes) {
                        Log.e("snake", "${item.width}")
                        val snake = ImageView(this)
                        snake.setBackgroundResource(R.drawable.snake)
                        snake.x = item.left

                        layouts[i].addView(snake, 180, 180)
                    }
                }
                is River -> {
                    // 강 Layout - 악어와 통나무 그리기

                    // Model에서 배열로 악어/통나무 데이터를 받아온다.
                    val riverCharacter = gameModel.landForm[i].getGameCharacter()

                    // 악어인지 통나무인지 확인이 필요하다
                    // 반복문을 통해 받은 데이터를 다 넣어주는데 악어/통나무 서로 다른 이미지로 세팅
                    for (character in riverCharacter) {
                        if (character is Timber) {
                            // 통나무 일 경우,
                            // ImageView를 생성하여 통나무 그림을 넣고 Model에서 위치 값을 가져와서 해당 위치로 위치시킨다.
                            // Viewgroup에 addView함으로써 실제로 화면상에 보이게 된다.
                            val timber = ImageView(this)
                            timber.setBackgroundResource(R.drawable.timber)
                            timber.scaleType = ImageView.ScaleType.CENTER_CROP
                            timber.x = character.left

                            layouts[i].addView(timber, 400, 180)
                        } else {
                            // 악어일 경우,
                            // ImageView를 생성하여 악어 그림을 넣고 Model에서 위치 값을 가져와서 해당 위치로 위치시킨다.
                            // Viewgroup에 addView함으로써 실제로 화면상에 보이게 된다.
                            val crocodile = ImageView(this)
                            crocodile.setBackgroundResource(R.drawable.crokerdail)
                            crocodile.scaleType = ImageView.ScaleType.CENTER_CROP
                            crocodile.x = character.left

                            if (character.direction < 0) {
                                // 강이 흐르는 방향에 따라 악어 흐르는 방향도 달라진다.
                                // Model에서 악어의 방향을 확인하고 화면상의 악어의 방향도 바꿔준다.
                                crocodile.rotationY = 180f
                            }

                            layouts[i].addView(crocodile, 400, 180)
                        }
                    }
                }
            }
        }
    }

    private fun checkUpdate() {
        // 20ms 간격으로 model값을 가져와서 View를 업데이트 시킨다.
        // 지속적 업데이트 및 데이터 관찰이 필요 - runnable 객체 생성, handler통해 20ms로 지속적 업데이트
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                // Action
                // 게임을 계속 진행시킨다. (강에서 움직이는 캐릭터들 move시킨다.)
                gameModel.progress()

                // Refresh
                // Model에서 데이터가 바뀌면 값을 View에도 업데이트 시킨다.
                refreshUI()

                // Check
                // 게임오버인지 gameModel.state로 확인
                if (gameModel.state != Game.GAMESTATE.IN_PROGRESS) {
                    gameOver()
                }

                handler.postDelayed(this, 20)
            }
        }
        handler.post(runnable)
    }

    private fun refreshUI() {
        // Model --> Controller --> View
        // << Model 값 변화를 Controller에 가져와서 View 에 반영 >>
        // 개구리가 점프를 한 경우, 개구리의 Y 값이 변했을 텐데 해당 변경된 값을 가져와 View를 업데이트.
        // 개구리가 통나무나 악어에 올라탄 경우, 움직이며 좌표가 변한다. 해당 좌표를 추적하며 View에서의 개구리를 업데이트 시킨다.
        frogImage.y = gameModel.frog.y
        frogImage.x = gameModel.frog.left

        // 강에 위치한 통나무와 악어들도 계속 움직인다.
        // 움직임을 지속적으로 관찰해 화면에 반영한다.
        for (i in 0 until gameModel.landForm.size) {
            // 강만 지속적으로 흐르기 때문에 Model의 landFrom에서 강만 추적하여 확인한다.
            if (gameModel.landForm[i] is River) {
                // 강에 위치한 통나무, 악어의 좌표를 가져와 View에 업데이트 한다.
                layouts[i][0].x = gameModel.landForm[i].getGameCharacter()[0].left
                layouts[i][1].x = gameModel.landForm[i].getGameCharacter()[1].left
                layouts[i][2].x = gameModel.landForm[i].getGameCharacter()[2].left
                layouts[i][3].x = gameModel.landForm[i].getGameCharacter()[3].left

            }
        }

        // Model의 점수, step 값이 변경되면 View 에서 반영한다.
        binding.textScore.text = gameModel.score.toString()
        binding.textLives.text = gameModel.step.toString()

    }

    private fun gameOver() {
        // Model에서 개구리 상태 변했을 때 알린다.
        when (gameModel.state) {
            Game.GAMESTATE.SCORE -> {
                Toast.makeText(this@MainActivity, "[Success] 점수 획득! ", Toast.LENGTH_SHORT).show()
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
            else -> Toast.makeText(this@MainActivity, "[ERROR] 알 수 없는 개구리의 죽음 ", Toast.LENGTH_SHORT)
                .show()
        }

        // state가 FINISHED가 아니면 재시작을 한다.

        // 게임 재시작을 위한 로직.
        // 게임 새로 시작하면 새로 Model에서 값을 받아 다시 그린다. ViewGroup에서 addView 된 View들을 지워준다.
        binding.layout.removeView(frogImage)
        for (layout in layouts) {
            layout.removeAllViews()
        }

        // 게임 재시작
        gameModel.gameStart()
        initialUI()
    }

    private fun gameFinish() {
        // 완전히 게임 끝!
        // 게임 끝났다는 토스트 메시지
        Toast.makeText(this@MainActivity, "게임 끝 - 지금까지 개구리 게임이었습니다. :) ", Toast.LENGTH_LONG).show()

        // 움직이던 핸들러 runnable 제거
        handler.removeCallbacks(runnable)
    }
}