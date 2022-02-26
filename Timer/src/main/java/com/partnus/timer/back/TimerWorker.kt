package com.partnus.timer.back

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class TimerWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        showToast()
        return Result.success()
    }
    private fun showToast() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "TimerWorker 호출됨", Toast.LENGTH_LONG).show()
        }
    }
}