package com.example.tmcinsaat.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmcinsaat.R
import com.example.tmcinsaat.adapter.AdapterForBuyer
import com.example.tmcinsaat.model.Products
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_seller.recyclerview
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private lateinit var buyerAdapter : AdapterForBuyer
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getdata()
        viewModel.products.observe(viewLifecycleOwner, Observer{ product->
            product?.let {
                buyerAdapter= AdapterForBuyer(product)
                recyclerview.layoutManager = GridLayoutManager(context, 2)
            }
            recyclerview.adapter=buyerAdapter
        })



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                buyerAdapter.filter.filter(p0)
                return false
            }
        }
        )

    }


}