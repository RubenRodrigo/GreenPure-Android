package com.miempresa.greenpure.ui.agregar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.agregar.ciudades.Ciudades

class AgregarLugar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_lugar)

        val ciudad = Ciudades()

        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contenedor, ciudad)
        transaction.commit()

        /*var llenarLista = ArrayList<ElementosCiudad>()
        llenarLista.add(ElementosCiudad(1, "Arequipa"))
        llenarLista.add(ElementosCiudad(2, "Puno"))
        llenarLista.add(ElementosCiudad(3, "Lima"))

        val adapter = AdaptadorCiudad(llenarLista)*/


        /*AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
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

                        }

                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }*/

    }
}