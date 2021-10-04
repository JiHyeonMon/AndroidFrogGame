package com.example.example.froggame.model.landform

import com.example.example.froggame.model.character.Character

abstract class LandForm {
    open fun setLandForm(w: Int, h: Int) {}
    open fun clearLandForm() {}
    open fun getGameCharacter(): ArrayList<Character> {
        return arrayListOf()
    }
}