package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.util.DataState
import com.example.myapplication.util.GameOnUtil
import com.example.myapplication.util.ScoreDetail
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GameOnViewModel(private val playerInfo: Pair<String, String>) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val mutableGamaDataState = MutableLiveData<DataState<List<ScoreDetail>>>()
    val gameDataState: LiveData<DataState<List<ScoreDetail>>> = mutableGamaDataState

    private val mutableScoreUpdate = MutableLiveData<List<ScoreDetail>>()
    val scoreUpdateLiveData: LiveData<List<ScoreDetail>> = mutableScoreUpdate

    init {
        processScoreForPlayer()
    }

    fun processScoreForPlayer(){
        val playerInfoMap = GameOnUtil.generateData(playerInfo)
        val randomPlayerList = mutableListOf<Pair<String, String>>()
        compositeDisposable.add(
            Observable.interval(0, 6000, TimeUnit.MILLISECONDS)
                .flatMap {
                    val winner = playerInfoMap.entries.shuffled().random()
                    randomPlayerList.add(Pair(winner.key, winner.value))
                    Observable.just(GameOnUtil.getScore(randomPlayerList))
                }
                .take(4)
                .doFinally { mutableGamaDataState.postValue(DataState.GameOver()) }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    mutableScoreUpdate.postValue(it)
                }, {
                    mutableGamaDataState.postValue(DataState.Error(it.message))
                    Log.e("exception", "Unexpected Error$it")
                })
        )

    }

    fun updateWinner(scoreDetailList: List<ScoreDetail>) {
        mutableGamaDataState.value = DataState.WinnerData(GameOnUtil.decideWinner(scoreDetailList))
    }

    fun getPlayerInfo() = playerInfo

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


@Suppress("UNCHECKED_CAST")
class GameOnFactory constructor(private val playerInfo: Pair<String, String>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameOnViewModel(playerInfo) as T
    }
}