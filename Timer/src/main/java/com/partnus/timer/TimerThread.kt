package com.partnus.timer

import android.util.Log

class TimerThread(
    private val startTime: Long,
    private val endTime: Long,
    private val stepTime: Long = 1000,
    private val timerCountType : Int = MILLISECOND,
    private val endFunc: (() -> Unit)? = null
) : Thread() {

    companion object{
        const val MILLISECOND = 0
        const val SECOND = 1
    }
    
    init {
        // 주 스레드가 종료 되면 같이 종료 되기 위해 데몬 설
        this.isDaemon = true
    }

    override fun run() {
        var sT = startTime
        var eT = endTime
        var stT = stepTime

        // 초 -> 밀리초
        if(timerCountType == SECOND){
            sT  *= 1000
            eT  *= 1000
            stT  *= 1000
        }
        
        TimerHandler(
            startTime,
            endTime,
            stepTime
        ) {
            // 만약 종료할때 호출할 함수가 있는 경우
            endFunc?.let {
                it()
            }
            // 타이머가 종료 되면 스레드를 정지 시킴
            this.interrupt()
        }.sendEmptyMessage(TimerHandler.TIMER_START)
    }
}