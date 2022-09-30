package com.example.tmcinsaat.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmcinsaat.R
import com.example.tmcinsaat.adapter.AdapterForBuyer
import kotlinx.android.synthetic.main.fragment_seller.*

class BuyernewFragment : Fragment() {
    private val countryAdapter = AdapterForBuyer(arrayListOf())


    private lateinit var viewModel: BuyernewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buyernew, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BuyernewViewModel::class.java)
        viewModel.getdata()

        recyclerview.layoutManager=GridLayoutManager(context,2)
        recyclerview.adapter=countryAdapter

        obserLivedata()

    }

    private fun obserLivedata() {
        viewModel.product.observe(viewLifecycleOwner, Observer{ product->
            product?.let {
                countryAdapter.updatelist(product)
            }
        })
    }

}