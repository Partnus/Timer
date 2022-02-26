package com.partnus.timer.back

import android.content.Context
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.work.*

/**/
class TimerHandler(
    private val context: Context,
    val startTime: Long,
    val endTime: Long,
    val stepTime: Long = 1000,
    val endFunc: (() -> Unit)? = null
) : Handler(Looper.getMainLooper()) {

    // UI에서 현재 측정 시간을 가져 가기위해
    var currentTime: Long = 0

    // BroadcastReceiver register 위한 변수
    val br = TimerBroadcastReceiver()
    val filter = IntentFilter(TIMER_BROADCAST_ACTION)

    // WorkManager
    private val workManager = WorkManager.getInstance(context)

    init {
        currentTime = startTime
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val remaining = endTime - currentTime
        when (msg.what) {
            TIMER_PAUSE ->{
                cancelWorkRequest() //
            }
            TIMER_RESET -> {
                cancelWorkRequest() //

                currentTime = startTime
                this.sendEmptyMessage(TIMER_PAUSE)
            }
            TIMER_START -> {
                enqueueTimerWorkRequest(remaining) //

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

    private fun enqueueTimerWorkRequest(second: Long) {

        val workData =
            Data.Builder()
            .putLong(WORKER_DATA_PARAM_TIME, second)
            .build()

        val workRequest =
            OneTimeWorkRequestBuilder<TimerWorker>()
            .setInputData(
                workData
            )
            .build()

        workManager.enqueue(workRequest)

    }
    private fun cancelWorkRequest() {
        workManager.cancelAllWork()
    }



    companion object {
        const val TIMER_PAUSE = 1000    // 일시 정지 상태
        const val TIMER_RESET = 1001    // 타이머를 다시 시작
        const val TIMER_START = 1002    // 타이머 시작
        const val TIMER_CONTINUE = 1003 // 타이머가 작동 중인 경우
        const val TIMER_STOP = 1004     // 타이머가 종료 된 경우
        // 유저는 이 액션으로 직접 브로드캐스트리시버를 등록하여 종료시에 시행될 작업을 처리할 수도 있음
        const val TIMER_BROADCAST_ACTION = "com.partnus.timer.TIMER_NOTIFICATION"
        const val WORKER_DATA_PARAM_TIME = "WORKER_DATA_PARAM_TIME"
    }
}