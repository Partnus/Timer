package com.partnus.timer.front

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.partnus.timer.R
import com.partnus.timer.back.TimerThread
import com.partnus.timer.databinding.PartnusTimerBinding

/**
 ** Timer **
 * {@link com.partnus.TimerType} 에 따라 {@link com.partnus.TimerableFactory} 에서 {@link com.partnus.Timerable}
 * 을 상속받는 객체들을 제공할 수 있도록 하는 메인 함수이다.
 */
class Timer(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs), Timerable {
    companion object {
        lateinit var binding: PartnusTimerBinding
        lateinit var timerType: TimerType // timer 종류
        lateinit var timer: Timerable // timer 객체
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.partnus_timer, this)
        binding = PartnusTimerBinding.bind(view)
        context.obtainStyledAttributes(attrs, R.styleable.Timer).also { typedArray ->
            // timerType enum 값 가져오기
            val timerTypeIdx = typedArray.getInt(R.styleable.Timer_timerStyle, 0)
            timerType = TimerType.values()[timerTypeIdx]
        }

        // TimerableFactory 를 통해 Timerable 객체 가져오기
        timer = TimerableFactory().getTimerableView(context, attrs, timerType)
        binding.timerContainer.addView(timer as View, 0)
    }

    override fun setInitTime(timestamp: Long) {
        timer.setInitTime(timestamp)
    }

    override fun setCurrentTime(timestamp: Long) {
        timer.setCurrentTime(timestamp)
    }

    override fun getInitTime(): Long = timer.getInitTime()

    override fun getCurrentTime(): Long = timer.getCurrentTime()

    fun start() {
        TimerThread(
            context = context,
            startTime = 0,
            endTime = timer.getInitTime(),
            currentTimeChangeFun = { currentTime, timerThread ->
                timer.setCurrentTime(currentTime)
            }
        ).apply {
            makeThread()
            startThread()
        }
    }
}
