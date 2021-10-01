package com.example.example.froggame.model

class River {

    // 강은 속력와 방향을 가진다.
    // 해당 변수들 생성
    // 강의 direction은 기본 1 (오른쪽)
    private var speed: Int = 0
    private var direction: Int = 1

    // 강에 떠다닐 통나무와 악어 생성
    var timber1: Timber
    var timber2: Timber
    var crocodile: Crocodile

    // 강을 만들 때 기본적으로 통나무 2개, 악어 한마리 생성됨.
    // 이때 좌표값을 처음에 겹치지 않게 설정위해서 초기 값을 지정해준다.
    // 각 객체와 겹치지 않게 떨어뜨리린다.
    constructor() {
        timber1 = Timber()
        timber2 = Timber()
        crocodile = Crocodile()
        timber1.setLeft(0f)
        crocodile.setLeft(timber1.getLeft() - crocodile.width - 150)
        timber2.setLeft(crocodile.getLeft() - timber2.width - 150)
    }

    // 강의 흐름이 반대인 경우 (오른쪽->왼쪽으로 흐르는 경우)
    // 각 통나무, 악어 객체 생성하고 위치 설정
    // direction을 -1로 설정한다.
    constructor(reverse: Boolean) {
        timber1 = Timber()
        timber2 = Timber()
        crocodile = Crocodile(reverse)
        timber1.setLeft(1080f)
        crocodile.setLeft(timber1.getRight() + 150)
        timber2.setLeft(crocodile.getRight() + 150)
        this.direction = -1

    }

    // 강의 속력을 랜덤으로 설정하고 강 흐르기 시작!
    fun setRiver() {
        this.speed = (2..7).random()
        flow()
    }

    fun flow() {
        // 흐르기 시작
        if (direction > 0) {
            // 순방향 (왼 --> 오) 인 경우
            // 통나무1, 2, 악어가 오른쪽 벽을 지나 완전히 지나갔다면 해당 위치를 왼쪽으로 이동시켜 계속해서 움직일 수 있게 한다.
            if (timber1.getLeft() > 1080) {
                timber1.setLeft(0F - timber1.width - 150)
            }
            if (timber2.getLeft() > 1080) {
                timber2.setLeft(crocodile.getLeft() - timber2.width - 150)
            }
            if (crocodile.getLeft() > 1080) {
                crocodile.setLeft(timber1.getLeft() - crocodile.width - 150)
            }

        } else {
            // 역방향 (오 --> 왼) 인 경우
            // 통나무1, 2, 악어가 왼쪽 벽을 지나 완전히 지나갔다면 해당 위치를 오른쪽으로 이동시켜 계속해서 움직일 수 있게 한다.
            if (timber1.getRight() < 0) {
                timber1.setLeft(1080f + 150)
            }
            if (timber2.getRight() < 0) {
                timber2.setLeft(crocodile.getRight() + 150)
            }
            if (crocodile.getRight() < 0) {
                crocodile.setLeft(timber1.getRight() + 150)
            }
        }
        // 통나무1, 2, 악어가 강의 속력과 방향에 맞춰 움직이게 한다.
        timber1.setLeft(timber1.getLeft() + speed * direction)
        timber2.setLeft(timber2.getLeft() + speed * direction)
        crocodile.setLeft(crocodile.getLeft() + speed * direction)

    }

    // 개구리가 강에 올라탔을 때 해당 속력과 속도로 움직이기 위해 getSpeed(), getDirection() 메서드 제공
    fun getSpeed(): Int {
        return this.speed
    }

    fun getDirection(): Int {
        return this.direction
    }

    // 게임이 완전히 종료됐을 때, 멈춘다.
    fun clear() {
        this.speed = 0
        this.direction = 0
    }

}