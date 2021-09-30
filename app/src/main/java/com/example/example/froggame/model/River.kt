package com.example.example.froggame.model

import android.os.Handler

class River {

    private var speed: Int = 0
    private var direction: Int = 1

    var timber1: Timber
    var timber2: Timber
    var crocodile: Crocodile

    lateinit var handler: Handler
    lateinit var r: Runnable

    constructor() {
        timber1 = Timber()
        timber2 = Timber()
        crocodile = Crocodile()
        timber1.setLeft(0f)
        crocodile.setLeft(timber1.getLeft() - crocodile.width - 100)
        timber2.setLeft(crocodile.getLeft() - timber2.width - 100)
    }

    constructor(reverse: Boolean) {
        timber1 = Timber()
        timber2 = Timber()
        crocodile = Crocodile(reverse)
        timber1.setLeft(1080f)
        crocodile.setLeft(timber1.getRight() + 100)
        timber2.setLeft(crocodile.getRight() + 100)
        this.direction = -1

    }

    fun setRiver() {
        this.speed = (2..7).random()
        flow()
    }

    private fun flow() {
        handler = Handler()
        r = object : Runnable {
            override fun run() {
                if (direction > 0) {
                    // timber move
                    if (timber1.getLeft() > 1080) {
                        timber1.setLeft(0F - timber1.width)
                    }
                    if (timber2.getLeft() > 1080) {
                        timber2.setLeft(0F - timber2.width)
                    }
                    if (crocodile.getLeft() > 1080) {
                        crocodile.setLeft(0F - crocodile.width)
                    }
                    timber1.setLeft(timber1.getLeft() + speed * direction)
                    timber2.setLeft(timber2.getLeft() + speed * direction)
                    crocodile.setLeft(crocodile.getLeft() + speed * direction)
//                crocodile
                    // crocodile move
                } else {
                    if (timber1.getRight() < 0) {
                        timber1.setLeft(1080f)
                    }
                    if (timber2.getRight() < 0) {
                        timber2.setLeft(1080f)
                    }
                    if (crocodile.getRight() < 0) {
                        crocodile.setLeft(1080f)
                    }
                    timber1.setLeft(timber1.getLeft() + speed * direction)
                    timber2.setLeft(timber2.getLeft() + speed * direction)
                    crocodile.setLeft(crocodile.getLeft() + speed * direction)
                }
                handler.postDelayed(this, 10)
            }

        }
        handler.post(r)
    }

    fun getSpeed(): Int {
        return this.speed
    }

    fun getDirection(): Int {
        return this.direction
    }

}