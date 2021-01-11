package com.miempresa.greenpure.ui.repositorio

import com.orm.dsl.Table

@Table
data class Lugar(
    var id: Long? = null,
    var idUbicacion: Long? = null,
    var ubicacion: String? = null,
    var distrito: String? = null){
    constructor(idUbicacion: Long?, ubicacion: String?, distrito: String?) : this() {
            this.idUbicacion = idUbicacion
            this.ubicacion = ubicacion
            this.distrito = distrito
    }
}