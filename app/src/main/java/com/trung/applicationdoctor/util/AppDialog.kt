package com.trung.applicationdoctor.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.R

object AppDialog {
    var alertDialog : AlertDialog? = null

    private fun initDialog(context: Context?) {
        if (context != null) {
            if (alertDialog != null) {
                if (alertDialog!!.isShowing) {
                    return
                }
                alertDialog = null
            }
        }
    }

    fun showDialog(context: Context, message: String, positive: (() -> Unit)? = null) {
        initDialog(context)

        val alertDialogBuilder = AlertDialog.Builder(context, R.style.MyCustomAlertDialog)

        alertDialogBuilder.setMessage(message)

        alertDialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
            positive?.invoke()
        })
        alertDialogBuilder.setOnCancelListener{dialog ->
            dialog.dismiss()
            positive?.invoke()
        }
        alertDialogBuilder.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.dismiss()
                positive?.invoke()
            }
            true
        }
        alertDialog = alertDialogBuilder.create()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(context.getColor(R.color.white))
//            alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.background = context.resources.getDrawable(R.color.tealish)
//        }
        alertDialog?.show()
    }
}