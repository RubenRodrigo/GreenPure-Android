package com.miempresa.greenpure.ui.map

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson
import com.miempresa.greenpure.R


class CustomInfoWindowAdapter(private val inflater: LayoutInflater) : InfoWindowAdapter {
    override fun getInfoWindow(m: Marker): View {
        //Carga layout personalizado.
        val v: View = inflater.inflate(R.layout.info_place, null)
        val lblLugarInfo = v.findViewById<TextView>(R.id.lblLugarInfo)
        val lblEstadoInfo = v.findViewById<TextView>(R.id.lblEstadoInfo)
        val lblTemperaturaInfo = v.findViewById<TextView>(R.id.lblTemperaturaInfo)
        val gson = Gson()
        val (lugarInfo, estadoInfo, temperaturaInfo) = gson.fromJson(
            m.getSnippet(),
            MarkerInfo::class.java
        )
        lblLugarInfo.setText(lugarInfo)
        lblEstadoInfo.setText(estadoInfo)
        lblTemperaturaInfo.setText(temperaturaInfo)
        return v
    }

    override fun getInfoContents(m: Marker): View? {
        return null
    }

    companion object {
        private const val TAG = "CustomInfoWindowAdapter"
    }
}