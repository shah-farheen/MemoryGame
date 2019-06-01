package com.buggyarts.memorygame.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.buggyarts.memorygame.R
import com.buggyarts.memorygame.model.Tile

class TilesAdapter(
    private val context: Context,
    private val tilesList: ArrayList<Tile>,
    private val tilesListener: TilesListener,
    private val inflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<TilesAdapter.TilesViewHolder>() {

    private var isGamePaused = false
    private val shownList: ArrayList<Int> = ArrayList()

    fun setGamePaused(isGamePaused: Boolean) {
        this.isGamePaused = isGamePaused
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TilesViewHolder {
        return TilesViewHolder(inflater.inflate(R.layout.item_tile, parent, false))
    }

    override fun getItemCount(): Int {
        return tilesList.size
    }

    override fun onBindViewHolder(holder: TilesViewHolder, position: Int) {
        val tile = tilesList[position]
        if (tile.isInvisible) {
            holder.rootView.visibility = View.INVISIBLE
        } else {
            holder.rootView.visibility = View.VISIBLE
        }

        if (tile.isHidden) {
            holder.tvTileValue.text = ""
        } else {
            holder.tvTileValue.text = tile.value.toString()
        }
    }

    private fun checkMatch() {
        if (tilesList[shownList[0]].value == tilesList[shownList[1]].value) {
            tilesList[shownList[0]].isInvisible = true
            tilesList[shownList[1]].isInvisible = true
            notifyItemChanged(shownList[0])
            notifyItemChanged(shownList[1])
            if (checkIfFinished()) {
                tilesListener.onGameFinished()
            }
        } else {
            tilesList[shownList[0]].isHidden = true
            tilesList[shownList[1]].isHidden = true
            notifyItemChanged(shownList[0])
            notifyItemChanged(shownList[1])
        }
        shownList.clear()
    }

    private fun checkIfFinished(): Boolean {
        for (tile in tilesList) {
            if (!tile.isInvisible) {
                return false
            }
        }
        return true
    }

    inner class TilesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTileValue: TextView = itemView.findViewById(R.id.tv_value)
        val rootView = itemView

        init {
            itemView.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                if (isGamePaused) {
                    Toast.makeText(context, "Resume game to continue!!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val tile = tilesList[adapterPosition]
                if (!tile.isInvisible && tile.isHidden) {

                    if (shownList.size > 1) {
                        return@setOnClickListener
                    }

                    tile.isHidden = false
                    shownList.add(adapterPosition)
                    notifyItemChanged(adapterPosition)
                    if (shownList.size == 2) {
                        checkMatch()
                    }
                }
            }
        }
    }

    interface TilesListener {
        fun onGameFinished()
    }
}