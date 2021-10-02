package com.example.example.froggame.model.character

import com.example.example.froggame.model.character.Character

class Snake : Character() {

    override fun isFrogGetOn(lFrog: Float, rFrog: Float): Boolean {
        // 올라탔는지 판단 - 완전히 겹치는지 판단
        // 일단 뱀과 겹치면 뱀과 닿였다고 판단
        if (
            (getLeft() < rFrog && rFrog < getRight()) || // 1. 개구리의 오른좌표가 뱀 내부에 있는 경우 - 개구리의 오른편이 뱀과 겹침
            (getLeft() < lFrog && lFrog < getRight()) || // 2. 개구리의 왼 좌표가 뱀 내부에 있는 경우 - 개구리의 왼편이 뱀과 겹침
            (getLeft() < lFrog && rFrog < getRight()) // 3. 개구리가 완전히 뱀 안에 들어가는 경우 - 완전히 겹침
        ) {
            return true
        }
        return false
    }

}