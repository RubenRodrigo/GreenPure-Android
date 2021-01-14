package com.miempresa.greenpure.ui.map

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson
import com.miempresa.greenpure.R


class CustomInfoWindowAdapter(private val inflater: LayoutInflater) : InfoWindowAdapter {
    override fun getInfoWindow(m: Marker): View {
        //Carga layout personalizado.
        val v: View = inflater.inflate(R.layout.info_place, null)
        val contenedorInfo = v.findViewById<LinearLayout>(R.id.contenedorInfo)

        val lblDistritoInfo = v.findViewById<TextView>(R.id.lblDistritoInfo)
        val lblEstadoInfo = v.findViewById<TextView>(R.id.lblEstadoInfo)
        //val lblTemperaturaInfo = v.findViewById<TextView>(R.id.lblTemperaturaInfo)
        val lblCalidadInfo = v.findViewById<TextView>(R.id.lblCalidadInfo)
        val imgEstado = v.findViewById<ImageView>(R.id.imgEstadoInfo)
        val gson = Gson()
        val (distritoInfo, estadoInfo, calidadInfo, temperaturaInfo) = gson.fromJson(
            m.getSnippet(),
            MarkerInfo::class.java
        )

        when(estadoInfo){
            "Bueno" -> {
                imgEstado.setImageResource(R.drawable.ic_good_air)
                contenedorInfo.setBackgroundColor(ContextCompat.getColor(v.context,R.color.good))
            }
            "Moderado" -> {
                imgEstado.setImageResource(R.drawable.ic_moderate_air)
                contenedorInfo.setBackgroundColor(ContextCompat.getColor(v.context,R.color.moderate))
            }
            "Regular" -> {
                imgEstado.setImageResource(R.drawable.ic_regular_air)
                contenedorInfo.setBackgroundColor(ContextCompat.getColor(v.context,R.color.regular))
            }
            "Mala" -> {
                imgEstado.setImageResource(R.drawable.ic_bad_air)
                contenedorInfo.setBackgroundColor(ContextCompat.getColor(v.context,R.color.bad))
            }
        }
        lblDistritoInfo.setText(distritoInfo)
        lblEstadoInfo.setText(estadoInfo)
        lblCalidadInfo.setText(calidadInfo)
        //lblTemperaturaInfo.setText(temperaturaInfo)
        return v
    }

    override fun getInfoContents(m: Marker): View? {
        return null
    }

    companion object {
        private const val TAG = "CustomInfoWindowAdapter"
    }
}