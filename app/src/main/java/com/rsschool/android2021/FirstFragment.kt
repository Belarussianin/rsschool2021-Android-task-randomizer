package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        previousResult?.text =
            "Previous result: ${arguments?.getInt(PREVIOUS_RESULT_KEY).toString()}"

        val mainActivity = MainActivity()
        var min = 0
        var max = 0

        view.findViewById<EditText>(R.id.min_value).doAfterTextChanged {
            val minView = view.findViewById<EditText>(R.id.min_value)
            minView.text.toString().toLongOrNull()?.let {
                when {
                    it > Int.MAX_VALUE -> {
                        hideKeyboard()
                        snackMessage("min > Int.MAX_VALUE")
                        minView.setText("")
                    }
                    else -> {
                        min = it.toInt()
                    }
                }
            }
        }
        view.findViewById<EditText>(R.id.max_value).doAfterTextChanged {
            val maxView = view.findViewById<EditText>(R.id.max_value)
            maxView.text.toString().toLongOrNull()?.let {
                when {
                    it > Int.MAX_VALUE -> {
                        hideKeyboard()
                        snackMessage("max > Int.MAX_VALUE")
                        maxView.setText("")
                    }
                    else -> {
                        max = it.toInt()
                    }
                }
            }
        }

        generateButton?.setOnClickListener {
            hideKeyboard()
            when {
                min <= 0 -> snackMessage("min empty")
                max <= 0 -> snackMessage("max empty")
                min > max -> snackMessage("min > max")
                else -> mainActivity.openSecondFragment(min, max, parentFragmentManager)
            }
        }
    }

    private fun snackMessage(charSequence: CharSequence) = view?.let {
        Snackbar.make(it, charSequence, Snackbar.LENGTH_SHORT).show()
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}