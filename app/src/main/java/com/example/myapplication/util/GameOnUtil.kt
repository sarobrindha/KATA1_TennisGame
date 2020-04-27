package com.example.myapplication.util

import androidx.annotation.VisibleForTesting

object GameOnUtil {
    @VisibleForTesting
    const val PLAYER_ONE_KEY = "player_one"

    @VisibleForTesting
    const val PLAYER_TWO_KEY = "player_two"

    private const val MAX_SCORE = 40
    private const val INIT_POSITION = 0

    fun generateData(playerInfo: Pair<String, String>) =
        mutableMapOf(
            Pair(PLAYER_ONE_KEY, playerInfo.first),
            Pair(PLAYER_TWO_KEY, playerInfo.second)
        )

    fun getScore(scoreProgressList: List<Pair<String, String>>): List<ScoreDetail> {
        return scoreProgressList.groupBy({ it.first }, { it.second }).map {
            val score = when (it.value.size) {
                0 -> 0
                1 -> 15
                2 -> 30
                3 -> 40
                else -> 40
            }
            val playerType =
                if (it.key == PLAYER_ONE_KEY) PlayerType.Player1 else PlayerType.Player2
            ScoreDetail(playerType, it.value[INIT_POSITION], score)
        }
    }

    fun getPlayerOneDetails(scoreDetailList: List<ScoreDetail>): ScoreDetail? {
        return scoreDetailList.firstOrNull {
            it.playerType == PlayerType.Player1
        }
    }

    fun getPlayerTwoScoreDetail(scoreDetailList: List<ScoreDetail>): ScoreDetail? {
        return scoreDetailList.firstOrNull {
            it.playerType == PlayerType.Player2
        }
    }

    fun decideWinner(scoreDetailList: List<ScoreDetail>): String {
        val scoreGroupedList = scoreDetailList.groupBy({ it.playerName }, { it.score }).toList()
        val player1ScoreDetail =
            scoreDetailList.groupBy({ it.playerName }, { it.score }).toList().first()
        val playerWhoWonBall =
            scoreDetailList.groupBy({ it.playerName }, { it.score }).toList().last()
        val player1Score = scoreGroupedList.first().second.last()
        val player2Score = scoreGroupedList.last().second.last()

        return when {
            player1Score == player2Score -> {
                playerWhoWonBall.first
            }
            player1Score == MAX_SCORE || player1Score > player2Score -> {
                player1ScoreDetail.first
            }
            player2Score == MAX_SCORE || player2Score > player1Score -> {
                playerWhoWonBall.first
            }
            else -> {
                ""
            }
        }
    }
}