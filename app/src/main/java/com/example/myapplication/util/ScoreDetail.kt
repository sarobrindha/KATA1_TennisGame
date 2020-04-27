package com.example.myapplication.util

data class ScoreDetail(val playerType:PlayerType,val playerName:String,val score:Int)

enum class PlayerType{
    Player1,
    Player2
}