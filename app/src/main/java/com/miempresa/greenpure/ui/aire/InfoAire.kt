package com.miempresa.greenpure.ui.aire

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.miempresa.greenpure.R
import kotlinx.android.synthetic.main.activity_info_aire.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class InfoAire : AppCompatActivity(){

    var listaHoras = ArrayList<String>()
    val listaCalidad = ArrayList<Entry>()
    val listaHumedad = ArrayList<Entry>()
    val listaTemperatura = ArrayList<Entry>()
    val listaCalor = ArrayList<Entry>()
    val listaConcentracion = ArrayList<Entry>()

    val MY_DEFAULT_TIMEOUT = 15000

    // AIR QUALITY CONSTANTS
    val GOOD_AIR = 30
    val MODERATE_AIR = 50
    val REGULAR_AIR = 70
    var CALIDAD_ESTADO = "NaN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_aire)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            var idDistrito = bundle.getString("idDistrito").toString()
            var URL: String = getString(R.string.urlAPI) + "/distritoDatos/"+idDistrito
            obtenerData(URL)
        }
    }

    fun obtenerData(URL: String){
        val queue = Volley.newRequestQueue(applicationContext)
        val stringRequest = JsonObjectRequest(Request.Method.GET, URL, null,
                { response ->
                    try {
                        returnData(response)
                        //progressBarAire.visibility = View.GONE

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
        stringRequest.setRetryPolicy(DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))
        queue.add(stringRequest)
    }


    fun returnData(response: JSONObject) {
        // Data for View
        var ciudad: String = response.getString("ciudadNombre")
        var nombre: String = response.getString("nombre")
        var calidadAVG: String = response.getString("calidadAVG")
        var calidadIMG = calidadAireIMG(calidadAVG.toInt())
        infoDistrito.text = nombre
        infoCiudadPais.text = "Perú, $ciudad"
        infoCalidad.text = calidadAVG
        infoCalidadImagen.setImageResource(calidadIMG)
        infoCalidadEstado.text = CALIDAD_ESTADO
        when (CALIDAD_ESTADO){
            "Bueno" -> infoCalidadContenedor.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.good))
            "Moderado" -> infoCalidadContenedor.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.moderate))
            "Regular" -> infoCalidadContenedor.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.regular))
            "Malo" -> infoCalidadContenedor.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.bad))
        }

        // Data for LineChart
        val datos: JSONArray = response.getJSONArray("datos")
        for (i in 0 until datos.length()){
            var hora = datos.getJSONObject(i).getString("hora")
            val calidad = datos.getJSONObject(i).getString("calidad").toFloat()
            val humedad = datos.getJSONObject(i).getString("humedad").toFloat()
            val temperatura = datos.getJSONObject(i).getString("temperatura").toFloat()
            val calor = datos.getJSONObject(i).getString("calor").toFloat()
            val concentracion = datos.getJSONObject(i).getString("concentracion").toFloat()
            listaHoras.add(hora)
            listaCalidad.add(Entry(i.toFloat(), calidad))
            listaHumedad.add(Entry(i.toFloat(), humedad))
            listaTemperatura.add(Entry(i.toFloat(), temperatura))
            listaCalor.add(Entry(i.toFloat(), calor))
            listaConcentracion.add(Entry(i.toFloat(), concentracion))
        }

        var linearChartCalidad: LineChart = findViewById(R.id.infoLinearChartCalidad)
        var lineDataCalidad = getDataSet(LineDataSet(listaCalidad, "Calidad"))
        createChart("Calidad", lineDataCalidad, linearChartCalidad)

        var linearChartHumedad: LineChart = findViewById(R.id.infoLinearChartHumedad)
        var lineDataHumedad = getDataSet(LineDataSet(listaHumedad, "Humedad"))
        createChart("Humedad", lineDataHumedad, linearChartHumedad)

        var linearChartTemperatura: LineChart = findViewById(R.id.infoLinearChartTemperatura)
        var lineDataTemperatura = getDataSet(LineDataSet(listaTemperatura, "Temperatura"))
        createChart("Temperatura", lineDataTemperatura, linearChartTemperatura)

        /*
        var linearChartCalor: LineChart = findViewById(R.id.infoLinearChartCalor)
        var lineDataCalor = getDataSet(LineDataSet(listaTemperatura, "Calor"))
        createChart("Calor", lineDataCalor, linearChartCalor)
         */

        var linearChartConcentracion: LineChart = findViewById(R.id.infoLinearChartConcentracion)
        var lineDataConcentracion = getDataSet(LineDataSet(listaConcentracion, "Polvo"))
        createChart("Concentacion", lineDataConcentracion, linearChartConcentracion)
    }

    private fun createChart(description: String, lineData: LineData, linearChart: LineChart){
        linearChart.animateY(1000)
        linearChart.description.text = description

        axisX(linearChart.xAxis)
        axisLeft(linearChart.axisLeft)
        axisRight(linearChart.axisRight)

        linearChart.data = lineData
    }

    private fun getDataSet(dataSet: LineDataSet): LineData{
        dataSet.setDrawCircles(false)
        dataSet.valueTextSize = 0f
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        return LineData(dataSet)
    }

    /*
    private fun legend(legend: Legend){
        legend.form = Legend.LegendForm.CIRCLE
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        var entries = ArrayList<LegendEntry>()
        for (i in 0 until listaHoras.size){
            var entry = LegendEntry()
            entry.label=listaHoras[i]
            entries.add(entry)
        }
        legend.setCustom(entries)
    }
    */

    private fun axisX(axis: XAxis){
        axis.isGranularityEnabled = true
        axis.position = XAxis.XAxisPosition.BOTTOM
        axis.valueFormatter = IndexAxisValueFormatter(listaHoras)
        axis.spaceMax = 1f
        axis.spaceMin = 1f
    }

    private fun axisLeft(axis: YAxis){
        axis.spaceTop = 30F
        axis.axisMinimum = 0f
    }

    private fun axisRight(axis: YAxis){
        axis.isEnabled = false
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
                CALIDAD_ESTADO = "Malo"
                return R.drawable.ic_bad_air
            }
            else -> return R.drawable.ic_good_air
        }
        return R.drawable.ic_good_air
    }


}