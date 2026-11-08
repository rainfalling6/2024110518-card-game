package com.example.a2024110518.model

import java.util.Random

class Deck {
    private val cards = mutableListOf<Card>()
    private val random = Random()

    init {
        for (suit in Card.validSuits) {
            for (rank in Card.rankStrings) {
                cards.add(Card(suit = suit, rank = rank))
            }
        }
    }

    fun drawRandomCard(): Card? {
        var randomCard: Card? = null
        if (cards.isNotEmpty()) {
            randomCard = cards.removeAt(random.nextInt(cards.size))
        }
        return randomCard
    }
}
