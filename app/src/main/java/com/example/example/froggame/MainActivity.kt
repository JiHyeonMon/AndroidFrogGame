package com.example.example.froggame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    private var height = 0
    private var width = 0

    lateinit var frog: ImageView

    lateinit var layout1: LinearLayout
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

        layout1 = findViewById(R.id.layout1)
        layout2 = findViewById(R.id.layout2)
        layout3 = findViewById(R.id.layout3)
        layout4 = findViewById(R.id.layout4)
        layout5 = findViewById(R.id.layout5)
        layout6 = findViewById(R.id.layout6)
        layout7 = findViewById(R.id.layout7)
        layout = findViewById(R.id.layout)

        val btnJump = findViewById<Button>(R.id.btnJump)
        frog = findViewById(R.id.frog)

//        setFrog()
        setSnake()
        startLayout2()
        startLayout3()
        startLayout5()
        startLayout6()

        btnJump.setOnClickListener {
            frog.y -= height

            Toast.makeText(this, "${frog.x}  ${frog.x+width}", Toast.LENGTH_SHORT).show()

        }
    }

    private fun setFrog() {
        //액티비티의 최 상위 윈도우에 윈도우로 뷰 추가하기
        val params = WindowManager.LayoutParams()

        params.x = width/2-75
        params.y = layout7.y.toInt()*6
        frog = Frog(this)
        frog.layoutParams = params

        layout.addView(frog, 200, 200)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        height = layout2.measuredHeight
        width = layout2.measuredWidth
    }

    private fun setSnake() {
        val snake1 = Snake(this)
        val snake2 = Snake(this)

        layout4.addView(snake1, 175, 175)
        layout4.addView(snake2, 175, 175)
    }

    private fun startLayout2() {
        //toRight
        val  layoutParams =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
        layoutParams.weight = 1f

        val tree1 = Tree(this)
        val tree2 = Tree(this)
        val crokerdail = Crokerdail(this, false)

        layout2.addView(tree1, layoutParams)
        layout2.addView(tree2, layoutParams)
        layout2.addView(crokerdail, layoutParams)
        tree1.move(5)
        tree2.move(8)
        crokerdail.move(6)
    }

    private fun startLayout3() {
        // toLeft
        val  layoutParams =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
        layoutParams.weight = 1f

        val tree1 = Tree(this)
        val tree2 = Tree(this)
        val crokerdail = Crokerdail(this, true)

        layout3.addView(tree1, layoutParams)
        layout3.addView(tree2, layoutParams)
        layout3.addView(crokerdail, layoutParams)
        tree1.move(-3)
        tree2.move(-5)
        crokerdail.move(-4)
    }

    private fun startLayout5() {
        //toRight
        val  layoutParams =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
        layoutParams.weight = 1f

        val tree1 = Tree(this)
        val tree2 = Tree(this)
        val crokerdail = Crokerdail(this, false)

        layout5.addView(tree1, layoutParams)
        layout5.addView(tree2, layoutParams)
        layout5.addView(crokerdail, layoutParams)
        tree1.move(3)
        tree2.move(5)
        crokerdail.move(4)
    }

    private fun startLayout6() {
        // toLeft
        val  layoutParams =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
        layoutParams.weight = 1f

        val tree1 = Tree(this)
        val tree2 = Tree(this)
        val crokerdail = Crokerdail(this, true)

        layout6.addView(tree1, layoutParams)
        layout6.addView(tree2, layoutParams)
        layout6.addView(crokerdail, layoutParams)
        tree1.move(-5)
        tree2.move(-8)
        crokerdail.move(-6)

    }

    interface check {
        fun check(xF: Int, wF:Int)
    }
}