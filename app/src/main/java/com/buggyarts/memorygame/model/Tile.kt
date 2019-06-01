package com.buggyarts.memorygame.model

class Tile {

    var value: Int = 0
    var isHidden: Boolean = true
    var isInvisible: Boolean = false

    constructor()

    constructor(value: Int, isHidden: Boolean, isInvisible: Boolean) {
        this.value = value
        this.isHidden = isHidden
        this.isInvisible = isInvisible
    }
}