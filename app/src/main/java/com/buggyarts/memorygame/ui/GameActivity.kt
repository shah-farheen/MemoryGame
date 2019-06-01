package com.buggyarts.memorygame.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.buggyarts.memorygame.R
import com.buggyarts.memorygame.model.Tile
import com.buggyarts.memorygame.utils.AppConstants
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), TilesAdapter.TilesListener {

    companion object {
        const val PARAM_LEVEL = "PARAM_LEVEL"
    }

    private var startTime: Long = 0
    private var timeElapsed: Long = 0
    private var isGamePaused = false
    private var isGameFinished = false
    private lateinit var tilesAdapter: TilesAdapter
    private lateinit var level: AppConstants.LEVELS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        level = if (intent.hasExtra(PARAM_LEVEL)) {
            AppConstants.LEVELS.valueOf(intent.getStringExtra(PARAM_LEVEL))
        } else {
            AppConstants.LEVELS.EASY
        }

        setupTiles()
        setClickListeners()
    }

    private fun setupTiles() {
        val numbers = ArrayList<Int>()
        for (i in 1..(level.rows * level.columns) / 2) {
            numbers.add(i)
            numbers.add(i)
        }

        val tilesLayoutManager = rv_tiles.layoutManager as GridLayoutManager
        tilesLayoutManager.spanCount = level.columns
        val tilesList = ArrayList<Tile>(level.rows * level.columns)
        for (i in 1..level.rows * level.columns) {
            tilesList.add(
                Tile(
                    value = numbers.removeAt((0 until numbers.size).random()),
                    isHidden = true,
                    isInvisible = false
                )
            )
        }
        tilesAdapter = TilesAdapter(context = this, tilesList = tilesList, tilesListener = this)
        rv_tiles.adapter = tilesAdapter
    }

    private fun setClickListeners() {
        btn_play_pause.setOnClickListener {
            if (isGamePaused) {
                resumeGame()
            } else {
                pauseGame()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resumeGame()
    }

    override fun onPause() {
        pauseGame()
        super.onPause()
    }

    private fun pauseGame() {
        timeElapsed += System.currentTimeMillis() - startTime
        isGamePaused = true
        btn_play_pause.text = getString(R.string.play)
        tilesAdapter.setGamePaused(true)
    }

    private fun resumeGame() {
        if (isGameFinished) {
            Toast.makeText(this, "Game Finished", Toast.LENGTH_SHORT).show()
            return
        }
        isGamePaused = false
        btn_play_pause.text = getString(R.string.pause)
        tilesAdapter.setGamePaused(false)
        startTime = System.currentTimeMillis()
    }

    override fun onGameFinished() {
        pauseGame()
        isGameFinished = true
        var score: Double = 1.0 / timeElapsed
        score *= 1000.0
        tv_score.text = String.format("Score : %d", score.toInt())
    }
}
