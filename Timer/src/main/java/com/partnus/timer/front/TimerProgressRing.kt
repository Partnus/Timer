package com.partnus.timer.front

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RotateDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
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

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        progressRing = findViewById(R.id.progressRing)
        context.obtainStyledAttributes(attrs, R.styleable.Timer).also { typedArray ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        progressRing?.progress = (currentTime * 100 / initTime.toDouble()).toInt()
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
        progressRing?.background?.also { drawable ->
            val shapeDrawable = drawable as GradientDrawable
            shapeDrawable.setColor(color)
        }
    }

    override fun setProgressColor(color: Int) {
        progressRing?.progressDrawable?.also { drawable ->
            val shapeDrawable = drawable as RotateDrawable
            shapeDrawable.setTintList(ColorStateList.valueOf(color))
        }
    }

    override fun setProgressTextSize(size: Float) {
        // progress ring 은 현재 text가 없지만, text 로 표현하는 기능이 추가될 것을 대비하여 생성
    }
}