package com.miempresa.greenpure.ui.aire

data class Elementos(
        val idDistrito: Int,
        val ciudad: String, val distrito: String,
        val humedad: Double, val temperatura: Double,
        val fecha: String,
        val hora: String,
        val calidadAVG: Int,
        val imagen: Int,
        val calidadEstado: String
)