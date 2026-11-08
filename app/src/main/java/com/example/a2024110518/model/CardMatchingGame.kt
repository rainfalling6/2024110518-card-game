package com.example.a2024110518.model

class CardMatchingGame(count: Int) {
    var score = 0
        private set

    private val cards: MutableList<Card> = mutableListOf()

    val cardCount: Int
        get() = cards.size

    val mismatchPenalty = 2
    val matchBonus = 4
    val costToChoose = 1

    init {
        val deck = Deck()
        for (i in 1..count) {
            val card = deck.drawRandomCard()
            if (card != null) {
                cards.add(card)
            }
        }
    }

    fun cardAtIndex(index: Int): Card {
        return cards[index]
    }

    fun chooseCardAtIndex(index: Int) {
        val card = cardAtIndex(index)
        if (!card.isMatched) {
            if (card.isChosen) {
                card.isChosen = false
            } else {
                for (otherCard in cards) {
                    if (otherCard.isChosen && !otherCard.isMatched) {
                        val matchScore = card.match(arrayOf(otherCard))
                        if (matchScore > 0) {
                            score += matchScore * matchBonus
                            otherCard.isMatched = true
                            card.isMatched = true
                        } else {
                            score -= mismatchPenalty
                            otherCard.isChosen = false
                        }
                        break
                    }
                }
                score -= costToChoose
                card.isChosen = true
            }
        }
    }
}
