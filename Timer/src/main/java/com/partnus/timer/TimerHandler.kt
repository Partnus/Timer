package com.partnus.timer

import android.os.Handler
import android.os.Looper
import android.os.Message

/**/
class TimerHandler(
    val startTime: Long,
    val endTime: Long,
    val stepTime: Long = 1000,
    val endFunc: (() -> Unit)? = null
) : Handler(Looper.getMainLooper()) {

    // UI에서 현재 측정 시간을 가져 가기위해
    var currentTime: Long = 0

    companion object {
        const val TIMER_PAUSE = 1000    // 일시 정지 상태
        const val TIMER_RESET = 1001    // 타이머를 다시 시작
        const val TIMER_START = 1002    // 타이머 시작
        const val TIMER_CONTINUE = 1003 // 타이머가 작동 중인 경우
        const val TIMER_STOP = 1004     // 타이머가 종료 된 경우
    }

    init {
        currentTime = startTime
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when (msg.what) {
            TIMER_PAUSE ->{

            }
            TIMER_RESET -> {
                currentTime = startTime
                this.sendEmptyMessage(TIMER_PAUSE)
            }
            TIMER_START -> {
                this.sendEmptyMessage(TIMER_CONTINUE)
            }
            TIMER_CONTINUE -> {
                currentTime += stepTime
                if (endTime > currentTime)
                    this.sendEmptyMessageDelayed(TIMER_CONTINUE, stepTime)
                else
                    this.sendEmptyMessage(TIMER_STOP)
            }
            TIMER_STOP -> {
                endFunc?.let { it() }
            }
        }
    }
}
