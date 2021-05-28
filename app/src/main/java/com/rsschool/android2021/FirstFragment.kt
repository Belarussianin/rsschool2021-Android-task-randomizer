package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
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
        var min: Int? = null
        var max: Int? = null
        val minView = view.findViewById<EditText>(R.id.min_value)
        val maxView = view.findViewById<EditText>(R.id.max_value)
        previousResult?.text = resources.getString(
            R.string.previous_result_text,
            arguments?.getInt(PREVIOUS_RESULT_KEY)
        )

        view.setOnClickListener {
            hideKeyboard()
        }

        minView.doAfterTextChanged {
            minView.text.run {
                if (isEmpty()) {
                    min = null
                } else {
                    toString().toLongOrNull()?.let {
                        if (it > Int.MAX_VALUE) {
                            hideKeyboard()
                            snackMessage("Min num > Int.MAX_VALUE")
                            min = null
                            minView.setText("")
                        } else {
                            min = it.toInt()
                        }
                    }
                }
            }
        }

        maxView.doAfterTextChanged {
            maxView.text.run {
                if (isEmpty()) {
                    max = null
                } else {
                    toString().toLongOrNull()?.let {
                        if (it > Int.MAX_VALUE) {
                            hideKeyboard()
                            snackMessage("Max num > Int.MAX_VALUE")
                            max = null
                            maxView.setText("")
                        } else {
                            max = it.toInt()
                        }
                    }
                }
            }
        }

        generateButton?.setOnClickListener {
            hideKeyboard()
            when {
                min.isNull() -> snackMessage("min empty")
                max.isNull() -> snackMessage("max empty")
                min!! <= -1 -> snackMessage("min empty")
                max!! <= -1 -> snackMessage("max empty")
                min!! > max!! -> snackMessage("min > max")
                else -> mainActivity().openSecondFragment(min!!, max!!, parentFragmentManager)
            }
        }
    }

    private fun snackMessage(charSequence: CharSequence) = view?.let {
        hideKeyboard()
        Snackbar.make(it, charSequence, Snackbar.LENGTH_SHORT).apply {
            view.findViewById<TextView>(R.id.snackbar_text).apply {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                textSize = 20F
            }
            setAction("HIDE") {}
        }.show()
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int) = FirstFragment().apply {
            arguments = bundleOf(PREVIOUS_RESULT_KEY to previousResult)
        }

        const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}