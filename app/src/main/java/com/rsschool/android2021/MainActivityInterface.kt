package com.rsschool.android2021

import androidx.fragment.app.FragmentManager

interface MainActivityInterface {
    fun openFirstFragment(prevNum: Int, fragmentManager: FragmentManager)
    fun openSecondFragment(max: Int, min: Int, fragmentManager: FragmentManager)
    fun putIntPreference(key: String, value: Int)
    fun getIntPreference(key: String): Int

    companion object {
        const val FIRST_FRAGMENT = "PREVIOUS_RESULT"
    }
}