package com.example.a2024110518

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a2024110518.model.CardMatchingGame
import com.example.a2024110518.model.PlayerInfo

/**
 * 翻牌游戏 Activity，承担 MVC 中的 Controller 角色：
 * 接收用户点击、调用 CardMatchingGame 模型处理，并把最新状态同步到界面。
 */
class CardGameActivity : AppCompatActivity() {

    private lateinit var gridCards: GridLayout
    private lateinit var tvScore: TextView
    private lateinit var game: CardMatchingGame
    private val cardViews = mutableListOf<TextView>()
    private var gameOverAnnounced = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_game)
        PlayerInfo.ensureInitialized(this)

        gridCards = findViewById(R.id.gridCards)
        tvScore = findViewById(R.id.tvScore)

        findViewById<Button>(R.id.btnReset).setOnClickListener { startNewGame() }
        findViewById<Button>(R.id.btnExit).setOnClickListener { finish() }

        startNewGame()
    }

    override fun onPause() {
        super.onPause()
        PlayerInfo.highestScore = maxOf(PlayerInfo.highestScore, game.score)
    }

    private fun startNewGame() {
        game = CardMatchingGame(CARD_COUNT)
        gameOverAnnounced = false
        buildCardViews()
        updateGameViews()
    }

    private fun buildCardViews() {
        gridCards.removeAllViews()
        cardViews.clear()

        val cardWidth = resources.getDimensionPixelSize(R.dimen.card_width)
        val cardHeight = resources.getDimensionPixelSize(R.dimen.card_height)
        val margin = resources.getDimensionPixelSize(R.dimen.card_margin)

        for (index in 0 until game.cardCount) {
            val cardView = TextView(this).apply {
                gravity = Gravity.CENTER
                textSize = 20f
                typeface = Typeface.DEFAULT_BOLD
                setTextColor(getColor(R.color.card_text))
                setBackgroundResource(R.drawable.bg_card_back)
                isClickable = true
                setOnClickListener { onCardClicked(index) }
            }
            val params = GridLayout.LayoutParams().apply {
                width = cardWidth
                height = cardHeight
                setMargins(margin, margin, margin, margin)
            }
            gridCards.addView(cardView, params)
            cardViews.add(cardView)
        }
    }

    private fun onCardClicked(index: Int) {
        game.chooseCardAtIndex(index)
        updateGameViews()

        if (allCardsMatched() && !gameOverAnnounced) {
            gameOverAnnounced = true
            PlayerInfo.highestScore = maxOf(PlayerInfo.highestScore, game.score)
            Toast.makeText(this, getString(R.string.toast_all_matched, game.score), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun updateGameViews() {
        tvScore.text = getString(R.string.score_format, game.score)

        for (index in 0 until game.cardCount) {
            val card = game.cardAtIndex(index)
            val cardView = cardViews[index]
            if (card.isChosen || card.isMatched) {
                cardView.text = card.toString()
                cardView.setBackgroundResource(
                    if (card.isMatched) R.drawable.bg_card_matched else R.drawable.bg_card_front
                )
            } else {
                cardView.text = getString(R.string.card_back)
                cardView.setBackgroundResource(R.drawable.bg_card_back)
            }
        }
    }

    private fun allCardsMatched(): Boolean {
        for (index in 0 until game.cardCount) {
            if (!game.cardAtIndex(index).isMatched) {
                return false
            }
        }
        return true
    }

    companion object {
        private const val CARD_COUNT = 16
    }
}
