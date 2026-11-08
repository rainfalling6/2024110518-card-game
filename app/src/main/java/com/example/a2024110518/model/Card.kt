package com.example.a2024110518.model

class Card(
    private val suit: String,
    private val rank: String,
    var isChosen: Boolean = false,
    var isMatched: Boolean = false
) {
    companion object {
        val rankStrings = arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val validSuits = arrayOf("♥", "♦", "♠", "♣")
    }

    override fun toString(): String {
        return suit + rank
    }

    /**
     * 判断当前牌与 [otherCards] 是否组成一组。
     *
     * 规则由原来的两张牌扩展为三张牌：
     * 所有牌点数相同返回 4，所有牌花色相同返回 1，否则返回 0。
     * 由于方法接收的是数组，两张牌模式仍然兼容。
     */
    fun match(otherCards: Array<Card>): Int {
        if (otherCards.isEmpty()) {
            return 0
        }
        if (otherCards.all { it.rank == rank }) {
            return 4
        }
        if (otherCards.all { it.suit == suit }) {
            return 1
        }
        return 0
    }
}
