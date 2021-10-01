package com.example.example.froggame.model

class Land {

    private var num = 0
    private val snakes = arrayListOf<Snake>()
    private var boards = arrayOf(ScoreBoard(), ScoreBoard(), ScoreBoard())

    // 게임이 재시작될 때마다 호출
    // 새로 뱀 생성위해 생성될 뱀 숫자 랜덤하게 뽑고, 해당 개수만큼 Snake객체 생성해서 배열에 넣는다.
    fun setLand() {
        num = (1..3).random()
        for (i in 1..num) {
            this.snakes.add(Snake())
        }

        relocate()
    }

    // 위치를 지정한다.
    // 뱀의 위치 랜덤으로 지정
    // 점수판의 위치로 조금 거리를 띄워 랜덤으로 지정
    private fun relocate() {
        for (i in 0 until num) {
            snakes[i].setLeft((1..700).random().toFloat())
        }
        boards[0].setLeft((0..100).random().toFloat())
        boards[1].setLeft((300..400).random().toFloat())
        boards[2].setLeft((600..900).random().toFloat())
    }

    fun getSnakes(): ArrayList<Snake> {
        return snakes
    }

    fun getBoards(): Array<ScoreBoard> {
        return boards
    }

    // 뱀을 랜덤하게 계속 생성
    // 게임 재시작 하게 될 때마다 생성 --> 게임 종료되면 clear()를 통해 생성한 snakes를 지워준다.
    fun clear() {
        snakes.clear()
    }


}