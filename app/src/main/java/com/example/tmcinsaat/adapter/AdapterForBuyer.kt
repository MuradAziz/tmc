package com.example.tmcinsaat.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.tmcinsaat.R
import com.example.tmcinsaat.model.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item.view.*

class AdapterForBuyer( private val products: ArrayList<Products>):
    RecyclerView.Adapter<ProductListAdapter.PostHolder>() {

    class PostHolder2(val view: View): RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListAdapter.PostHolder {
        val inflate= LayoutInflater.from(parent.context)
        val view=inflate.inflate(R.layout.row_item_buyer, parent, false)
        return ProductListAdapter.PostHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductListAdapter.PostHolder, position: Int) {
        var navController: NavController?

        holder.itemView.productname.text=products[position].productname
        holder.itemView.productcost.text=products[position].productprice
        holder.itemView.productdescription.text=products[position].productDetail
        Picasso.get().load(products[position].downloadUrl).into(holder.itemView.productimage)

        val productName=products[position].productname
        val productPrice=products[position].productprice
        val productDetail=products[position].productDetail
        val productUrl=products[position].downloadUrl
        val bundle= Bundle()
        holder.itemView.setOnClickListener{
            bundle.putString("Pname", productName)
            bundle.putString("Pprice", productPrice)
            bundle.putString("Pdetail", productDetail)
            bundle.putString("Purl", productUrl)

            navController= Navigation.findNavController(it)
            navController!!.navigate(R.id.productDetailsFragment, bundle)

        }
    }
    fun updatelist(newlist: List<Products>){
        products.clear()
        products.addAll(newlist)
        notifyDataSetChanged()
    }

}
