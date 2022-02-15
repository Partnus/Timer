package com.partnus.timer.front

/**
 * Timer 기능을 하는 모든 종류의 객체들이 상속받아서 개발할 수 있도록 만들어진 interface
 */
interface Timerable {
    fun setInitTime(timestamp: Long)
    fun setCurrentTime(timestamp: Long)
    fun getInitTime(): Long
    fun getCurrentTime(): Long
}
