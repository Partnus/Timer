package com.partnus.timer

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class TimerHandler : Handler(Looper.getMainLooper()) {
    var counter = 0
    companion object{
        const val TIMER_RESET = 1001
        const val TIMER_START = 1002
        const val TIMER_STOP = 1003
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when(msg.what){
            TIMER_RESET -> {}
            TIMER_START -> {
                Log.d("로그", "handleMessage: ${counter++}")
                this.sendEmptyMessageDelayed(TIMER_START, 1000)
            }
            TIMER_STOP -> {}
        }
    }
}
