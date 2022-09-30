package com.example.tmcinsaat

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
        lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_sellerFragment)
        }

        btnsignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        forgotPassword.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle("Forgot Password")
            val view: View = layoutInflater.inflate(R.layout.for_forgot_password, null)
            val username = view.findViewById<EditText>(R.id.forgot_username)
            builder.setView(view)
            builder.setPositiveButton("Reset") { _, _ ->
                forgotPassword(username)
            }
            builder.setNegativeButton("close") { _, _ -> }
            builder.show()

        }

        btnlogin.setOnClickListener {
            var email = etgiriskullaniciadi.text.toString()
            var pasword = etgirisparol.text.toString()
            if (email.equals("") || pasword.equals("")) {
                Toast.makeText(activity, "email needed!", Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(email, pasword).addOnSuccessListener {
                    findNavController().navigate(R.id.action_loginFragment_to_sellerFragment)
                }.addOnFailureListener {
                    Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }

        }
        }

    private fun forgotPassword(girismail: EditText) {
        TextUtils.isEmpty(girismail?.text.toString())
        auth.sendPasswordResetEmail(girismail?.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "email sent", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity, "bad", Toast.LENGTH_LONG).show()
                }
            }
    }
}
//https://github.com/MuradAziz/TMCinsaatnew/tree/master