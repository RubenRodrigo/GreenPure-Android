package com.miempresa.greenpure.ui.agregar.distritos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miempresa.greenpure.MainActivity2
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.repositorio.LugarRepositorio

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Distritos.newInstance] factory method to
 * create an instance of this fragment.
 */
class Distritos : Fragment(), OnClickListenerDistrito {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =
                inflater.inflate(R.layout.fragment_distritos, container, false)
        var listaDistrito: RecyclerView = view.findViewById(R.id.listaDistritos)
        listaDistrito.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listaDistrito.layoutManager = LinearLayoutManager(context)

        var llenarLista = ArrayList<ElementosDistrito>()
        llenarLista.add(ElementosDistrito(1, "Cerro Colorado", "Arequipa, Peru", "25"))
        llenarLista.add(ElementosDistrito(2, "Arequipa", "Arequipa, Peru", "28"))
        llenarLista.add(ElementosDistrito(3, "Selva Alegre", "Arequipa, Peru", "20"))
        llenarLista.add(ElementosDistrito(4, "Cayma", "Arequipa, Peru", "20"))

        val adapter = AdaptadorDistrito(this, llenarLista)
        listaDistrito.adapter = adapter

        var lugarRepositorio = LugarRepositorio()
        var listaLugares = lugarRepositorio.listar()

        if (listaLugares.isNotEmpty()){
            for (i in 0 until listaLugares.size){
                println("Ubicaciones: "+listaLugares[i].distrito)
            }
        }

        if(arguments != null){
            val recibidoIdCiudad = requireArguments().getString("idCiudad")
            val recibidoCiudad = requireArguments().getString("ciudad")
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Distritos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Distritos().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onItemClick(elemento: ElementosDistrito) {
        var idUbicacion = elemento.id
        var ubicacion = elemento.ubicacion
        var distrito = elemento.distrito

        var lugarrepo = LugarRepositorio()

        if (lugarrepo.findByIdUbicacion(idUbicacion.toString())){
            Toast.makeText(context, "El distrito "+distrito+" ya existe", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Se añadio el distrito "+distrito, Toast.LENGTH_SHORT).show()

            lugarrepo.crear(idUbicacion.toLong(), ubicacion, distrito)
            val intent = Intent(context, MainActivity2::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
    }
}