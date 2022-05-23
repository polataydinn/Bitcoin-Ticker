package com.aydinpolat.bitcointicker.common

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.aydinpolat.bitcointicker.R

class SetTimerDialog constructor(
    private val activity: Activity,
) {
    private var dialog: AlertDialog? = null
    var listener: ((Int) -> Unit)? = null


    fun startTimerDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_set_time_dialog, null))
        builder.setCancelable(true)
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()

        val setTimeButton = dialog!!.findViewById<CardView>(R.id.set_time_button)
        val getTimeText = dialog!!.findViewById<EditText>(R.id.timer_text_input)

        setTimeButton.setOnClickListener {
            if (getTimeText.text.toString().isNotEmpty() && getTimeText.text.toString()
                    .isNumber()
            ) {
                listener?.invoke(getTimeText.text.toString().toInt())
                Toast.makeText(
                    activity,
                    activity.getString(R.string.set_time_success), Toast.LENGTH_SHORT
                ).show()
                dismissDialog()
            } else {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.timer_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }

}

fun String.isNumber(): Boolean {
    return this.matches(Regex("[0-9]+"))
}