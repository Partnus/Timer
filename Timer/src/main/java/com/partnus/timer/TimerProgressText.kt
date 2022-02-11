package com.partnus.timer

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

/**
 * xml 에서는 timerStyle 을 progressText 로 설정했을 때 만들어지는 View
 * {@link com.partnus.TimerableFactory} 에 {@link com.partnus.TimerType} 의 PROGRESS_TEXT 에 대응한다
 * progress text 형태로 timer 기능을 수행하는 Custom View
 */
class TimerProgressText (context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs),
    Timerable,
    Progressable {

    var layout: LinearLayout
    var timerText: TextView

    companion object {
        var initTime = 0L
        var currentTime = 0L
    }

    init {

        val view = View.inflate(context, R.layout.layout_progresstext, this)
        layout = view.findViewById(R.id.layout)
        timerText = view.findViewById(R.id.timerText)

        setInitTime(100) // default initTime
        setCurrentTime(0) // default current

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

            // set progress text size
            setProgressTextSize(
                typedArray.getFloat(
                    R.styleable.Timer_progressTextSize,
                    20.0F // default progress tint
                )
            )
        }

        invalidateProgress()
    }

    private fun invalidateProgress() {
        // currentTime, initTime 기반으로 progress text 상태 업데이트
        timerText.text = (currentTime * 100 / initTime.toDouble()).toString()
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
        timerText.setBackgroundColor(color)
    }

    override fun setProgressColor(color: Int) {
        timerText.setTextColor(color)
    }

    override fun setProgressTextSize(size: Float) {
        timerText.textSize = size
    }


}
