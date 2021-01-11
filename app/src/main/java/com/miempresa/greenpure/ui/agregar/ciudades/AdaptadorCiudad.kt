package com.miempresa.greenpure.ui.agregar.ciudades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miempresa.greenpure.R


class AdaptadorCiudad(val listenerCiudad: OnClickListenerCiudad, val ListaCiudad: ArrayList<ElementosCiudad>): RecyclerView.Adapter<AdaptadorCiudad.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(
                R.layout.elementosciudades,
                parent,
                false
        )
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val fCiudad = itemView.findViewById<TextView>(R.id.txtCiudad)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var idCiudad = ListaCiudad[position].idCiudad
        var ciudad = ListaCiudad[position].ciudad.toString()
        holder?.fCiudad?.text = ciudad

        holder.itemView.setOnClickListener{
            listenerCiudad.onItemClick(ElementosCiudad(idCiudad, ciudad))
        }
    }

    override fun getItemCount(): Int {
        return ListaCiudad.size
    }

}