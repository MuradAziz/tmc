package com.example.tmcinsaat.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmcinsaat.model.Products
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SellerViewModel:ViewModel (){
    private lateinit var db: FirebaseFirestore
    val productsSeller=MutableLiveData<ArrayList<Products>>()
    private var prodlist=ArrayList<Products>()

    fun getSellerdata(){
        db = Firebase.firestore
        db.collection("Products").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error!=null){
                    Log.d("error", "error")
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
                            productsSeller.value=prodlist
                        }
                    }
                }
            }






    }
    }
}