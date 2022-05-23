package com.aydinpolat.bitcointicker.common.extentions

import android.app.Activity


fun Activity.hideKeyboard() {
    val inputManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
    if (currentFocus != null) {
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

