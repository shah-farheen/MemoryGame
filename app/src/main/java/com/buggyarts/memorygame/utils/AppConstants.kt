package com.buggyarts.memorygame.utils

object AppConstants {

    enum class LEVELS(val rows: Int, val columns: Int) {
        EASY(3, 2),
        MEDIUM(4, 3),
        HARD(5, 4)
    }
}