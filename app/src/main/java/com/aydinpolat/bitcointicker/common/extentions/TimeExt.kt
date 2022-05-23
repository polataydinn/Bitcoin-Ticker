package com.aydinpolat.bitcointicker.common.extentions

fun Int.secondsToMillis(): Long {
    return this.toLong() * 1000
}
