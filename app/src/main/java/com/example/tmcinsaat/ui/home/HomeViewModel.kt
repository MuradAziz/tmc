package com.example.tmcinsaat.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmcinsaat.model.Products
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeViewModel : ViewModel() {

    private lateinit var db: FirebaseFirestore
    var products = MutableLiveData<ArrayList<Products>>()
    var productList=ArrayList<Products>()



    fun getdata() {
        db = Firebase.firestore
        db.collection("Products").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d("error", "error")
                } else {
                    if (value != null) {
                        productList.clear()
                        if (!value.isEmpty) {
                            val documents = value.documents
                            for (document in documents) {
                                val name = document.get("productname") as String
                                val detail = document.get("description") as String
                                val price = document.get("price") as String
                                val image = document.get("downloadUrl") as String
                                val prod = Products(detail, name, price, image)
                                productList.add(prod)
                                products.value=productList
                            }
                        }
                    }
                }


            }
    }
}