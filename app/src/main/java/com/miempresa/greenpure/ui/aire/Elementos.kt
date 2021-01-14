package com.miempresa.greenpure.ui.aire

data class Elementos(
        val id: Int,
        val ubicacion: String, val lugar: String,
        val humedad: Double, val temperatura: Double,
        val calor: Double, val concentracion: Double,
        val sensorHumo: Boolean, val sensorMetano: Boolean,
        val fecha: String,
        val imagen: Int,
        val calidadEstado: String,
        val calidad: Int
)