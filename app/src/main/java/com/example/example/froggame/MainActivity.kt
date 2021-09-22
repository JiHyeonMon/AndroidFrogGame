package com.example.example.froggame

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private var height = 0
    private var width = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnJump = findViewById<Button>(R.id.btnJump)
        val frog = findViewById<ImageView>(R.id.frog)
        val crokerdail = findViewById<ImageView>(R.id.crokerdail)

        setSnake()
        addCrokerdail()
        addTree()

        btnJump.setOnClickListener {
            frog.y = frog.y - height
            ObjectAnimator.ofFloat(frog, "translationX", 1080F).apply {
                duration = 4000
                start()
            }
            Toast.makeText(this, height.toString()+width.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        val const2 = findViewById<ConstraintLayout>(R.id.layout2)
        height = const2.measuredHeight
        width = const2.measuredWidth
    }

    private fun setSnake() {
        val layout = findViewById<LinearLayout>(R.id.layout4)

        for (i in 0..3) {
            layout.addView(LayoutInflater.from(this).inflate(R.layout.snake, null, false))
        }
//        var snake1 = LayoutInflater.from(this).inflate(R.layout.snake, null, false)
//        var snake2 = LayoutInflater.from(this).inflate(R.layout.snake, null, false)
//        var snake3 = LayoutInflater.from(this).inflate(R.layout.snake, null, false)
//
//        layout.addView(snake1, 0)
//        layout.addView(snake2, 1)
//        layout.addView(snake3, 2)
    }

    private fun addCrokerdail() {
        val layout = findViewById<LinearLayout>(R.id.layout6)
        val crokerdail = ImageView(this)
        crokerdail.setImageResource(R.drawable.crokerdail)
        layout.addView(crokerdail, 0)

        ObjectAnimator.ofFloat(crokerdail, "translationX", 1080F).apply {
            duration = 4000
            start()
        }

    }

    private fun addTree() {
        val layout = findViewById<LinearLayout>(R.id.layout6)
        val tree = ImageView(this)
        tree.setImageResource(R.drawable.tree)
        layout.addView(tree, 0)

        ObjectAnimator.ofFloat(tree, "translationX", 1080F).apply {
            duration = 4000
            start()
        }

    }
}