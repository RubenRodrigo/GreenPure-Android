package com.miempresa.greenpure.ui.aire

import org.json.JSONArray

interface AireListener {
    fun returnData(response: JSONArray)
    //fun returnData(llenarLista: String)
}