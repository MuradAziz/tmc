package com.example.tmcinsaat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment() {

lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        btnsignupback.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        btnsignupSave.setOnClickListener {

            val email = etsignupusernmae.text.toString()
            val pasword = etsignuppassword.text.toString()
            val paswordagain = etsignuppasswordagain.text.toString()

            if (email.equals("") || pasword.equals("") || paswordagain.equals("")) {
                Toast.makeText(activity, "enter email or password", Toast.LENGTH_LONG).show()
            } else {
                if (pasword == paswordagain) {
                    auth.createUserWithEmailAndPassword(email, pasword).addOnSuccessListener {
                        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    }.addOnFailureListener {
                        Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(activity, "enter right password", Toast.LENGTH_LONG).show()
                }
            }


        }
    }
}

