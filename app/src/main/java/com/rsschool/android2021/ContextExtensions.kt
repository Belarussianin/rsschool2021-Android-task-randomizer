package com.rsschool.android2021

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() = view?.let { activity?.hideKeyboard(it) }

fun Fragment.mainActivity() = requireActivity() as MainActivity

fun Activity.hideKeyboard() = hideKeyboard(currentFocus ?: View(this))

fun Activity.something() {

}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

