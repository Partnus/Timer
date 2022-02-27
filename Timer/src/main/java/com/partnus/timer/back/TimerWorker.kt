package com.partnus.timer.back

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Thread.sleep

class TimerWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        try {
            inputData.getLong(TimerHandler.WORKER_DATA_PARAM_TIME, 0).also { ms ->
                sleep(ms)
                Log.e("TAG_TIMER_THREAD", "타이머 끝")
                showToastDelayed() // TODO Notification 으로 교체
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    private fun showToastDelayed() {
        Handler(Looper.getMainLooper()).post() {
            Toast.makeText(applicationContext, "TimerWorker 호출됨", Toast.LENGTH_LONG).show()
        }
    }

    private fun createNotification() {

    }
}