package com.example.myapplication.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_player_info.*

class PlayerInfoFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player_info,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        player_info_fragment_toolbar.setNavigationOnClickListener { (activity as MainActivity).onBackPressed()  }

        lets_play_button.setOnClickListener {
            val action = PlayerInfoFragmentDirections.actionPlayerInfoFragmentToGameOnFragment(player_one_name_field.text.toString(),player_two_name_field.text.toString())
            findNavController().navigate(action)
            it.hideKeyboard()
            player_one_name_field.text.clear()
            player_two_name_field.text.clear()
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}