package com.paysky.momogrow.utilis

import android.os.CountDownTimer

abstract class CountUpTimer(private val millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval) {
    abstract fun onTick2(second: Int)

    override fun onTick(p0: Long) {
        onTick2(((millisInFuture - p0) / 1000).toInt())
    }

    override fun onFinish() {
        onTick(millisInFuture / 1000);
    }
}