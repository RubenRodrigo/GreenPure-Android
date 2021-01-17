package com.miempresa.greenpure.ui.agregar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.agregar.ciudades.Ciudades

class AgregarLugar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_lugar)

        val ciudad = Ciudades()

        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contenedor, ciudad)
        transaction.commit()
    }
}