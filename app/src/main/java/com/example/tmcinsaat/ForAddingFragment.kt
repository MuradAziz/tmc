package com.example.tmcinsaat

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_for_adding.*
import kotlinx.android.synthetic.main.fragment_seller.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.sql.Timestamp
import java.time.LocalDate.now
import java.util.*
import kotlin.collections.HashMap


class ForAddingFragment : Fragment() {
    val IMAGE_REQUEST_CODE=100
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var selectedpicture: Uri?=null
    lateinit var auth:FirebaseAuth
    lateinit var firestore:FirebaseFirestore
    lateinit var storage:FirebaseStorage

   /* var productname=etproductname.text.toString()
    var price=etprice.text.toString()
    var description=etdescription.text.toString()
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_for_adding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register()
        auth=FirebaseAuth.getInstance()
        firestore= Firebase.firestore
        storage=Firebase.storage
        btnUploadImage.setOnClickListener{
            upload()
        }


        btnchooseimage.setOnClickListener {
      val intenttogallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intenttogallery)
        }
    }
    private fun upload() {
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val reference = storage.reference
        val imageReference = reference.child("images").child(imageName)

        if (selectedpicture != null || etproductname.equals("") || etprice.equals("") || etdescription.equals("")) {
            imageReference.putFile(selectedpicture!!).addOnSuccessListener {
                val uploadpicture = storage.reference.child("images").child(imageName)
                uploadpicture.downloadUrl.addOnSuccessListener { it ->
                    val downloadUrl = it.toString()

                    val postMap = HashMap<String, Any>()
                    postMap["downloadUrl"] = downloadUrl
                    postMap["usermail"] = auth.currentUser!!.email!!
                    postMap.put("productname", etproductname.text.toString())
                    postMap.put("price", etprice.text.toString())
                    postMap.put("description", etdescription.text.toString())
                    postMap.put("date", com.google.firebase.Timestamp.now())


                        firestore.collection("Products").add(postMap).addOnSuccessListener {
                            findNavController().navigate(R.id.action_forAddingFragment_to_sellerFragment)
                        }.addOnFailureListener {
                            Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
                        }

                }.addOnFailureListener {
                    Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Toast.makeText(activity, "You must fill the field", Toast.LENGTH_LONG).show()

        }
    }

   private fun register(){
       activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
           if(result.resultCode== RESULT_OK){
               val intentFromResult=result.data
               if(intentFromResult!=null){
                  selectedpicture= intentFromResult.data
                   selectedpicture?.let {
                       chooseImage.setImageURI(it)
                   }
               }
           }

       }
    }
}