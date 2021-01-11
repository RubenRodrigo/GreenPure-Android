package com.miempresa.greenpure.ui.agregar.distritos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miempresa.greenpure.R

class AdaptadorDistrito(val listenerDistrito: OnClickListenerDistrito, val ListaDistrito: ArrayList<ElementosDistrito>): RecyclerView.Adapter<AdaptadorDistrito.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorDistrito.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(
                R.layout.elementosdistritos,
                parent,
                false
        )
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val fDistrito = itemView.findViewById<TextView>(R.id.txtDistrito)
        val fUbicacion = itemView.findViewById<TextView>(R.id.txtUbicacion)
        val fCalidad = itemView.findViewById<TextView>(R.id.txtCalidad)
    }

    override fun onBindViewHolder(holder: AdaptadorDistrito.ViewHolder, position: Int) {
        var id = ListaDistrito[position].id
        var distrito = ListaDistrito[position].distrito
        var ubicacion = ListaDistrito[position].ubicacion
        var calidad = ListaDistrito[position].calidad

        holder?.fDistrito?.text = distrito
        holder?.fUbicacion?.text = ubicacion
        holder?.fCalidad?.text = calidad

        holder.itemView.setOnClickListener{
            listenerDistrito.onItemClick(ElementosDistrito(id, distrito, ubicacion, calidad))
        }
    }

    override fun getItemCount(): Int {
        return ListaDistrito.size
    }

}