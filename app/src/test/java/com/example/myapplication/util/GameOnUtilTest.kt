package com.example.myapplication.util

import org.junit.Assert
import org.junit.Test

class GameOnUtilTest {
    private val playerInfo = Pair("JOHN", "RIO")

    private val mockPlayerInfoList = mutableListOf(
        Pair(GameOnUtil.PLAYER_ONE_KEY, playerInfo.first),
        Pair(GameOnUtil.PLAYER_ONE_KEY, playerInfo.first),
        Pair(GameOnUtil.PLAYER_TWO_KEY, playerInfo.second),
        Pair(GameOnUtil.PLAYER_ONE_KEY, playerInfo.first)
    )

    private val mockScoreDetailList =  mutableListOf(
        ScoreDetail(playerType = PlayerType.Player1, playerName = "JOHN", score = 40),
        ScoreDetail(playerType = PlayerType.Player2, playerName = "RIO", score = 15)
    )

    @Test
    fun testGenerateData() {
        val actualResult = GameOnUtil.generateData(playerInfo)
        val expectedResult = mutableMapOf(
            Pair(GameOnUtil.PLAYER_ONE_KEY, playerInfo.first),
            Pair(GameOnUtil.PLAYER_TWO_KEY, playerInfo.second)
        )
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun testGetScore() {
        val actualResult = GameOnUtil.getScore(mockPlayerInfoList)
        Assert.assertEquals(mockScoreDetailList, actualResult)
    }

    @Test
    fun testGetPlayerOneDetails() {
        Assert.assertNotNull(GameOnUtil.getPlayerOneDetails(mockScoreDetailList))
    }

    @Test
    fun testGetPlayerTwoScoreDetail() {
        Assert.assertNotNull(GameOnUtil.getPlayerTwoScoreDetail(mockScoreDetailList))
    }

    @Test
    fun testDecideWinner() {

        //when both player has same score
        val equalScore =  mutableListOf(
            ScoreDetail(playerType = PlayerType.Player1, playerName = "JOHN", score = 40),
            ScoreDetail(playerType = PlayerType.Player2, playerName = "RIO", score = 40)
        )
        val actualWinner = GameOnUtil.decideWinner(equalScore)
        val expectedWinner = "RIO"
        Assert.assertEquals(expectedWinner,actualWinner)

        //when player1 secured highest score
        val player1HasHighestScore =  mutableListOf(
            ScoreDetail(playerType = PlayerType.Player1, playerName = "JOHN", score = 40),
            ScoreDetail(playerType = PlayerType.Player2, playerName = "RIO", score = 15)
        )
        val actualWinner1 = GameOnUtil.decideWinner(player1HasHighestScore)
        val expectedWinner1 = "JOHN"
        Assert.assertEquals(expectedWinner1,actualWinner1)

        //when player2 secured highest score
        val player2HasHighestScore =  mutableListOf(
            ScoreDetail(playerType = PlayerType.Player1, playerName = "JOHN", score = 15),
            ScoreDetail(playerType = PlayerType.Player2, playerName = "RIO", score = 30)
        )
        val actualWinner2 = GameOnUtil.decideWinner(player2HasHighestScore)
        val expectedWinner2 = "RIO"
        Assert.assertEquals(expectedWinner2,actualWinner2)
    }
}