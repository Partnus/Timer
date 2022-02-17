package com.partnus.timer.front

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.partnus.timer.R

class TimerProgressRing(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs),
    Timerable,
    Progressable {

    var progressRing:ProgressBar? = null

    companion object {
        var initTime = 0L
        var currentTime = 0L
    }

    init {
        inflate(context, R.layout.progress_ring, this) // xml init

        setInitTime(100) // default initTime
        setCurrentTime(0) // default current

        layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 0)

        progressRing = findViewById<ProgressBar>(R.id.progressRing).apply {
            context.obtainStyledAttributes(attrs, R.styleable.Timer).also { typedArray ->
                // Todo : custom drawable 을 변경하는 방법은 없을까?
                // set progress background color
                setProgressBackgroundColor(
                    typedArray.getInt(
                        R.styleable.Timer_progressBackgroundTint,
                        resources.getColor(R.color.gray, null) // default progress background tint
                    )
                )

                // set progress color
                setProgressColor(
                    typedArray.getInt(
                        R.styleable.Timer_progressTint,
                        resources.getColor(R.color.teal_200, null) // default progress tint
                    )
                )
            }
        }

        invalidateProgress()
    }

    private fun invalidateProgress() {
        // currentTime, initTime 기반으로 progress 상태 업데이트
        progressRing?.progress = (TimerProgressBar.currentTime * 100 / TimerProgressBar.initTime.toDouble()).toInt()
    }

    override fun getCurrentTime(): Long = TimerProgressBar.currentTime

    override fun getInitTime(): Long = TimerProgressBar.initTime

    override fun setCurrentTime(timestamp: Long) {
        TimerProgressBar.currentTime = timestamp
        invalidateProgress()
    }

    override fun setInitTime(timestamp: Long) {
        TimerProgressBar.initTime = timestamp
        invalidateProgress()
    }

    override fun setProgressBackgroundColor(color: Int) {
        // Todo : custom drawable 을 변경하는 방법은 없을까?
        progressRing?.progressBackgroundTintList = ColorStateList.valueOf(color)
    }

    override fun setProgressColor(color: Int) {
        // Todo : custom drawable 을 변경하는 방법은 없을까?
        progressRing?.progressTintList = ColorStateList.valueOf(color)
    }
}