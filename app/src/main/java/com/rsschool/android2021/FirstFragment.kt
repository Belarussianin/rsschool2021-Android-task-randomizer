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
import androidx.fragment.app.FragmentTransaction
import com.rsschool.android2021.SecondFragment.Companion.newInstance

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

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        var min = 0
        view.findViewById<EditText>(R.id.min_value).doAfterTextChanged {
            view.findViewById<EditText>(R.id.min_value).text.toString().toIntOrNull()?.let {
                min = it
            }
        }
        var max = 0
        view.findViewById<EditText>(R.id.max_value).doAfterTextChanged {
            view.findViewById<EditText>(R.id.max_value).text.toString().toIntOrNull()?.let {
                max = it
            }
        }

        generateButton?.setOnClickListener {
            val secondFragment: Fragment = newInstance(min, max)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, secondFragment)
            transaction.commit()
        }
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