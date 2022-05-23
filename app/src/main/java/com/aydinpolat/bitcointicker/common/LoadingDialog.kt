package com.aydinpolat.bitcointicker.common

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.aydinpolat.bitcointicker.R

class LoadingDialog constructor(private val activity: Activity) {
    private var dialog: AlertDialog? = null

    fun startLoadingDialog(){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    fun dismissDialog(){
        dialog?.dismiss()
    }

}