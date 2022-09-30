package com.example.tmcinsaat

import ProductListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmcinsaat.adapter.AdapterForBuyer
import com.example.tmcinsaat.model.Products
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_seller.*


class BuyerFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var prodlist=ArrayList<Products>()
    private  var productListAdapter= AdapterForBuyer(prodlist)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buyer, container, false)
    }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            auth= FirebaseAuth.getInstance()
            db= Firebase.firestore

            getdata()

            recyclerview.layoutManager= GridLayoutManager(context, 2)
            recyclerview.adapter=productListAdapter


        }

        private fun getdata() {
            db.collection("Products").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
                if(error!=null){
                    Toast.makeText(activity, error.localizedMessage, Toast.LENGTH_LONG).show()
                }else{
                    if(value !=null){
                        prodlist.clear()
                        if(!value.isEmpty){
                            val documents=value.documents
                            // prodlist.clear
                            for(document  in documents){
                                val name=document.get("productname") as String
                                val detail=document.get("description") as String
                                val price=document.get("price") as String
                                val image=document.get("downloadUrl") as String

                                val prod= Products(detail, name, price, image)
                                prodlist.add(prod)
                            }
                        }
                        productListAdapter.notifyDataSetChanged()
                    }
                }



            }


        }
}