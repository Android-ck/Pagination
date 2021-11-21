package com.zerir.networking.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Notify{

    private var snackBar: Snackbar? = null

    fun showSnackBar(message: String, view: View, time: Int) {
        snackBar?.dismiss()

        //validate time
        //Snackbar.LENGTH_INDEFINITE is not allowed here
        val t = if(time in Snackbar.LENGTH_SHORT..Snackbar.LENGTH_LONG) time else Snackbar.LENGTH_LONG

        snackBar = Snackbar.make(view, message, t)
        snackBar?.show()
    }

    fun showSnackBar(message: String, view: View, time: Int, actionName: String, action: () -> Unit) {
        snackBar?.dismiss()

        //validate time
        val t = if(time in Snackbar.LENGTH_INDEFINITE..Snackbar.LENGTH_LONG) time else Snackbar.LENGTH_INDEFINITE

        snackBar = Snackbar.make(view, message, t)
        snackBar?.setAction(actionName) {
            action()
            snackBar?.dismiss()
        }
        snackBar?.setAction(actionName) {
            action()
            snackBar?.dismiss()
        }
        snackBar?.show()
    }

}