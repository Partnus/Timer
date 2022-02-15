package com.partnus.timer.front

import android.content.Context
import android.util.AttributeSet

/**
 * {@link com.partnus.TimerType} 값에 따라 {@link com.partnus.Timerable} 상속받는 객체들을 제공하는 Factory
 */
class TimerableFactory {
    fun getTimerableView(context: Context, attrs: AttributeSet, type: TimerType): Timerable {
        return when (type) {
            TimerType.PROGRESS_BAR -> {
                TimerProgressBar(context, attrs)
            }
            TimerType.PROGRESS_TEXT -> {
                TimerProgressText(context, attrs)
            }
//            TimerType.PROGRESS_RING -> {
//                null
//            }
        }
    }
}
