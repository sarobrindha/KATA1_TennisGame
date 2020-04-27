package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_game_over.*

class GameOverFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_over, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        end_game_button.setOnClickListener { requireActivity().finish() }
        reset_button.setOnClickListener {
            findNavController().navigate(R.id.action_gameOverFragment_to_startGameFragment)
        }
    }
}