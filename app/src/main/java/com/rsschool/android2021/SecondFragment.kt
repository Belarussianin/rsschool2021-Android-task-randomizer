package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment


class SecondFragment : Fragment() {
    private var backButton: Button? = null
    private var result: TextView? = null
    private lateinit var numListener: OnGenerateListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnGenerateListener) {
            numListener = context
        } else {
            throw ClassCastException(context.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY)
            ?: throw Exception("Null MIN value passed to the second fragment")
        val max = arguments?.getInt(MAX_VALUE_KEY)
            ?: throw Exception("Null MAX value passed to the second fragment")
        result?.text = generateRandom(min, max).toString()

        backButton?.setOnClickListener {
            mainActivity().openFirstFragment(parentFragmentManager)
        }
    }

    private fun generateRandom(min: Int, max: Int): Int {
        val random = (min..max).random()
        numListener.onGenerateRandom(random)
        return random
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int) = SecondFragment().apply {
            arguments = bundleOf(MIN_VALUE_KEY to min, MAX_VALUE_KEY to max)
        }

        const val MIN_VALUE_KEY = "MIN_VALUE"
        const val MAX_VALUE_KEY = "MAX_VALUE"

        interface OnGenerateListener {
            fun onGenerateRandom(random: Int)
        }
    }
}