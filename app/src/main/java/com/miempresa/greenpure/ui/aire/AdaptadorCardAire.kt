package com.miempresa.greenpure.ui.aire

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.miempresa.greenpure.R

class AdaptadorCardAire(val ListaElementos: ArrayList<Elementos>): RecyclerView.Adapter<AdaptadorCardAire.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val fTemperatura = itemView.findViewById<TextView>(R.id.lblTemperatura)
        val fEstado = itemView.findViewById<TextView>(R.id.lblEstado)
        val fImagen = itemView.findViewById<ImageView>(R.id.imgEstado)
        val fCalidad = itemView.findViewById<TextView>(R.id.lblCalidad)
        val fCiudad = itemView.findViewById<TextView>(R.id.lblCiudad)
        val fDepartamento = itemView.findViewById<TextView>(R.id.lblDepartamento)
        val fDiaAyer = itemView.findViewById<TextView>(R.id.lblDiaAyer)
        val fDiaHoy = itemView.findViewById<TextView>(R.id.lblDiaHoy)
        val fDiaManana = itemView.findViewById<TextView>(R.id.lblDiaManana)
        val fCalidadDiaAyer = itemView.findViewById<TextView>(R.id.lblCalidadDiaAyer)
        val fCalidadDiaHoy = itemView.findViewById<TextView>(R.id.lblCalidadDiaHoy)
        val fCalidadDiaManana = itemView.findViewById<TextView>(R.id.lblCalidadDiaManana)
        val fFecha = itemView.findViewById<TextView>(R.id.lblUltimaActualizacion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCardAire.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementoslista, parent, false)
        return AdaptadorCardAire.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdaptadorCardAire.ViewHolder, position: Int) {
        holder?.fTemperatura?.text=ListaElementos[position].temperatura.toString()
        holder?.fEstado?.text= "Bueno"
        holder?.fCalidad?.text= 21.toString()
        holder?.fCiudad?.text=ListaElementos[position].lugar
        holder?.fDepartamento?.text=ListaElementos[position].lugar
        holder?.fFecha?.text=ListaElementos[position].fecha

        holder.itemView.setOnClickListener(){
            val intent = Intent(holder.itemView.context, InfoAire::class.java)
            intent.putExtra("id", ListaElementos[position].id.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ListaElementos.size
    }

}