package com.partnus.timer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TimerThread(
            0,
            5000,
        ) { Log.d("로그", "onCreate: END") }.start()
    }
}
