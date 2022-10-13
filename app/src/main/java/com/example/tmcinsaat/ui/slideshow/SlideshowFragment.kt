package com.example.tmcinsaat.ui.slideshow

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tmcinsaat.R
import kotlinx.android.synthetic.main.fragment_slideshow.*


class SlideshowFragment : Fragment() {
        val myLatitude:Double=40.439112
        val myLongitude:Double=50.093874
        val labelLocation:String="TMC @Bina"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_slideshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textMap.setOnClickListener {
            val urlAddress = "http://maps.google.com/maps?q=$myLatitude,$myLongitude($labelLocation)&iwloc=A&hl=es"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress))
            startActivity(intent)
        }
    }
}
