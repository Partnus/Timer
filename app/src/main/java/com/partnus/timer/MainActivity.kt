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
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testProgressRing()
        testTimerWorkerRequest()
    }

    private fun testProgressRing() {
        // progressbar 생성 및 테스트 Code
        val progressRing = findViewById<Timer>(R.id.timer)
        progressRing.setInitTime(10000L) // 초기 시간 설정 10초
        progressRing.start()
    }

    private fun testTimerWorkerRequest() {
        TimerThread(
            context = this,
            startTime = 0,
            endTime = 3000,
        ) {}.run()
        Log.e(TAG_TIMER_THREAD, "타이머 시작")
    }

    companion object {
        const val TAG_TIMER_THREAD = "TAG_TIMER_THREAD"
    }
}
