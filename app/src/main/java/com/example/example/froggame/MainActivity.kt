package com.example.example.froggame

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var jumpCnt = 6

    private var height = 0
    private var width = 0

    lateinit var frog1: Frog

    lateinit var score: TextView
    lateinit var lives: TextView

    lateinit var layout1: ConstraintLayout
    lateinit var layout2: LinearLayout
    lateinit var layout3: LinearLayout
    lateinit var layout4: LinearLayout
    lateinit var layout5: LinearLayout
    lateinit var layout6: LinearLayout
    lateinit var layout7: LinearLayout
    lateinit var layout: ConstraintLayout

    lateinit var snake1: Snake
    lateinit var snake2: Snake

    lateinit var tree1InLayout6: Tree
    lateinit var tree2InLayout6: Tree
    lateinit var crokerdailInLayout6: Crokerdail

    lateinit var tree1InLayout5: Tree
    lateinit var tree2InLayout5: Tree
    lateinit var crokerdailInLayout5: Crokerdail

    lateinit var tree1InLayout3: Tree
    lateinit var tree2InLayout3: Tree
    lateinit var crokerdailInLayout3: Crokerdail

    lateinit var tree1InLayout2: Tree
    lateinit var tree2InLayout2: Tree
    lateinit var crokerdailInLayout2: Crokerdail


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

        score = findViewById(R.id.textScore)
        lives = findViewById(R.id.textLives)

        score.text = "0"
        lives.text = "4"

        val btnJump = findViewById<Button>(R.id.btnJump)

        gameStart()

        btnJump.setOnClickListener {
            frog1.y -= height

            jumpCnt -= 1

            when (jumpCnt) {
                5 -> {
                    checkFrog(tree1InLayout6, tree2InLayout6, crokerdailInLayout6)
                }
                4 -> {
                    frog1.handler.removeCallbacks(frog1.r)
                    checkFrog(tree1InLayout5, tree2InLayout5, crokerdailInLayout5)
                }
                3 -> {
                    frog1.handler.removeCallbacks(frog1.r)
                    snake1.checkFrog(frog1.x, frog1.x + frog1.width, callback)
                    snake2.checkFrog(frog1.x, frog1.x + frog1.width, callback)
                }
                2 -> {
                    frog1.handler.removeCallbacks(frog1.r)
                    checkFrog(tree1InLayout3, tree2InLayout3, crokerdailInLayout3)
                }
                1 -> {
                    frog1.handler.removeCallbacks(frog1.r)
                    checkFrog(tree1InLayout2, tree2InLayout2, crokerdailInLayout2)
                }
                0 -> {
                    frog1.handler.removeCallbacks(frog1.r)
                    checkScore()
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        height = layout2.measuredHeight
        width = layout2.measuredWidth
    }

    fun gameStart() {
        setFrog()
        setSnake()
        startLayout2()
        startLayout3()
        startLayout5()
        startLayout6()
    }

    fun restart() {
        jumpCnt = 6
        setFrog()

        snake1.setSnake()
        snake2.setSnake()
    }

    private fun setFrog() {
        frog1 = Frog(this)
        frog1.setCallback(callback)

        frog1.layoutParams = ViewGroup.LayoutParams(120, 120)

        layout.addView(frog1)
        frog1.y = 1055f
        frog1.x = 450f
    }


    private fun setSnake() {
        snake1 = Snake(this)
        snake2 = Snake(this)

        layout4.addView(snake1, 175, 175)
        layout4.addView(snake2, 175, 175)
    }

    private fun startLayout2() {
        //toRight
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.weight = 1f

        tree1InLayout2 = Tree(this)
        tree2InLayout2 = Tree(this)
        crokerdailInLayout2 = Crokerdail(this, false)

        layout2.addView(tree1InLayout2, layoutParams)
        layout2.addView(tree2InLayout2, layoutParams)
        layout2.addView(crokerdailInLayout2, layoutParams)
        tree1InLayout2.move((3..6).random())
        tree2InLayout2.move((3..6).random())
        crokerdailInLayout2.move((3..6).random())
    }

    private fun startLayout3() {
        // toLeft
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.weight = 1f

        tree1InLayout3 = Tree(this)
        tree2InLayout3 = Tree(this)
        crokerdailInLayout3 = Crokerdail(this, true)

        layout3.addView(tree1InLayout3, layoutParams)
        layout3.addView(tree2InLayout3, layoutParams)
        layout3.addView(crokerdailInLayout3, layoutParams)
        tree1InLayout3.move((-6..-3).random())
        tree2InLayout3.move((-6..-3).random())
        crokerdailInLayout3.move((-6..-3).random())
    }

    private fun startLayout5() {
        //toRight
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.weight = 1f

        tree1InLayout5 = Tree(this)
        tree2InLayout5 = Tree(this)
        crokerdailInLayout5 = Crokerdail(this, false)

        layout5.addView(tree1InLayout5, layoutParams)
        layout5.addView(tree2InLayout5, layoutParams)
        layout5.addView(crokerdailInLayout5, layoutParams)
        tree1InLayout5.move((3..6).random())
        tree2InLayout5.move((3..6).random())
        crokerdailInLayout5.move((3..6).random())
    }

    private fun startLayout6() {
        // toLeft
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.weight = 1f

        tree1InLayout6 = Tree(this)
        tree2InLayout6 = Tree(this)
        crokerdailInLayout6 = Crokerdail(this, true)

        layout6.addView(tree1InLayout6, layoutParams)
        layout6.addView(tree2InLayout6, layoutParams)
        layout6.addView(crokerdailInLayout6, layoutParams)
        tree1InLayout6.move((-6..-3).random())
        tree2InLayout6.move((-6..-3).random())
        crokerdailInLayout6.move((-6..-3).random())

    }

    private fun checkFrog(tree1: Tree, tree2: Tree, crokerdail: Crokerdail) {
        // 캐릭터들 속도 랜덤 - 캐릭터들 겹쳐있을 경우 올라타는 순서가 조건문 순서
        // 캐릭터들 생성 속도가 tree1 > tree2 > crokerdail 순서라 역순으로 조건문 형성 - 겹칠 경우 제일 위로 오는 순서
        if (frog1.x >= crokerdail.x &&
            frog1.x + frog1.width <= crokerdail.x + crokerdail.width
        ) {
            if (crokerdail.isHead(frog1.x, frog1.x+frog1.width)) {
                Toast.makeText(this, "[4] Crokerdail Eat Frog - The End", Toast.LENGTH_SHORT).show()
                callback.dead()
            } else {
                frog1.move(crokerdail.getSpeed())
            }
        }else if (frog1.x >= tree2.x &&
            frog1.x + frog1.width <= tree2.x + tree2.width
        ) {
            frog1.move(tree1.getSpeed())
        } else if (frog1.x >= tree1.x &&
            frog1.x + frog1.width <= tree1.x + tree1.width
        ) {
            frog1.move(tree2.getSpeed())
        }  else {
            Toast.makeText(this, "[3] Frog Drown - The End", Toast.LENGTH_SHORT).show()
            callback.dead()
        }
    }

    private fun checkScore() {
        Log.e("checkScore", "${frog1.x} ${frog1.x + frog1.width} ${panel2.left} ${panel2.right}")
        if ((frog1.x > panel1.left && frog1.x + frog1.width < panel1.right) || (frog1.x > panel2.left && frog1.x + frog1.width < panel2.right) || (frog1.x > panel3.left && frog1.x + frog1.width < panel3.right)) {
            // in - 점수 획득
            score.text = (score.text.toString().toInt() + 1).toString()
            restart()
        } else {
            lives.text = (lives.text.toString().toInt() - 1).toString()
            restart()
        }
    }

    private val callback = object : Callback {
        override fun callback() {
            Toast.makeText(this@MainActivity, "[2] Meet Snake - The End", Toast.LENGTH_SHORT).show()
            lives.text = (lives.text.toString().toInt() - 1).toString()
            dead()
        }

        override fun dead() {
//            layout.removeViewInLayout(frog1)

            lives.text = (lives.text.toString().toInt() - 1).toString()
            restart()
        }
    }
}