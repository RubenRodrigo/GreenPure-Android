package com.miempresa.greenpure.ui.aire

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.miempresa.greenpure.R

class AdaptadorCardAire(val ListaElementos: ArrayList<Elementos>): RecyclerView.Adapter<AdaptadorCardAire.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val fTextoElemento = itemView.findViewById<TextView>(R.id.elemento_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCardAire.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementoslista, parent, false)
        return AdaptadorCardAire.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdaptadorCardAire.ViewHolder, position: Int) {
        holder?.fTextoElemento?.text=ListaElementos[position].txtElemento
        holder.itemView.setOnClickListener(){
            Toast.makeText(holder.itemView.context,
                "Pulsaste la posicion" + (position+1), Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return ListaElementos.size
    }

}