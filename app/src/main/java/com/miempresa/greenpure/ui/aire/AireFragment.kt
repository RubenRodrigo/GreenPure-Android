package com.miempresa.greenpure.ui.aire

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.agregar.AgregarLugar
import com.miempresa.greenpure.ui.modificar.ModificarLugar
import org.json.JSONException

class AireFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater.inflate(R.layout.fragment_aire, container, false)

        var listaLugares: RecyclerView = view.findViewById(R.id.places_list)
        var btnAgregar: Button = view.findViewById(R.id.btnAgregar)
        var btnModificar: Button = view.findViewById(R.id.btnModificar)

        listaLugares.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listaLugares.layoutManager = LinearLayoutManager(context)

        var llenarLista = ArrayList<Elementos>()

        crearLista(llenarLista, listaLugares)

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

        return view
    }

    // Function to call data from API
    fun crearLista(llenarLista: ArrayList<Elementos>, listaLugares: RecyclerView){
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(activity)
            val url = getString(R.string.urlAPI) + "/dato/"
            val stringRequest = JsonArrayRequest(url,
                    { response ->
                        try {
                            for (i in 0 until response.length()) {
                                val id = response.getJSONObject(i).getString("id")
                                val ubicacion = "Arequipa"
                                val lugar = "Cerro Colorado"
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
                                        fecha,
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
    }
}