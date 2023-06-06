package com.xupt.safeAndRun.modulesCore.login.status

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.xupt.safeAndRun.modulesCore.login.mvvm.VerifyViewModel

class AnimatorController(private val viewModel: VerifyViewModel) {
    private var valueAnimator: ValueAnimator? = null

    fun start(){
        if(valueAnimator === null){
            valueAnimator = ValueAnimator.ofInt(VerifyViewModel.WAIT_TIME.toInt())
            valueAnimator?.interpolator = LinearInterpolator()
            viewModel.btClickable = false
            valueAnimator?.addUpdateListener{
                viewModel.timeLeft = (it.animatedValue as Int).toLong()
                viewModel.btString = ((VerifyViewModel.WAIT_TIME - it.animatedValue as Int) / 1000).toString()
            }
            valueAnimator?.addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    complete()
                }
            })
        }
        else{
            valueAnimator?.setIntValues(VerifyViewModel.WAIT_TIME.toInt(), 0)
        }
        valueAnimator?.duration = VerifyViewModel.WAIT_TIME
        valueAnimator?.start()
    }
    fun complete(){
        viewModel.btClickable = true
        viewModel.btString = "重新发送验证码"
    }

}