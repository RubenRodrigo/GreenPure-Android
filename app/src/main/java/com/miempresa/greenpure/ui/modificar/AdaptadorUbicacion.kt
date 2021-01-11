package com.miempresa.greenpure.ui.modificar

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.miempresa.greenpure.MainActivity2
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.repositorio.Lugar
import com.miempresa.greenpure.ui.repositorio.LugarRepositorio

class AdaptadorUbicacion(val ListaUbicaciones: ArrayList<Lugar>): RecyclerView.Adapter<AdaptadorUbicacion.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorUbicacion.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(
                R.layout.elementosubicacion,
                parent,
                false
        )
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val fUbicacionDistrito = itemView.findViewById<TextView>(R.id.lblubicacionDistrito)
        val fUbicacionUbicacion = itemView.findViewById<TextView>(R.id.lblUbicacionUbicacion)
        val fUbicacionEliminar = itemView.findViewById<ImageView>(R.id.btnEliminar)
    }

    override fun onBindViewHolder(holder: AdaptadorUbicacion.ViewHolder, position: Int) {
        val lugar = this.ListaUbicaciones.get(position)
        var distrito = ListaUbicaciones[position].distrito
        var ubicacion = ListaUbicaciones[position].ubicacion
        var id = ListaUbicaciones[position].id
        holder?.fUbicacionDistrito?.text = distrito
        holder?.fUbicacionUbicacion?.text = ubicacion
        holder?.fUbicacionEliminar.setOnClickListener(){
            val builder: AlertDialog.Builder = AlertDialog.Builder(holder?.itemView.context)
            builder.setTitle("Confirmacion")
            builder.setMessage("¿Desea eliminar el registro?")
                    .setPositiveButton("Aceptar"){dialog, id ->
                        Toast.makeText(holder.itemView.context, "Se elimino "+distrito, Toast.LENGTH_SHORT).show()
                        var lugarrepo = LugarRepositorio()
                        lugarrepo.borrar(lugar.id!!)
                        val intent = Intent(holder?.itemView.context, MainActivity2::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        holder?.itemView.context.startActivity(intent)
                    }
                    .setNegativeButton("Cancelar"){dialog, id ->
                    }
            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return ListaUbicaciones.size
    }
}