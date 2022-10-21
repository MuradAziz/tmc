package com.example.tmcinsaat.ui

import ProductListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmcinsaat.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_seller.*
import kotlinx.android.synthetic.main.fragment_seller.recyclerview



class SellerFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var sellerViewModel: SellerViewModel
    lateinit var productListAdapter: ProductListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sellerViewModel= ViewModelProvider(this)[SellerViewModel::class.java]
        auth = FirebaseAuth.getInstance()
        sellerViewModel.getSellerdata()

        sellerViewModel.productsSeller.observe(viewLifecycleOwner, Observer { products->
            products.let{
                productListAdapter=ProductListAdapter(it)
                recyclerview.layoutManager = GridLayoutManager(context, 2)
                recyclerview.adapter = productListAdapter
            }

        })

        searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                productListAdapter.filter.filter(p0)
                return false
            }

        }

        )
        imgadd.setOnClickListener {
            findNavController().navigate(R.id.action_sellerFragment_to_forAddingFragment)
        }

        imglogout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_sellerFragment_to_chooseFragment)
        }
    }

}