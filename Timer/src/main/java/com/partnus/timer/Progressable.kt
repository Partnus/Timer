package com.partnus.timer

/**
 * Progress 종류(progress bar, 원 모양 progress 등 )들이 상속받아서 구현할 수 있도록 만든 interface
 */
interface Progressable {
    fun setProgressBackgroundColor(colorRes: Int) // progressbar 배경색 설정
    fun setProgressColor(colorRes: Int) // progressbar 색 설정

    fun setProgressTextSize(size: Float) // progressText 텍스트 크기 설정
}
