package com.miempresa.greenpure.ui.agregar.distritos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.miempresa.greenpure.MainActivity2
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.repositorio.LugarRepositorio
import org.json.JSONException
import org.json.JSONObject

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

    private var param1: String? = null
    private var param2: String? = null

    lateinit var listaDistrito: RecyclerView
    val MY_DEFAULT_TIMEOUT = 15000

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
        var ciudadDistrito: TextView = view.findViewById(R.id.lblCiudadAgregar)
        listaDistrito = view.findViewById(R.id.listaDistritos)
        listaDistrito.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listaDistrito.layoutManager = LinearLayoutManager(context)

        //val adapter = AdaptadorDistrito(this, llenarLista)
        //listaDistrito.adapter = adapter

        if(arguments != null){
            val recibidoIdCiudad = requireArguments().getString("idCiudad")
            val recibidoCiudad = requireArguments().getString("ciudad")
            var URL: String = getString(R.string.urlAPI) + "/ciudad/"+recibidoIdCiudad
            ciudadDistrito.text = "Ciudad: $recibidoCiudad"
            getData(URL)
        }

        return view
    }

    fun getData(URL: String){
        val queue = Volley.newRequestQueue(context)
        val stringRequest = JsonObjectRequest(Request.Method.GET, URL, null,
                { response ->
                    try {

                        returnData(response)
                        //progressBarAire.visibility = View.GONE

                    } catch (e: JSONException) {
                        Toast.makeText(
                                context,
                                "Error al obtener los datos",
                                Toast.LENGTH_LONG
                        ).show()
                        e.printStackTrace()
                    }
                }, {
            println(it)
            Toast.makeText(
                    context,
                    "Verifique que esta conectado a internet",
                    Toast.LENGTH_LONG
            ).show()
        })

        stringRequest.setRetryPolicy(DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))

        queue.add(stringRequest)
    }

    fun returnData(response: JSONObject){
        var llenarLista = ArrayList<ElementosDistrito>()
        var distritos = response.getJSONArray("distritos")

        for (i in 0 until distritos.length()) {
            val distrito: JSONObject = distritos[i] as JSONObject
            var id = distrito.getString("idDistrito").toInt()
            var nombre = distrito.getString("nombre")
            var ciudadNombre = distrito.getString("ciudadNombre")
            var calidad = distrito.getString("calidad")
            llenarLista.add(ElementosDistrito(id, nombre, "Peru, $ciudadNombre", calidad))
        }

        val adapter = AdaptadorDistrito( this, llenarLista)
        listaDistrito.adapter = adapter

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