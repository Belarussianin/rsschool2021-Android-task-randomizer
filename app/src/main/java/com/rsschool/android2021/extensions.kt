package com.rsschool.android2021

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import kotlin.random.Random
import kotlin.random.nextInt

fun Fragment.hideKeyboard() = view?.let { activity?.hideKeyboard(it) }

fun Fragment.mainActivity() = requireActivity() as MainActivity

fun Fragment.shitSmartCast() = requireActivity() as Shit

fun Fragment.enableActionBar(value: Boolean) {
    mainActivity().supportActionBar?.let {
        it.setDisplayHomeAsUpEnabled(value)
        it.setDisplayShowHomeEnabled(value);
    }
}

fun Activity.hideKeyboard() = hideKeyboard(currentFocus ?: View(this))

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Any?.isNull() = this == null

fun IntRange.random() = Random(System.currentTimeMillis()).nextInt(this)

fun TextInputEditText.clearText() = setText("")

fun TextInputLayout.clearError() {
    error = ""
}

private val apiKey = "a628d13d-cc05-46aa-a964-e4fff152836d"
//num=1&min=${min}&max=${max}&col=1&base=10&format=plain&rnd=new
val baseUrl = "https://www.random.org/integers/?"

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun get(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .build()
        }
        return retrofit!!
    }
}

interface RetrofitServices {
    @GET("")
    fun getRandomInt(): Call<MutableList<Int>>
}