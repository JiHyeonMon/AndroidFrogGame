package com.example.example.froggame.controller

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.example.froggame.R
import com.example.example.froggame.model.Frog
import com.example.example.froggame.model.Game

class MainActivity : AppCompatActivity() {

    var height = 0

    lateinit var game: Game
    lateinit var frog: Frog

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initial()

        game = Game()
        while (lives.text.toString().toInt() > 0) {
            game.gameStart()
        }

//        Action
//        Check
//        Update
        btnJump.setOnClickListener {
            frog.jump(height)
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

        score = findViewById(R.id.textScore)
        lives = findViewById(R.id.textLives)

        score.text = "0"
        lives.text = "4"
    }
}