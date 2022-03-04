package com.partnus.timer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.partnus.timer.back.TimerThread
import com.partnus.timer.front.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // progressbar 생성 및 테스트 Code
        val progressRing = findViewById<Timer>(R.id.timer)
        progressRing.setInitTime(10000L) // 초기 시간 설정 10초
        progressRing.start()
    }
}
