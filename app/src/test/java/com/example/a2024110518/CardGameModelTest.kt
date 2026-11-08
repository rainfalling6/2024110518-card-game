package com.example.a2024110518

import com.example.a2024110518.model.Card
import com.example.a2024110518.model.CardMatchingGame
import com.example.a2024110518.model.Deck
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * 翻牌游戏模型的单元测试。
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
    fun game_initialState_hasZeroScoreAndExpectedCardCount() {
        val game = CardMatchingGame(16)

        assertEquals(0, game.score)
        assertEquals(16, game.cardCount)
    }

    @Test
    fun game_firstChoose_costsOnePointAndMarksChosen() {
        val game = CardMatchingGame(16)

        game.chooseCardAtIndex(0)

        assertEquals(-1, game.score)
        assertTrue(game.cardAtIndex(0).isChosen)
    }

    @Test
    fun game_chooseSameCardAgain_deselectsWithoutExtraCost() {
        val game = CardMatchingGame(16)

        game.chooseCardAtIndex(0)
        game.chooseCardAtIndex(0)

        assertEquals(-1, game.score)
        assertFalse(game.cardAtIndex(0).isChosen)
    }
}
