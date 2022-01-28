package com.partnus.timer

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ProgressBar

/**
 * xml 에서는 timerStyle 을 progressBar 로 설정했을 때 만들어지는 View
 * {@link com.partnus.TimerableFactory} 에 {@link com.partnus.TimerType} 의 PROGRESS_BAR 에 대응한다
 * progress bar 형태로 timer 기능을 수행하는 Custom View
 */
class TimerProgressBar(context: Context, attrs: AttributeSet) :
    ProgressBar(context, attrs, android.R.attr.progressBarStyleHorizontal),
    Timerable,
    Progressable {

    companion object {
        var initTime = 0L
        var currentTime = 0L
    }

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        setInitTime(100) // default initTime
        setCurrentTime(0) // default current
        progressDrawable = resources.getDrawable(R.drawable.layout_progressbar, null) // custom drawable 적용
        context.obtainStyledAttributes(attrs, R.styleable.Timer).also { typedArray ->
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

        invalidateProgress()
    }

    private fun invalidateProgress() {
        // currentTime, initTime 기반으로 progress 상태 업데이트
        progress = (currentTime * 100 / initTime.toDouble()).toInt()
    }

    override fun getCurrentTime(): Long = currentTime

    override fun getInitTime(): Long = initTime

    override fun setCurrentTime(timestamp: Long) {
        currentTime = timestamp
        invalidateProgress()
    }

    override fun setInitTime(timestamp: Long) {
        initTime = timestamp
        invalidateProgress()
    }

    override fun setProgressBackgroundColor(color: Int) {
        progressBackgroundTintList = ColorStateList.valueOf(color)
    }

    override fun setProgressColor(color: Int) {
        progressTintList = ColorStateList.valueOf(color)
    }
}
