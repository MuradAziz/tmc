package com.example.tmcinsaat.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tmcinsaat.R
import com.example.tmcinsaat.downloadUrl
import com.example.tmcinsaat.placeHolderDrawble
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.fragment_product_details.detailscost
import kotlinx.android.synthetic.main.fragment_product_details.detailsdescription
import kotlinx.android.synthetic.main.fragment_product_details.detailsname
import kotlinx.android.synthetic.main.fragment_product_details.detailstimage
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.row_item_buyer.view.*


class BlankFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameText = arguments?.getString("Pname")
        val priceText = arguments?.getString("Pprice")
        val detailText = arguments?.getString("Pdetail")
        val image = arguments?.getString("Purl")
        detailsname.text = "Product name: " + nameText
        detailscost.text = "Product price: " + priceText
        detailsdescription.text = "Product description: " + detailText
        context?.let { placeHolderDrawble(it) }?.let {
            if (image != null) {
                detailstimage.downloadUrl(image, it)
            }
        }
        //holder.view.productimagebuyer.downloadUrl(products[position].downloadUrl, placeHolderDrawble(holder.itemView.context))


        buttonOrder.setOnClickListener{

            val toNumber="+994506499297"
            val text="Məhsulun adi: "+nameText.toString()
            val miqdari="Məhsulun miqdari: "
            val unvan="Unvan: "


            val openWhatsappIntent = Intent(Intent.ACTION_VIEW)
            openWhatsappIntent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text+"\n"+ miqdari+"\n"+unvan))
            startActivity(openWhatsappIntent)
        }


    }
}

