package com.partnus.timer.back

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.work.Worker
import androidx.work.WorkerParameters

class TimerWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        try {
            inputData.getString(TimerHandler.WORKER_DATA_PARAM_TIME)?.toLong()?.let { ms ->
                showToastDelayed(ms) // TODO Toast 를 Notification 코드로 바꾸기
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }


    }
    private fun showToastDelayed(ms: Long) {
        Handler(Looper.getMainLooper()).postDelayed(ms) {
            Toast.makeText(applicationContext, "TimerWorker 호출됨", Toast.LENGTH_LONG).show()
        }
    }
}