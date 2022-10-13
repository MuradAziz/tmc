package com.example.tmcinsaat

import ProductListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmcinsaat.model.Products
import com.example.tmcinsaat.ui.ProductDetailsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_seller.*
import kotlinx.android.synthetic.main.fragment_seller.recyclerview
import kotlinx.android.synthetic.main.row_item.*


class SellerFragment : Fragment()  {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var prodlist=ArrayList<Products>()
    private  var productListAdapter= ProductListAdapter(prodlist)


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
        auth=FirebaseAuth.getInstance()
        db=Firebase.firestore

        getdata()

        recyclerview.layoutManager=GridLayoutManager(context, 2)
        recyclerview.adapter=productListAdapter

        searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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

        imglogout.setOnClickListener{
            auth.signOut()
        findNavController().navigate(R.id.action_sellerFragment_to_chooseFragment)
        }
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