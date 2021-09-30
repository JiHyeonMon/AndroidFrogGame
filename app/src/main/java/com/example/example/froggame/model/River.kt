package com.example.example.froggame.model

import android.os.Handler

class River {

    private var speed: Int = 0
    private var direction: Int = 1

    var timber1: Timber
    var crocodile: Crocodile

    constructor() {
        timber1 = Timber()
        crocodile = Crocodile()
        crocodile.setLeft(crocodile.getLeft() - crocodile.width)
    }

    constructor(reverse: Boolean) {
        timber1 = Timber()
        crocodile = Crocodile(reverse)
        crocodile.setLeft(timber1.getRight() + crocodile.width)
        this.direction = -1

    }

    fun setRiver() {
        this.speed = (2..6).random()
        flow()
    }

    private fun flow() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {

                if (direction > 0) {
                    // timber move
                    if (timber1.getLeft() > 1080) {
                        timber1.setLeft(0F - timber1.width)
                    }
                    if (crocodile.getLeft() > 1080) {
                        crocodile.setLeft(0F - crocodile.width)
                    }
                    timber1.setLeft(timber1.getLeft() + speed * direction)
                    crocodile.setLeft(crocodile.getLeft() + speed * direction)
//                crocodile
                    // crocodile move
                } else {
                    if (timber1.getRight() < 0) {
                        timber1.setLeft(1080f)
                    }
                    if (crocodile.getRight() < 0) {
                        crocodile.setLeft(1080f)
                    }
                    timber1.setLeft(timber1.getLeft() + speed * direction)
                    crocodile.setLeft(crocodile.getLeft() + speed * direction)
                }


                handler.postDelayed(this, 10)
            }
        })
    }

    fun getSpeed(): Int {
        return this.speed
    }

    fun getDirection(): Int {
        return this.direction
    }

}