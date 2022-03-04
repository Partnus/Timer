package com.partnus.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.partnus.timer.front.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        // progressText 생성 및 테스트 Code
        val progressText = findViewById<Timer>(R.id.timer)
        progressText.setInitTime(10000L) // 초기 시간 설정 10초
        progressText.start()
    }
}
