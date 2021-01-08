package com.miempresa.greenpure.ui.aire

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.miempresa.greenpure.R
import org.json.JSONException

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
        listaLugares.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listaLugares.layoutManager = LinearLayoutManager(context)

        var llenarLista = ArrayList<Elementos>()

        AsyncTask.execute {
            val queue = Volley.newRequestQueue(activity)
            val url = getString(R.string.urlAPI) + "/dato/"
            val stringRequest = JsonArrayRequest(url,
                    { response ->
                        try {
                            for (i in 0 until response.length()) {
                                val id = response.getJSONObject(i).getString("id")
                                val ubicacion = response.getJSONObject(i).getString("Ubicacion")
                                val lugar = response.getJSONObject(i).getString("Lugar")
                                val humedad = response.getJSONObject(i).getString("Humedad")
                                val temperatura = response.getJSONObject(i).getString("Temperatura")
                                val calor = response.getJSONObject(i).getString("Calor")
                                val concentracion = response.getJSONObject(i).getString("Concentracion")
                                val sensorHumo = response.getJSONObject(i).getBoolean("SensorHumo")
                                val sensorMetano = response.getJSONObject(i).getBoolean("SensorMetano")
                                val fecha = response.getJSONObject(i).getString("fecha")
                                llenarLista.add(Elementos(
                                        id.toInt(),
                                        ubicacion, lugar,
                                        humedad.toDouble(), temperatura.toDouble(),
                                        calor.toDouble(), concentracion.toDouble(),
                                        sensorHumo, sensorMetano,
                                        fecha

                                ))
                            }

                            val adapter = AdaptadorCardAire(llenarLista)
                            listaLugares.adapter = adapter

                        } catch (e: JSONException) {
                            Toast.makeText(
                                    activity,
                                    "Error al obtener los datos",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }, {
                Toast.makeText(
                        activity,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                ).show()
            })
            queue.add(stringRequest)
        }

        return view
    }

    fun crearLista(): ArrayList<Elementos> {
        var llenarLista = ArrayList<Elementos>()

        AsyncTask.execute {
            val queue = Volley.newRequestQueue(activity)
            val url = getString(R.string.urlAPI) + "/dato/"
            val stringRequest = JsonArrayRequest(url,
                    { response ->
                        try {
                            for (i in 0 until response.length()) {
                                val id = response.getJSONObject(i).getString("id")
                                val ubicacion = response.getJSONObject(i).getString("Ubicacion")
                                val lugar = response.getJSONObject(i).getString("Lugar")
                                val humedad = response.getJSONObject(i).getString("Humedad")
                                val temperatura = response.getJSONObject(i).getString("Temperatura")
                                val calor = response.getJSONObject(i).getString("Calor")
                                val concentracion = response.getJSONObject(i).getString("Concentracion")
                                val sensorHumo = response.getJSONObject(i).getBoolean("SensorHumo")
                                val sensorMetano = response.getJSONObject(i).getBoolean("SensorMetano")
                                val fecha = response.getJSONObject(i).getString("fecha")
                                llenarLista.add(Elementos(
                                        id.toInt(),
                                        ubicacion, lugar,
                                        humedad.toDouble(), temperatura.toDouble(),
                                        calor.toDouble(), concentracion.toDouble(),
                                        sensorHumo, sensorMetano,
                                        fecha

                                ))
                            }

                        } catch (e: JSONException) {
                            Toast.makeText(
                                    activity,
                                    "Error al obtener los datos",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }, {
                Toast.makeText(
                        activity,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                ).show()
            })
            queue.add(stringRequest)
        }

        return llenarLista
    }

}