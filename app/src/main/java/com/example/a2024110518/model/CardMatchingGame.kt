package com.example.a2024110518.model

/**
 * 三张牌配对的翻牌游戏模型。
 *
 * 每局按 3 张一组发牌，保证同一点数的 3 张牌一定同时出现在牌桌上，
 * 因此 24 张牌恰好可以组成 8 组，牌局始终可解。
 */
class CardMatchingGame(private val totalCards: Int) {

    var score = 0
        private set

    private val cardList: MutableList<Card> = mutableListOf()

    val cards: List<Card>
        get() = cardList

    val cardCount: Int
        get() = cardList.size

    val mismatchPenalty = 2
    val matchBonus = 4
    val costToChoose = 1

    init {
        require(totalCards > 0 && totalCards % MATCH_COUNT == 0) {
            "totalCards must be a positive multiple of $MATCH_COUNT"
        }
        reset()
    }

    /**
     * 重新发牌并清零分数，供“重置游戏”和横竖屏切换时复用。
     */
    fun reset() {
        score = 0
        cardList.clear()

        val groupCount = totalCards / MATCH_COUNT
        val ranks = Card.rankStrings.toMutableList()
        java.util.Collections.shuffle(ranks)
        for (rank in ranks.take(groupCount)) {
            val suits = Card.validSuits.toMutableList()
            java.util.Collections.shuffle(suits)
            for (suit in suits.take(MATCH_COUNT)) {
                cardList.add(Card(suit = suit, rank = rank))
            }
        }
        java.util.Collections.shuffle(cardList)
    }

    fun cardAtIndex(index: Int): Card {
        return cardList[index]
    }

    /**
     * 翻开一张牌。每翻开一张牌扣 1 分；当桌面上同时有 3 张已翻开的牌时，
     * 由 Card.match() 判断这 3 张牌是否配对，配对成功加 16 分并锁定，
     * 配对失败扣 2 分并把 3 张牌全部翻回去。
     */
    fun chooseCardAtIndex(index: Int) {
        val card = cardAtIndex(index)
        if (card.isMatched) {
            return
        }
        if (card.isChosen) {
            card.isChosen = false
            return
        }

        score -= costToChoose
        card.isChosen = true

        val chosenCards = cardList.filter { it.isChosen && !it.isMatched }
        if (chosenCards.size == MATCH_COUNT) {
            val matchScore = chosenCards.first().match(chosenCards.drop(1).toTypedArray())
            if (matchScore > 0) {
                score += matchScore * matchBonus
                chosenCards.forEach {
                    it.isChosen = false
                    it.isMatched = true
                }
            } else {
                score -= mismatchPenalty
                chosenCards.forEach { it.isChosen = false }
            }
        }
    }

    private companion object {
        const val MATCH_COUNT = 3
    }
}
