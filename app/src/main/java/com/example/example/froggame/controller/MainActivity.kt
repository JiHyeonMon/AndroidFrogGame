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

    lateinit var game: Game

    lateinit var score: TextView
    lateinit var lives: TextView
    lateinit var btnJump: Button

    lateinit var layout1: ConstraintLayout
    lateinit var layout2: LinearLayout
    lateinit var layout3: LinearLayout
    lateinit var layout4: ConstraintLayout
    lateinit var layout5: LinearLayout
    lateinit var layout6: LinearLayout
    lateinit var layout7: LinearLayout
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        game = Game()

        initial()

        game.gameStart()
        setFrogUI()
        setSnakeUI()
        setScoreBoardUI()
        setCrocodile()

        //run loop
        checkUpdate()

        btnJump.setOnClickListener {
            game.frogJump(height)
        }

    }

    private fun setFrogUI() {
        val gameLayout = findViewById<View>(R.id.layout) as ConstraintLayout

        frogImage = ImageView(this)
        frogImage.setBackgroundResource(R.drawable.frog)
        frogImage.scaleType = ImageView.ScaleType.FIT_XY
        frogImage.layoutParams = ViewGroup.LayoutParams(150, 150)

        gameLayout.addView(frogImage)
        frogImage.x = game.frog.getLeft()
        frogImage.y = game.frog.getY()
    }

    private fun setSnakeUI() {

        val snakeLayout = findViewById<View>(R.id.layout4) as ConstraintLayout
        val snakes = game.land.getSnakes()

        for (item in snakes) {
            val snake = ImageView(this)
            snake.setBackgroundResource(R.drawable.snake)
            snake.x = item.getLeft()

            snakeLayout.addView(snake, 180, 180)
        }
    }

    private fun setScoreBoardUI() {

        val scoreBoardLayout = findViewById<View>(R.id.layout1) as ConstraintLayout
        val boards = game.land.getBoards()

        for (element in boards) {
            val panel = ImageView(this)
            panel.setBackgroundResource(R.drawable.circle)
            panel.x = element.getLeft()

            scoreBoardLayout.addView(panel, 180, 180)
        }
    }

    private fun setCrocodile() {
        if (game.river2.crocodile.reverse) {
            crocodileInLayout3.rotationY = 180f
        }
        if (game.river4.crocodile.reverse) {
            crocodileInLayout6.rotationY = 180f
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        height = layout2.measuredHeight
    }

    private fun initial() {
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

        score.text = "0"
        lives.text = "4"
    }

    private fun checkUpdate() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {

                if (game.state == Game.GameState.GAMEOVER) {
                    frogImage.visibility = View.INVISIBLE
                    gameRestart()
                }

                //frog
                frogImage.y = game.frog.getY()
                frogImage.x = game.frog.getLeft()

                //timber
                timber1InLayout2.x = game.river1.timber1.getLeft()
                timber2InLayout2.x = game.river1.timber2.getLeft()
                timber1InLayout3.x = game.river2.timber1.getLeft()
                timber2InLayout3.x = game.river2.timber2.getLeft()
                timber1InLayout5.x = game.river3.timber1.getLeft()
                timber2InLayout5.x = game.river3.timber2.getLeft()
                timber1InLayout6.x = game.river4.timber1.getLeft()
                timber2InLayout6.x = game.river4.timber2.getLeft()

                //crocodile
                crocodileInLayout2.x = game.river1.crocodile.getLeft()
                crocodileInLayout3.x = game.river2.crocodile.getLeft()
                crocodileInLayout5.x = game.river3.crocodile.getLeft()
                crocodileInLayout6.x = game.river4.crocodile.getLeft()

                handler.postDelayed(this, 5)
            }
        })
    }

    fun gameRestart() {
        //score
        score.text = game.score.toString()
        lives.text = game.step.toString()

        // 게임 끝나고 뱀 다시 그려야 함
        // 기존에 동적으로 addView로 뱀을 추가한 layout4 내부의 뱀을 지운다.
        layout4.removeAllViewsInLayout()
        layout1.removeAllViews()
        Toast.makeText(this@MainActivity, "GAME OVER", Toast.LENGTH_SHORT).show()

        // 게임 재시작
        game.gameStart()
        setFrogUI() // 개구리 처음 위치로 옮기기
        setSnakeUI() // 뱀 새로운 위치, 개수로 다시 그리기
        setScoreBoardUI()
    }
}