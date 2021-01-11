package com.miempresa.greenpure.ui.agregar.ciudades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.agregar.distritos.Distritos

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Ciudades.newInstance] factory method to
 * create an instance of this fragment.
 */
class Ciudades : Fragment(), OnClickListenerCiudad {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View
                = inflater.inflate(R.layout.fragment_ciudades, container, false)
        var listaCiudad: RecyclerView = view.findViewById(R.id.listaCiudades)

        listaCiudad.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listaCiudad.layoutManager = LinearLayoutManager(context)

        var llenarLista = ArrayList<ElementosCiudad>()
        llenarLista.add(ElementosCiudad(1, "Arequipa"))
        llenarLista.add(ElementosCiudad(2, "Puno"))
        llenarLista.add(ElementosCiudad(3, "Lima"))

        val adapter = AdaptadorCiudad( this, llenarLista)
        listaCiudad.adapter = adapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Ciudades.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Ciudades().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(elemento: ElementosCiudad) {

        val distrito = Distritos()
        val args = Bundle()
        args.putString("idCiudad", elemento.idCiudad.toString())
        args.putString("ciudad", elemento.ciudad)
        distrito.arguments = args
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contenedor, distrito)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(context, "Estas en el metodo onResume", Toast.LENGTH_SHORT).show()
    }
}