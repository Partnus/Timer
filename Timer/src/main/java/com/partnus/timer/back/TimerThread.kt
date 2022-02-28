package com.partnus.timer.back

import android.content.Context
import android.util.Log

class TimerThread(
    private val context: Context,
    private val startTime: Long,
    private val endTime: Long,
    private val stepTime: Long = 1000,
    private val isDaemon: Boolean = true,
    // 타이머 시간이 변경될 때 마다 호출 되는 함수 입니다.
    private val currentTimeChangeFun: ((Long, TimerThread) -> Unit) = { _, _ -> },
    // 타이머가 종료 할 때 호출하는 함수 입니다.
    private var endFunc: (() -> Unit)? = null,
) {
    private var thread: Thread? = null

    private var timerHandler: TimerHandler = TimerHandler(
        context,
        startTime,
        endTime,
        stepTime,
        currentTimeChangeFun = currentTimeChangeFun,
        this
    )


    fun makeThread() {
        thread = object : Thread() {
            override fun run() {
                timerHandler.endFunc = {
                    // 만약 종료할때 호출할 함수가 있는 경우
                    endFunc?.let {
                        it()
                    }
                    // 타이머가 종료 되면 스레드를 정지 시킴
                    this.interrupt()
                }

            }
        }
        thread!!.isDaemon = isDaemon
    }

    fun startThread() {
        thread?.let {
            thread!!.start()
            timerHandler.sendEmptyMessage(TimerHandler.TIMER_START)
        }
    }

    fun timerPause() {
        // TODO:: Delayed remove 방법 필요
    }

    fun timerReset(){

    }

    fun timerStop(){

    }

    fun threadEnabled(): Boolean = thread != null
    fun threadIsAlive(): Boolean = if (thread != null) thread!!.isAlive else false
}