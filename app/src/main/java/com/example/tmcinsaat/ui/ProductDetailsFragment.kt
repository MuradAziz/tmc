package com.example.tmcinsaat.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tmcinsaat.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_product_details.*


class ProductDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val nameText=arguments?.getString("Pname")
            val priceText=arguments?.getString("Pprice")
            val detailText=arguments?.getString("Pdetail")
            val image=arguments?.getString("Purl")
            detailsname.text="Product name: "+nameText
            detailscost.text="Product price: "+ priceText
            detailsdescription.text="Product description: "+detailText
            Picasso.get().load(image).into(detailstimage)


    }
}