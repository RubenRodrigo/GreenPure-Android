package com.miempresa.greenpure.ui.aire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.miempresa.greenpure.R

class AireFragment : Fragment() {

    private lateinit var aireViewModel: AireViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        aireViewModel =
                ViewModelProvider(this).get(AireViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_aire, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        aireViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}