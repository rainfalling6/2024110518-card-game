package com.example.a2024110518

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a2024110518.model.Card

/**
 * RecyclerView 适配器：把 [Card] 列表绑定到 item_card 布局。
 *
 * 适配器只负责“怎么显示”和“点击回调”，不处理游戏规则；
 * 点击事件通过 [OnCardClickListener] 交回 CardGameActivity（Controller）。
 */
class CardAdapter(
    private val cards: List<Card>,
    private val listener: OnCardClickListener
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    interface OnCardClickListener {
        fun onCardClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false) as TextView
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        val cardView = holder.cardView

        // 横屏和竖屏使用不同的卡片高度，保证 24 张牌都能完整显示。
        val resources = cardView.resources
        val height = resources.getDimensionPixelSize(R.dimen.card_height)
        val margin = resources.getDimensionPixelSize(R.dimen.card_margin)
        val params = cardView.layoutParams as RecyclerView.LayoutParams
        params.height = height
        params.setMargins(margin, margin, margin, margin)
        cardView.layoutParams = params

        if (card.isChosen || card.isMatched) {
            cardView.text = card.toString()
            cardView.setBackgroundResource(
                if (card.isMatched) {
                    R.drawable.bg_card_matched
                } else {
                    R.drawable.bg_card_front
                }
            )
        } else {
            cardView.text = cardView.context.getString(R.string.card_back)
            cardView.setBackgroundResource(R.drawable.bg_card_back)
        }

        cardView.setOnClickListener {
            listener.onCardClick(holder.bindingAdapterPosition)
        }
    }

    class CardViewHolder(val cardView: TextView) : RecyclerView.ViewHolder(cardView)
}
