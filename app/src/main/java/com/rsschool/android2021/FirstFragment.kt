package com.rsschool.android2021

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
//import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
//import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        enableActionBar(false)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        val minView = view.findViewById<TextInputEditText>(R.id.min_value)
        val maxView = view.findViewById<TextInputEditText>(R.id.max_value)
        val minLayout = view.findViewById<TextInputLayout>(R.id.min_value_layout)
        val maxLayout = view.findViewById<TextInputLayout>(R.id.max_value_layout)

        var min: Int? =
            if (!MainActivity.autoClearOption) if (MainActivity.min == 0) null else MainActivity.min else null
        var max: Int? =
            if (!MainActivity.autoClearOption) if (MainActivity.max == 0) null else MainActivity.max else null

        previousResult?.text = resources.getString(
            R.string.previous_result_text,
            arguments?.getInt(PREVIOUS_RESULT_KEY)
                ?: throw Exception("Null PREVIOUS value passed to the first fragment")
        )
        min?.let {
            minView.setText(it.toString())
        }
        max?.let {
            maxView.setText(it.toString())
        }

        view.setOnClickListener {
            hideKeyboard()
        }

        minView.doAfterTextChanged {
            minView.text?.run {
                if (isEmpty()) {
                    min = null
                } else {
                    toString().toLongOrNull()?.let {
                        if (it > Int.MAX_VALUE) {
                            minLayout.error = getString(R.string.min_more_int_max)
                            min = null
                            minView.clearText()
                        } else {
                            min = it.toInt()
                        }
                    }
                }
            }
        }

        maxView.doAfterTextChanged {
            maxView.text?.run {
                if (isEmpty()) {
                    max = null
                } else {
                    toString().toLongOrNull()?.let {
                        if (it > Int.MAX_VALUE) {
                            maxLayout.error = getString(R.string.max_more_int_max)
                            max = null
                            maxView.clearText()
                        } else {
                            max = it.toInt()
                        }
                    }
                }
            }
        }

        generateButton?.setOnClickListener {
            minLayout.clearError()
            when {
                min.isNull() -> minLayout.error = getString(R.string.min_empty)
                max.isNull() -> maxLayout.error = getString(R.string.max_empty)
                min!! <= -1 -> minLayout.error = getString(R.string.min_empty)
                max!! <= -1 -> maxLayout.error = getString(R.string.max_empty)
                min!! > max!! -> maxLayout.error = getString(R.string.min_more_max)
                MainActivity.equalityPossible == false && min == max -> minLayout.error = getString(R.string.min_equal_max)
                else -> {
                    hideKeyboard()
                    shitSmartCast().onGenerateButtonClick(min!!, max!!)
                    mainActivity().openSecondFragment(parentFragmentManager)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            //Restore AutoClearSetting state from static var
            R.id.menu_settingsFragment -> {
                item.subMenu.findItem(R.id.autoClearSettingFragment).isChecked =
                    MainActivity.autoClearOption
                item.subMenu.findItem(R.id.equalitySettingFragment).isChecked =
                    MainActivity.equalityPossible
                true
            }
            R.id.autoClearSettingFragment -> {
                item.isChecked = !item.isChecked
                MainActivity.autoClearOption = item.isChecked
                true
            }
            R.id.equalitySettingFragment -> {
                item.isChecked = !item.isChecked
                MainActivity.equalityPossible = item.isChecked
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    private fun snackMessage(@StringRes resourceID: Int) = view?.let {
//        hideKeyboard()
//        Snackbar.make(it, resourceID, Snackbar.LENGTH_SHORT).apply {
//            view.findViewById<TextView>(R.id.snackbar_text).apply {
//                textAlignment = View.TEXT_ALIGNMENT_CENTER
//                textSize = 20F
//            }
//            setAction(R.string.snackbar_message) {}
//        }.show()
//    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int) = FirstFragment().apply {
            arguments = bundleOf(PREVIOUS_RESULT_KEY to previousResult)
        }

        const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}