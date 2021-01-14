package com.miempresa.greenpure.ui.aire

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.agregar.AgregarLugar
import com.miempresa.greenpure.ui.modificar.ModificarLugar
import org.json.JSONArray
import org.json.JSONException


class AireFragment : Fragment(){

    lateinit var listaLugares: RecyclerView

    val GOOD_AIR = 30
    val MODERATE_AIR = 50
    val REGULAR_AIR = 70
    var CALIDAD_ESTADO = "NaN"
    lateinit var progressBarAire: ProgressBar
    val MY_DEFAULT_TIMEOUT = 15000

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater.inflate(R.layout.fragment_aire, container, false)

        listaLugares = view.findViewById(R.id.places_list)
        var btnAgregar: Button = view.findViewById(R.id.btnAgregar)
        var btnModificar: Button = view.findViewById(R.id.btnModificar)
        progressBarAire = view.findViewById(R.id.progressBarAire)

        listaLugares.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listaLugares.layoutManager = LinearLayoutManager(context)


        //AireAsyncTask(this, context, progresBarAire).execute("http://54.157.137.57/resumen")

        //crearLista(llenarLista, listaLugares, progresBarAire)

        // Buttom to call Agregar activity
        btnAgregar.setOnClickListener{
            val intent = Intent(context, AgregarLugar::class.java)
            startActivity(intent)
        }

        // Buttom to call Modificar activity
        btnModificar.setOnClickListener{
            val intent = Intent(context, ModificarLugar::class.java)
            startActivity(intent)
        }

        val url = context?.resources?.getString(R.string.urlAPI) + "/resumen/"
        obtenerData(url)

        return view
    }

    fun returnData(response: JSONArray) {
        var llenarLista = ArrayList<Elementos>()
        for (i in 0 until response.length()) {
            val dato = response.getJSONObject(i)
            val id = dato.getString("id")
            val ubicacion = dato.getString("ciudad")
            val lugar = "Cerro Colorado"
            val humedad = "15616"
            val temperatura = "16165"
            val calor = "464"
            val concentracion = "4654"
            val sensorHumo = true
            val sensorMetano = false
            val fecha = dato.getString("fecha")
            val calidad = 60
            llenarLista.add(Elementos(
                    id.toInt(),
                    ubicacion, lugar,
                    humedad.toDouble(), temperatura.toDouble(),
                    calor.toDouble(), concentracion.toDouble(),
                    sensorHumo, sensorMetano,
                    fecha,
                    calidadAireIMG(calidad),
                    CALIDAD_ESTADO,
                    calidad
            ))
        }
        val adapter = AdaptadorCardAire(llenarLista)
        listaLugares.adapter = adapter
    }

    fun obtenerData(URL: String){
        progressBarAire.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(context)
        val stringRequest = JsonArrayRequest(URL,
                { response ->
                    try {

                        returnData(response)
                        progressBarAire.visibility = View.GONE

                    } catch (e: JSONException) {
                        Toast.makeText(
                                context,
                                "Error al obtener los datos",
                                Toast.LENGTH_LONG
                        ).show()
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

    fun calidadAireIMG(calidad: Int): Int {
        when{
            calidad in 0..GOOD_AIR -> {
                CALIDAD_ESTADO = "Bueno"
                return R.drawable.ic_good_air
            }
            calidad in GOOD_AIR..MODERATE_AIR -> {
                CALIDAD_ESTADO = "Moderado"
                return R.drawable.ic_moderate_air
            }
            calidad in MODERATE_AIR..REGULAR_AIR -> {
                CALIDAD_ESTADO = "Regular"
                return R.drawable.ic_regular_air
            }
            calidad > REGULAR_AIR -> {
                CALIDAD_ESTADO = "MALA"
                return R.drawable.ic_bad_air
            }
            else -> return R.drawable.ic_good_air
        }
        return R.drawable.ic_good_air
    }

}