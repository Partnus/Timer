package com.partnus.timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        // Todo : background 기능구현 적용하기 (현재는 테스트를 위해 임시로 flow 를 사용)
        val progressRing = findViewById<Timer>(R.id.timer)
        progressRing.setInitTime(10000L) // 초기 시간 설정 10초
        CoroutineScope(Dispatchers.Main).launch {
            flow {
                // 10초 부터 0초까지 1초 간격으로 flow 전송
                for (currentPercent in progressRing.getInitTime() downTo 0 step 1000) {
                    emit(currentPercent)
                    delay(1000L)
                }
            }.collect { percent ->
                progressRing.setCurrentTime(percent) // 현재 시간 적용
            }
        }
    }
}
