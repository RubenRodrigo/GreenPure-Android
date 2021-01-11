package com.miempresa.greenpure.ui.repositorio

import com.orm.SugarRecord

class LugarRepositorio {
    fun crear(idUbicacion:Long, ubicacion: String, distrito: String){
        var lugar = Lugar(idUbicacion, ubicacion, distrito)
        SugarRecord.save(lugar)
    }

    fun listar(): List<Lugar>{
        var lugar = SugarRecord.listAll(Lugar::class.java)
        return lugar
    }

    fun findByIdUbicacion(idUbicacion: String): Boolean{
        print(idUbicacion)
        var lugar = SugarRecord.find(Lugar::class.java, "ID_UBICACION = ?", idUbicacion)
        if (lugar.size >= 1){
            return true
        }
        return false
    }

    fun borrar(id: Long){
        var lugar = SugarRecord.findById(Lugar::class.java, id)
        SugarRecord.delete(lugar)
    }

}