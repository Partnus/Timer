package com.partnus.timer.back

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/*
*
* */
internal class TimerHandler(
    private val context: Context,
    private val startTime: Long,
    private val endTime: Long,
    private val stepTime: Long = 1000,
    private val currentTimeChangeFun : ((Long, TimerThread) -> Unit)? = null,
    private val timerThread: TimerThread,
) : Handler(Looper.getMainLooper()) {

    var endFunc: (() -> Unit)? = null

    // UI에서 현재 측정 시간을 가져 가기위해
    var currentTime: Long = 0
        set(value) {
            currentTimeChangeFun?.invoke(value, timerThread)
            field = value
        }

    // BroadcastReceiver register 위한 변수
    val br = TimerBroadcastReceiver()
    val filter = IntentFilter(TIMER_BROADCAST_ACTION)

    init {
        currentTime = startTime
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        Log.d("로그", "handleMessage: $msg")
        when (msg.what) {
            TIMER_PAUSE ->{

            }
            TIMER_RESET -> {
                unregisterLocalBroadcast() // 리셋시 브로드캐스트 등록 해제
                currentTime = startTime
                this.sendEmptyMessage(TIMER_PAUSE)
            }
            TIMER_START -> {
                registerLocalBroadcast() // 시작시 브로드캐스트 등록
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
                sendBroadcastMessage() // 타이머 종료시 브로드캐스트 전송
                unregisterLocalBroadcast() // 전송 후 등록 해제

                endFunc?.let { it() }
            }
        }
    }

    private fun registerLocalBroadcast() {
        LocalBroadcastManager.getInstance(context).registerReceiver(br, filter)
    }
    private fun unregisterLocalBroadcast() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(br)
    }
    private fun sendBroadcastMessage() {
        val intent = Intent().also {
            it.setAction(TIMER_BROADCAST_ACTION)
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }


    companion object {
        internal const val TIMER_PAUSE = 1000    // 일시 정지 상태
        internal const val TIMER_RESET = 1001    // 타이머를 다시 시작
        internal const val TIMER_START = 1002    // 타이머 시작
        internal const val TIMER_CONTINUE = 1003 // 타이머가 작동 중인 경우
        internal const val TIMER_STOP = 1004     // 타이머가 종료 된 경우
        // 유저는 이 액션으로 직접 브로드캐스트리시버를 등록하여 종료시에 시행될 작업을 처리할 수도 있음
        const val TIMER_BROADCAST_ACTION = "com.partnus.timer.TIMER_NOTIFICATION"
    }
}