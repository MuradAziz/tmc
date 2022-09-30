package com.example.tmcinsaat.ui

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmcinsaat.model.Products
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BuyernewViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    val product = MutableLiveData<List<Products>>()


    fun getdata() {
        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        db.collection("Products").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d("error", "error")
                } else {
                    if (value != null) {

                        if (!value.isEmpty) {
                            val documents = value.documents
                            // prodlist.clear
                            for (document in documents) {
                                val name = document.get("productname") as String
                                val detail = document.get("description") as String
                                val price = document.get("price") as String
                                val image = document.get("downloadUrl") as String

                                val prod = Products(detail, name, price, image)
                                product.value= arrayListOf(prod)
                            }
                        }
                    }
                }


            }
    }
}
