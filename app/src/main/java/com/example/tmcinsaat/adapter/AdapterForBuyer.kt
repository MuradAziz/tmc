package com.example.tmcinsaat.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.tmcinsaat.R
import com.example.tmcinsaat.downloadUrl
import com.example.tmcinsaat.model.Products
import com.example.tmcinsaat.placeHolderDrawble
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item.view.*
import kotlinx.android.synthetic.main.row_item.view.productcost
import kotlinx.android.synthetic.main.row_item.view.productdescription
import kotlinx.android.synthetic.main.row_item.view.productname
import kotlinx.android.synthetic.main.row_item_buyer.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterForBuyer( private val products: ArrayList<Products>):
    RecyclerView.Adapter<AdapterForBuyer.PostHolder2>(), Filterable {
    var countryFilterList = ArrayList<Products>()
    init {
        countryFilterList = products
    }

    class PostHolder2(val view: View): RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder2 {
        val inflate= LayoutInflater.from(parent.context)
        val view=inflate.inflate(R.layout.row_item_buyer, parent, false)
        return PostHolder2(view)
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val charSearch = constraint.toString()
                    if (charSearch.isEmpty()) {
                        countryFilterList = products
                    } else {
                        val resultList = ArrayList<Products>()
                        for (row in products) {
                            if (row.productname.lowercase(Locale.ROOT)
                                    .contains(charSearch.lowercase(Locale.ROOT))
                            ) {
                                resultList.add(row)
                            }
                        }
                        countryFilterList = resultList
                    }
                    val filterResults = FilterResults()
                    filterResults.values = countryFilterList
                    return filterResults
                }

                @Suppress("UNCHECKED_CAST")
                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    countryFilterList = results?.values as ArrayList<Products>
                    notifyDataSetChanged()
                }

            }
        }

    override fun onBindViewHolder(holder: PostHolder2, position: Int) {
        var navController: NavController?

        holder.itemView.productname.text="Product name: "+countryFilterList[position].productname
        holder.itemView.productname.text="Product name: "+products[position].productname
        holder.itemView.productcost.text="Product price: "+products[position].productprice
        holder.itemView.productdescription.text="Product description: "+products[position].productDetail
        holder.view.productimagebuyer.downloadUrl(products[position].downloadUrl, placeHolderDrawble(holder.itemView.context))


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
            navController!!.navigate(R.id.blankFragment, bundle)

        }
    }
}


