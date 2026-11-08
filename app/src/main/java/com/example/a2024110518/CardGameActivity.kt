package com.example.a2024110518

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2024110518.model.CardMatchingGame
import com.example.a2024110518.model.PlayerInfo

/**
 * 翻牌游戏 Activity，承担 MVC 中的 Controller 角色。
 *
 * View 使用 RecyclerView + GridLayoutManager 显示 24 张牌；
 * Adapter 负责单张牌 item 的绑定与点击回调；
 * Model 负责发牌、三张牌配对和计分。
 */
class CardGameActivity : AppCompatActivity(), CardAdapter.OnCardClickListener {

    private lateinit var rvCards: RecyclerView
    private lateinit var tvScore: TextView
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var game: CardMatchingGame
    private lateinit var adapter: CardAdapter
    private var gameOverAnnounced = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_game)
        PlayerInfo.ensureInitialized(this)

        rvCards = findViewById(R.id.rvCards)
        tvScore = findViewById(R.id.tvScore)

        layoutManager = GridLayoutManager(this, spanCountForOrientation(resources.configuration))
        rvCards.layoutManager = layoutManager
        rvCards.setHasFixedSize(true)

        findViewById<Button>(R.id.btnReset).setOnClickListener { startNewGame() }
        findViewById<Button>(R.id.btnExit).setOnClickListener { finish() }

        startNewGame()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        layoutManager.spanCount = spanCountForOrientation(newConfig)
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        PlayerInfo.highestScore = maxOf(PlayerInfo.highestScore, game.score)
    }

    override fun onCardClick(position: Int) {
        game.chooseCardAtIndex(position)
        adapter.notifyDataSetChanged()
        updateScore()

        if (allCardsMatched() && !gameOverAnnounced) {
            gameOverAnnounced = true
            PlayerInfo.highestScore = maxOf(PlayerInfo.highestScore, game.score)
            Toast.makeText(this, getString(R.string.toast_all_matched, game.score), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun startNewGame() {
        game = CardMatchingGame(CARD_COUNT)
        gameOverAnnounced = false
        adapter = CardAdapter(game.cards, this)
        rvCards.adapter = adapter
        updateScore()
    }

    private fun updateScore() {
        tvScore.text = getString(R.string.score_format, game.score)
    }

    private fun allCardsMatched(): Boolean {
        return game.cards.all { it.isMatched }
    }

    private fun spanCountForOrientation(configuration: Configuration): Int {
        return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LANDSCAPE_SPAN_COUNT
        } else {
            PORTRAIT_SPAN_COUNT
        }
    }

    companion object {
        private const val CARD_COUNT = 24
        private const val PORTRAIT_SPAN_COUNT = 4
        private const val LANDSCAPE_SPAN_COUNT = 6
    }
}
