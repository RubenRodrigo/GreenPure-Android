package com.miempresa.greenpure.ui.aire

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.Utils
import com.miempresa.greenpure.R
import kotlinx.android.synthetic.main.activity_info_aire.*
import org.json.JSONException

class InfoAire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_aire)
        val bundle: Bundle ? = intent.extras
        if (bundle!=null) {
            var id = bundle.getString("id").toString()
            cargarData(id)
        }
        val visitors: ArrayList<Entry> = ArrayList()
        visitors.add(Entry(2015f, 10f))
        visitors.add(Entry(2016f, 12f))
        visitors.add(Entry(2017f, 14f))
        visitors.add(Entry(2018f, 15f))
        visitors.add(Entry(2019f, 16f))
        visitors.add(Entry(2020f, 13f))
        visitors.add(Entry(2021f, 17f))
        visitors.add(Entry(2022f, 14f))
        visitors.add(Entry(2023f, 16f))
        visitors.add(Entry(2024f, 18f))
        visitors.add(Entry(2025f, 15f))
        visitors.add(Entry(2026f, 13f))

        /*var linearDataSet = LineDataSet(visitors, "Visitors")
        linearDataSet.setValueTextColor(Color.BLACK)
        linearDataSet.valueTextSize = 16f
        linearDataSet.setDrawHighlightIndicators(false)
        linearDataSet.setDrawCircles(false)
        linearDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER*/

        var linearDataSet = LineDataSet(visitors, "")
        linearDataSet.setDrawCircles(false)
        linearDataSet.setValueTextSize(0f)
        linearDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        /*linearDataSet.setDrawFilled(true)
        if(Utils.getSDKInt() >= 18){
            val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(
                    ContextCompat.getColor(this, R.color.color1),
                    ContextCompat.getColor(this, R.color.color2),
                    ContextCompat.getColor(this, R.color.color3),
                    ContextCompat.getColor(this, R.color.color4),
                    ContextCompat.getColor(this, R.color.color5)
            )
            )
            linearDataSet.setFillDrawable(gradientDrawable)
        }*/

        val linearData = LineData(linearDataSet)

        infoLinearChartCalidad.setData(linearData)
        infoLinearChartCalidad.animateY(1000)

        infoLinearChartTemperatura.setData(linearData)
        infoLinearChartTemperatura.animateY(1000)

        infoLinearChartConcentracion.setData(linearData)
        infoLinearChartConcentracion.animateY(1000)

        infoLinearChartHumedad.setData(linearData)
        infoLinearChartHumedad.animateY(1000)

        infoLinearChartHumo.setData(linearData)
        infoLinearChartHumo.animateY(1000)

        infoLinearChartMetano.setData(linearData)
        infoLinearChartMetano.animateY(1000)

    }

    fun crearGrafica(){

    }

    fun cargarData(id: String){
        AsyncTask.execute{
            val queue = Volley.newRequestQueue(applicationContext)
            var url = getString(R.string.urlAPI)
            url += "/dato/"+id
            println("---------------- ----------------")
            Log.i("URL",url)
            val stringRequest = JsonObjectRequest(Request.Method.GET, url, null,
                    { response ->
                        try {
                            Log.i("URL","---------------- ----------------")
                            infoDistrito.setText(response.getString("Lugar"))
                            infoCiudadPais.setText(response.getString("Lugar"))
                            infoCalidadEstado.setText("Bueno")
                            /*lugar = response.getJSONObject(i).getString("Lugar")
                            humedad = response.getJSONObject(i).getString("Humedad")
                            temperatura = response.getJSONObject(i).getString("Temperatura")
                            calor = response.getJSONObject(i).getString("Calor")
                            concentracion = response.getJSONObject(i).getString("Concentracion")
                            sensorHumo = response.getJSONObject(i).getBoolean("SensorHumo")
                            sensorMetano = response.getJSONObject(i).getBoolean("SensorMetano")
                            fecha = response.getJSONObject(i).getString("fecha")*/
                        } catch (e: JSONException) {
                            Toast.makeText(
                                    this,
                                    "Error al obtener los datos",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }, Response.ErrorListener{error ->
                Toast.makeText(
                        this,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                ).show()
                println(error)
            })
            queue.add(stringRequest)
        }
    }

}