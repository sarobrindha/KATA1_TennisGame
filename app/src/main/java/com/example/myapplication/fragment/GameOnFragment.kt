package com.example.myapplication.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.util.DataState
import com.example.myapplication.util.GameOnUtil
import com.example.myapplication.util.ScoreDetail
import com.example.myapplication.viewmodel.GameOnFactory
import com.example.myapplication.viewmodel.GameOnViewModel
import kotlinx.android.synthetic.main.fragment_game_on.*

class GameOnFragment : Fragment() {

    private val playerInfo by lazy {
        arguments?.let {
            Pair(
                GameOnFragmentArgs.fromBundle(it).playerNameOne,
                GameOnFragmentArgs.fromBundle(it).playerNameTwo
            )
        } ?: Pair("", "")
    }

    private var scoreList: MutableList<ScoreDetail> = mutableListOf()

    private val viewModel by lazy {
        ViewModelProvider(this, GameOnFactory(playerInfo)).get(GameOnViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_on, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpObserver()
    }

    override fun onDetach() {
        scoreList.clear()
        super.onDetach()
    }

    private fun initView() {
        game_on_fragment_toolbar.setNavigationOnClickListener { (activity as MainActivity).onBackPressed() }
    }

    private fun setUpObserver() {
        viewModel.gameDataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.GameOver -> {
                    viewModel.updateWinner(scoreList)
                }
                is DataState.WinnerData -> {
                    updateWinnerOnBoard(dataState.playerName)
                }
                is DataState.Error -> {
                    showErrorMessage(dataState.message)
                }
            }
        })

        viewModel.scoreUpdateLiveData.observe(viewLifecycleOwner, Observer { scoreDetailList ->
            scoreList.addAll(scoreDetailList)
            updateScoreOnBoard(scoreDetailList)
        })
    }

    private fun updateScoreOnBoard(scoreDetailList: List<ScoreDetail>) {
        scoreDetailList.takeIf { it.isNotEmpty() }?.let { listData ->
            val playerOneScore = GameOnUtil.getPlayerOneDetails(listData)
            val playerTwoScore = GameOnUtil.getPlayerTwoScoreDetail(listData)
            player_one_score.text = getString(
                R.string.score_per_player,
                viewModel.getPlayerInfo().first,
                playerOneScore?.score ?: 0
            )
            player_two_score.text = getString(
                R.string.score_per_player,
                viewModel.getPlayerInfo().second,
                playerTwoScore?.score ?: 0
            )
        }
    }

    private fun updateWinnerOnBoard(playerName: String) {
        takeIf { winner_text != null }?.let {
            winner_text.visibility = View.VISIBLE
            winner_text.text = getString(R.string.congrats_message, playerName)
            Handler().postDelayed({
                findNavController().navigate(R.id.action_gameOnFragment_to_scoreBoardFragment)
            }, 3000)
        }
    }

    private fun showErrorMessage(errorMessage: String?) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Error")
            setMessage(errorMessage)
            setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
            setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }
}