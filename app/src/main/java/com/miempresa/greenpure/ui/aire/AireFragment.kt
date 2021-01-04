package com.miempresa.greenpure.ui.aire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val view:View = inflater.inflate(R.layout.fragment_aire, container, false)

        var listaLugares: RecyclerView = view.findViewById(R.id.places_list)
        listaLugares.layoutManager = LinearLayoutManager(context)

        var llenarLista = ArrayList<Elementos>()

        llenarLista.add(
                Elementos("Icono")
        )
        llenarLista.add(
                Elementos("Icono")
        )
        llenarLista.add(
                Elementos("Icono")
        )
        llenarLista.add(
                Elementos("Icono")
                )

        val adapter = AdaptadorCardAire(llenarLista)
        listaLugares.adapter = adapter

        return view
    }
}