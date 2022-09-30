package com.example.tmcinsaat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_choose.*


class ChooseFragment : androidx.fragment.app.Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnbuyer.setOnClickListener {
            findNavController().navigate(R.id.action_chooseFragment_to_buyernewFragment)
        }
        btnseller.setOnClickListener{
            findNavController().navigate(R.id.action_chooseFragment_to_loginFragment)
        }
    }

}