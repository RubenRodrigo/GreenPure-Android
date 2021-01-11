package com.miempresa.greenpure.ui.modificar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.repositorio.Lugar
import com.miempresa.greenpure.ui.repositorio.LugarRepositorio
import kotlinx.android.synthetic.main.activity_modificar_lugar.*

class ModificarLugar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_lugar)

        listaUbicaciones.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        listaUbicaciones.layoutManager = LinearLayoutManager(applicationContext)

        var lugarrepo = LugarRepositorio()
        var listaLugar = lugarrepo.listar()
        val adapter = AdaptadorUbicacion(listaLugar as ArrayList<Lugar>)
        listaUbicaciones.adapter = adapter
    }
}