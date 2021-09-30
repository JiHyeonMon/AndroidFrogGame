package com.example.example.froggame.controller

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.example.froggame.R
import com.example.example.froggame.model.Frog
import com.example.example.froggame.model.Game


class MainActivity : AppCompatActivity() {

    var height = 0

    lateinit var game: Game
    lateinit var frogModel: Frog

    lateinit var score: TextView
    lateinit var lives: TextView
    lateinit var btnJump: Button

    lateinit var layout1: ConstraintLayout
    lateinit var layout2: LinearLayout
    lateinit var layout3: LinearLayout
    lateinit var layout4: LinearLayout
    lateinit var layout5: LinearLayout
    lateinit var layout6: LinearLayout
    lateinit var layout7: LinearLayout
    lateinit var layout: ConstraintLayout

    lateinit var timber1InLayout2: ImageView
    lateinit var crocodileInLayout2: ImageView
    lateinit var timber1InLayout3: ImageView
    lateinit var crocodileInLayout3: ImageView
    lateinit var timber1InLayout5: ImageView
    lateinit var crocodileInLayout5: ImageView
    lateinit var timber1InLayout6: ImageView
    lateinit var crocodileInLayout6: ImageView

    lateinit var frogImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initial()

        game = Game()
        game.gameStart()
        setFrogUI()
        setSnakeUI()
        setCrocodile()

//        Action
//        Check
//        Update

        //run loop
        checkUpdate()

        btnJump.setOnClickListener {
            game.frogJump(height)
        }

    }

    private fun setFrogUI() {
        frogImage.x = game.frog.getLeft()
        frogImage.y = game.frog.getY()
    }

    private fun setSnakeUI() {
        val land = findViewById<View>(R.id.layout4) as LinearLayout

        val snakeNumber = game.land.getNum()
        var snakes = game.land.getSnakes()

        Toast.makeText(this@MainActivity, snakeNumber.toString(), Toast.LENGTH_SHORT).show()

        for (i in 0 until snakeNumber) {
            val snake = ImageView(this)
            snake.id = View.generateViewId()
            snake.setBackgroundResource(R.drawable.snake)
            snake.layoutParams = ViewGroup.LayoutParams(160,160)

            // TODO 뱀 길이 알아내야 함

            snake.x = snakes[i].getLeft()

            land.addView(snake)
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
        crocodileInLayout2 = findViewById(R.id.crocodileInLayout2)
        timber1InLayout3 = findViewById(R.id.timber1InLayout3)
        crocodileInLayout3 = findViewById(R.id.crocodileInLayout3)
        timber1InLayout5 = findViewById(R.id.timber1InLayout5)
        crocodileInLayout5 = findViewById(R.id.crocodileInLayout5)
        timber1InLayout6 = findViewById(R.id.timber1InLayout6)
        crocodileInLayout6 = findViewById(R.id.crocodileInLayout6)

        frogImage = findViewById(R.id.frogImage)

        score = findViewById(R.id.textScore)
        lives = findViewById(R.id.textLives)

        score.text = "0"
        lives.text = "4"
    }

    private fun checkUpdate(){
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {

                if (game.state == Game.GameState.FINISHED) {
                    // 게임 끝나고 뱀 다시 그려야 함
                    // 기존에 동적으로 addView로 뱀을 추가한 layout4 내부의 뱀을 지운다.
                    layout4.removeAllViews()
                    Toast.makeText(this@MainActivity, "FROG DEAD", Toast.LENGTH_SHORT).show()

                    // 게임 재시작
                    game.gameStart()
                    setFrogUI() // 개구리 처음 위치로 옮기기
                    setSnakeUI() // 뱀 새로운 위치, 개수로 다시 그리기
                }

                //frog
                frogImage.y = game.frog.getY()
                frogImage.x = game.frog.getLeft()

                //timber
                timber1InLayout2.x = game.river1.timber1.getLeft()
                timber1InLayout3.x = game.river2.timber1.getLeft()
                timber1InLayout5.x = game.river3.timber1.getLeft()
                timber1InLayout6.x = game.river4.timber1.getLeft()

                //crocodile
                crocodileInLayout2.x = game.river1.crocodile.getLeft()
                crocodileInLayout3.x = game.river2.crocodile.getLeft()
                crocodileInLayout5.x = game.river3.crocodile.getLeft()
                crocodileInLayout6.x = game.river4.crocodile.getLeft()

                handler.postDelayed(this, 5)
            }
        })
    }
}