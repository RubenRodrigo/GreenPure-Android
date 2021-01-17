package com.miempresa.greenpure.ui.aire

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.miempresa.greenpure.R
import kotlinx.android.synthetic.main.activity_info_aire.*

class AdaptadorCardAire(val ListaElementos: ArrayList<Elementos>): RecyclerView.Adapter<AdaptadorCardAire.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //val fTemperatura = itemView.findViewById<TextView>(R.id.lblTemperatura)
        val fCiudad = itemView.findViewById<TextView>(R.id.lblCiudad)
        val fDistrito = itemView.findViewById<TextView>(R.id.lblDistrito)
        val fHumedad = itemView.findViewById<TextView>(R.id.lblHumedadActualizado)
        val fTemperatura = itemView.findViewById<TextView>(R.id.lblTemperatura)
        val fHora = itemView.findViewById<TextView>(R.id.lblHora)
        val fCalidadAVG = itemView.findViewById<TextView>(R.id.lblCalidadAVG)
        val fEstadoCalidad = itemView.findViewById<TextView>(R.id.lblEstadoCalidad)
        val fImagenCalidad = itemView.findViewById<ImageView>(R.id.imgEstadoCalidad)
        val fContenedor = itemView.findViewById<LinearLayout>(R.id.contenedorResumen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCardAire.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementoslista, parent, false)
        return AdaptadorCardAire.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdaptadorCardAire.ViewHolder, position: Int) {
        holder?.fCiudad?.text= ListaElementos[position].ciudad
        holder?.fDistrito?.text= ListaElementos[position].distrito
        holder?.fHumedad?.text= ListaElementos[position].humedad.toString()
        holder?.fTemperatura?.text= ListaElementos[position].temperatura.toString()
        holder?.fHora?.text= ListaElementos[position].hora
        holder?.fCalidadAVG?.text = ListaElementos[position].calidadAVG.toString()
        holder?.fEstadoCalidad?.text = ListaElementos[position].calidadEstado
        holder?.fImagenCalidad?.setImageResource(ListaElementos[position].imagen)

        when(ListaElementos[position].calidadEstado){
            "Bueno" -> holder?.fContenedor.setBackgroundColor(ContextCompat.getColor(holder?.itemView.context, R.color.good))
            "Moderado" -> holder?.fContenedor.setBackgroundColor(ContextCompat.getColor(holder?.itemView.context, R.color.moderate))
            "Regular" -> holder?.fContenedor.setBackgroundColor(ContextCompat.getColor(holder?.itemView.context, R.color.regular))
            "Malo" -> holder?.fContenedor.setBackgroundColor(ContextCompat.getColor(holder?.itemView.context, R.color.bad))
        }

        holder.itemView.setOnClickListener(){
            val intent = Intent(holder.itemView.context, InfoAire::class.java)
            intent.putExtra("idDistrito", ListaElementos[position].idDistrito.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ListaElementos.size
    }


}