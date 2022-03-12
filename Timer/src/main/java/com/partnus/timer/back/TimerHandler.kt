package com.partnus.timer.back

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.work.*

/**/
class TimerHandler(
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

    // WorkManager
    private val workManager = WorkManager.getInstance(context)

    init {
        currentTime = startTime
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val remaining = endTime - currentTime
        if(msg.what != TIMER_CONTINUE) removeMessages(TIMER_CONTINUE)
        when (msg.what) {
            TIMER_PAUSE ->{
                cancelWorkRequest() // Pause 에 WorkRequest 취소
            }
            TIMER_RESET -> {
                cancelWorkRequest() // Reset 에 WorkRequest 취소

                currentTime = startTime
                this.sendEmptyMessage(TIMER_PAUSE)
            }
            TIMER_START -> {
                enqueueTimerWorkRequest(remaining) // Start 에 WorkRequest 를 WorkManager 에 enqueue
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
        workManager.cancelAllWork() // TODO id 나 tag 로 특정지어 취소하는 코드로 변경 예정
    }

    companion object {
        const val TIMER_PAUSE = 1000    // 일시 정지 상태
        const val TIMER_RESET = 1001    // 타이머를 다시 시작
        const val TIMER_START = 1002    // 타이머 시작
        const val TIMER_CONTINUE = 1003 // 타이머가 작동 중인 경우
        const val TIMER_STOP = 1004     // 타이머가 종료 된 경우
        const val WORKER_DATA_PARAM_TIME = "WORKER_DATA_PARAM_TIME"
    }
}