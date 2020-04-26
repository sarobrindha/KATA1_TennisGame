package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.myapplication.fragment.StartGameFragment

private const val START_GAME_FRAGMENT_TAG = "start_game_fragment_tag"
private const val SPLASH_TIME_OUT = 3000L

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            loadInitFragment()
        }, SPLASH_TIME_OUT)
    }

    private fun loadInitFragment() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, StartGameFragment().newInstance(), START_GAME_FRAGMENT_TAG)
            commit()
        }
    }
}
