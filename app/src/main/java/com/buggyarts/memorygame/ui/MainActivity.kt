package com.buggyarts.memorygame.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.buggyarts.memorygame.R
import com.buggyarts.memorygame.utils.AppConstants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setClickListeners()
    }

    private fun setClickListeners() {
        btn_level_easy.setOnClickListener {
            startActivity(
                Intent(this, GameActivity::class.java)
                    .putExtra(GameActivity.PARAM_LEVEL, AppConstants.LEVELS.EASY.name)
            )
            finish()
        }

        btn_level_medium.setOnClickListener {
            startActivity(
                Intent(this, GameActivity::class.java)
                    .putExtra(GameActivity.PARAM_LEVEL, AppConstants.LEVELS.MEDIUM.name)
            )
            finish()
        }

        btn_level_hard.setOnClickListener {
            startActivity(
                Intent(this, GameActivity::class.java)
                    .putExtra(GameActivity.PARAM_LEVEL, AppConstants.LEVELS.HARD.name)
            )
            finish()
        }
    }
}
