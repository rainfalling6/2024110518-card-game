package com.example.a2024110518

import com.example.a2024110518.model.Card
import com.example.a2024110518.model.CardMatchingGame
import com.example.a2024110518.model.Deck
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * 三张牌配对翻牌游戏模型的单元测试。
 */
class CardGameModelTest {

    @Test
    fun card_matchSameRank_returns4() {
        val card = Card(suit = "♥", rank = "A")
        val other = Card(suit = "♠", rank = "A")

        assertEquals(4, card.match(arrayOf(other)))
    }

    @Test
    fun card_matchSameSuit_returns1() {
        val card = Card(suit = "♥", rank = "A")
        val other = Card(suit = "♥", rank = "2")

        assertEquals(1, card.match(arrayOf(other)))
    }

    @Test
    fun card_matchDifferentRankAndSuit_returns0() {
        val card = Card(suit = "♥", rank = "A")
        val other = Card(suit = "♣", rank = "2")

        assertEquals(0, card.match(arrayOf(other)))
    }

    @Test
    fun card_matchThreeSameRank_returns4() {
        val card = Card(suit = "♥", rank = "A")
        val second = Card(suit = "♦", rank = "A")
        val third = Card(suit = "♠", rank = "A")

        assertEquals(4, card.match(arrayOf(second, third)))
    }

    @Test
    fun card_matchThreeSameSuit_returns1() {
        val card = Card(suit = "♥", rank = "A")
        val second = Card(suit = "♥", rank = "2")
        val third = Card(suit = "♥", rank = "3")

        assertEquals(1, card.match(arrayOf(second, third)))
    }

    @Test
    fun card_matchThreeWithDifferentRankAndSuit_returns0() {
        val card = Card(suit = "♥", rank = "A")
        val second = Card(suit = "♦", rank = "2")
        val third = Card(suit = "♠", rank = "3")

        assertEquals(0, card.match(arrayOf(second, third)))
    }

    @Test
    fun deck_drawsAll52UniqueCards() {
        val deck = Deck()
        val drawnCards = mutableSetOf<String>()

        var card = deck.drawRandomCard()
        while (card != null) {
            assertTrue(drawnCards.add(card.toString()))
            card = deck.drawRandomCard()
        }

        assertEquals(52, drawnCards.size)
    }

    @Test
    fun game_initialState_hasZeroScoreAnd24Cards() {
        val game = CardMatchingGame(24)

        assertEquals(0, game.score)
        assertEquals(24, game.cardCount)
    }

    @Test
    fun game_firstChoose_costsOnePointAndMarksChosen() {
        val game = CardMatchingGame(24)

        game.chooseCardAtIndex(0)

        assertEquals(-1, game.score)
        assertTrue(game.cardAtIndex(0).isChosen)
    }

    @Test
    fun game_chooseSameCardAgain_deselectsWithoutExtraCost() {
        val game = CardMatchingGame(24)

        game.chooseCardAtIndex(0)
        game.chooseCardAtIndex(0)

        assertEquals(-1, game.score)
        assertFalse(game.cardAtIndex(0).isChosen)
    }

    @Test
    fun game_dealsEightSolvableTriples() {
        val game = CardMatchingGame(24)
        val rankCounts = game.cards.groupingBy { it.toString().drop(1) }.eachCount()

        assertEquals(8, rankCounts.size)
        assertTrue(rankCounts.values.all { it == 3 })
    }

    @Test
    fun game_threeCardsOfSameRank_areMatchedAndScored() {
        val game = CardMatchingGame(24)
        val triple = game.cards.withIndex()
            .groupBy { it.value.toString().drop(1) }
            .values
            .first { it.size == 3 }

        triple.forEach { game.chooseCardAtIndex(it.index) }

        assertEquals(13, game.score)
        triple.forEach {
            assertTrue(game.cardAtIndex(it.index).isMatched)
            assertFalse(game.cardAtIndex(it.index).isChosen)
        }
    }

    @Test
    fun game_threeCardsOfDifferentRanks_areNotMatchedAndPenalized() {
        val game = CardMatchingGame(24)
        val ranks = mutableSetOf<String>()
        val indexes = mutableListOf<Int>()
        for (index in 0 until game.cardCount) {
            if (ranks.add(game.cardAtIndex(index).toString().drop(1))) {
                indexes.add(index)
            }
            if (indexes.size == 3) {
                break
            }
        }

        indexes.forEach { game.chooseCardAtIndex(it) }

        assertEquals(-5, game.score)
        indexes.forEach {
            assertFalse(game.cardAtIndex(it).isChosen)
            assertFalse(game.cardAtIndex(it).isMatched)
        }
    }

    @Test
    fun game_reset_restoresScoreAndDealsNewCards() {
        val game = CardMatchingGame(24)
        game.chooseCardAtIndex(0)

        game.reset()

        assertEquals(0, game.score)
        assertEquals(24, game.cardCount)
        assertTrue(game.cards.none { it.isChosen || it.isMatched })
        val rankCounts = game.cards.groupingBy { it.toString().drop(1) }.eachCount()
        assertTrue(rankCounts.values.all { it == 3 })
    }
}
