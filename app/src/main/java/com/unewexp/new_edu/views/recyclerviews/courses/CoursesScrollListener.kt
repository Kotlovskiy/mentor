package com.unewexp.new_edu.views.recyclerviews.courses

import android.os.CountDownTimer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

typealias lymbdaDown = () -> Unit
typealias lymbdaUp = () -> Unit

class CoursesScrollListener(val lymbdaDown: lymbdaDown, val lymbdaUp: lymbdaUp): RecyclerView.OnScrollListener(){
    //false - Scrolled Downwards, true - Scrolled Upwards
    var directionUp = false
    var run_timer = false

    var itemPosition = 0
    var isScroll = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
            isScroll = true
        }else if(newState == RecyclerView.SCROLL_STATE_SETTLING){
            isScroll = true
        }else{
            isScroll = false
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        when {
            dy > 0 -> directionUp = false
            dy < 0 -> directionUp = true
        }
        itemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (run_timer){
            var timer = object: CountDownTimer(2000, 1) {
                override fun onTick(millisUntilFinished: Long) {
                    recyclerView.stopScroll()
                }

                override fun onFinish() {
                    run_timer = false
                }
            }
        }

        if (!directionUp and isScroll and (itemPosition == 0)) {
            run_timer = true
            lymbdaDown.invoke()
        }
        if (directionUp and isScroll and (itemPosition == 0)){
            run_timer = true
            lymbdaUp.invoke()
        }

    }
}
