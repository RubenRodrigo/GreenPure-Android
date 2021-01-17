package com.miempresa.greenpure.ui.aire

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.agregar.AgregarLugar
import com.miempresa.greenpure.ui.modificar.ModificarLugar
import com.miempresa.greenpure.ui.repositorio.LugarRepositorio
import org.json.JSONArray
import org.json.JSONException

class AireFragment : Fragment(){

    lateinit var listaLugares: RecyclerView

    val GOOD_AIR = 30
    val MODERATE_AIR = 50
    val REGULAR_AIR = 70
    var CALIDAD_ESTADO = "NaN"
    lateinit var progressBarAire: ProgressBar
    val MY_DEFAULT_TIMEOUT = 15000
    lateinit var navbar: BottomNavigationView
    lateinit var fragment: Fragment
    lateinit var container: ConstraintLayout

    private val TAG = "MyTag"
    lateinit var queue: RequestQueue
    lateinit var stringRequest: JsonArrayRequest

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater.inflate(R.layout.fragment_aire, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaLugares = view.findViewById(R.id.places_list)
        var btnAgregar: Button = view.findViewById(R.id.btnAgregar)
        var btnModificar: Button = view.findViewById(R.id.btnModificar)
        progressBarAire = view.findViewById(R.id.progressBarAire)

        listaLugares.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listaLugares.layoutManager = LinearLayoutManager(context)

        // Buttom to call Agregar activity
        btnAgregar.setOnClickListener{
            val intent = Intent(context, AgregarLugar::class.java)
            startActivity(intent)
        }

        // Buttom to call Modificar activity
        btnModificar.setOnClickListener{
            val intent = Intent(context, ModificarLugar::class.java)
            startActivity(intent)
        }

        var urlParams = savedData()
        println(urlParams)
        val url = getString(R.string.urlAPI) +"/distritos"+ urlParams
        getData(url)

    }


    fun getData(URL: String){
        progressBarAire.visibility = View.VISIBLE
        queue = Volley.newRequestQueue(context)
        stringRequest = JsonArrayRequest(URL,
                { response ->
                    try {
                        println("Termino el request")
                        setData(response, isFirstTime())
                        progressBarAire.visibility = View.GONE

                    } catch (e: JSONException) {
                        Toast.makeText(
                                context,
                                "Error al obtener los datos",
                                Toast.LENGTH_LONG
                        ).show()
                        e.printStackTrace()
                    }
                }, {
            it.printStackTrace()
            Toast.makeText(
                    context,
                    "Verifique que esta conectado a internet",
                    Toast.LENGTH_LONG
            ).show()
        })
        stringRequest.tag = TAG
        stringRequest.setRetryPolicy(DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))

        queue.add(stringRequest)
    }

    fun setData(response: JSONArray, firstTime: Boolean) {
        var llenarLista = ArrayList<Elementos>()
        if (firstTime){
            println("Primera ejecucion")
            for (i in 0 until response.length()) {
                val dato = response.getJSONObject(i)
                val idUbicacion = dato.getString("idDistrito")
                val ubicacion = "Peru," + dato.getString("ciudad")
                val distrito = dato.getString("distrito")
                setDefaultData(idUbicacion.toInt(), ubicacion, distrito)
            }
        }
        for (i in 0 until response.length()) {
            val dato = response.getJSONObject(i)
            val idDistrito = dato.getString("idDistrito")
            val ciudad = dato.getString("ciudad")
            val distrito = dato.getString("distrito")
            val humedad = dato.getString("humedad")
            val temperatura = dato.getString("temperatura")
            val fecha = dato.getString("fecha")
            val hora = dato.getString("hora")
            val calidadAVG = dato.getString("calidadAVG").toInt()
            llenarLista.add(Elementos(
                    idDistrito.toInt(),
                    ciudad, distrito,
                    humedad.toDouble(), temperatura.toDouble(),
                    fecha, hora,
                    calidadAVG,
                    calidadAireIMG(calidadAVG),
                    CALIDAD_ESTADO,
            ))
        }

        /*
        navbar = activity?.findViewById(R.id.nav_view)!!
        navbar?.visibility = View.VISIBLE

        fragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment)!!
        fragment?.view?.visibility = View.VISIBLE

        container = activity?.findViewById(R.id.container)!!
        container?.setBackgroundResource(0)
        */
        val adapter = AdaptadorCardAire(llenarLista)
        listaLugares.adapter = adapter
    }
    fun calidadAireIMG(calidad: Int): Int {
        when{
            calidad in 0..GOOD_AIR -> {
                CALIDAD_ESTADO = "Bueno"
                return R.drawable.ic_good_air
            }
            calidad in GOOD_AIR..MODERATE_AIR -> {
                CALIDAD_ESTADO = "Moderado"
                return R.drawable.ic_moderate_air
            }
            calidad in MODERATE_AIR..REGULAR_AIR -> {
                CALIDAD_ESTADO = "Regular"
                return R.drawable.ic_regular_air
            }
            calidad > REGULAR_AIR -> {
                CALIDAD_ESTADO = "MALA"
                return R.drawable.ic_bad_air
            }
            else -> return R.drawable.ic_good_air
        }
        return R.drawable.ic_good_air
    }

    fun savedData(): String {
        var builder = Uri.Builder()
        var lugarRepositorio = LugarRepositorio()
        var ubicaciones = lugarRepositorio.listar()
        for (i in 0 until ubicaciones.size){
            builder.appendQueryParameter("id_distrito"+(i+1), ubicaciones[i].idUbicacion.toString())
        }
        return builder.build().toString()
    }

    fun isFirstTime(): Boolean{
        val prefs: SharedPreferences? = activity?.getPreferences(AppCompatActivity.MODE_PRIVATE)

        if (prefs != null) {
            if (!prefs.getBoolean("firstTime", false)){
                Toast.makeText(context, "Primera Ejecucion", Toast.LENGTH_SHORT).show()
                val editor: SharedPreferences.Editor = prefs.edit()
                editor.putBoolean("firstTime", true)
                editor.commit()
                return true
            }
        }
        return  false
    }

    fun setDefaultData(idUbicacion: Int, ubicacion: String, distrito: String){
        val lugarrepo = LugarRepositorio()
        lugarrepo.crear(idUbicacion.toLong(), ubicacion, distrito)
    }

    override fun onStop() {
        super.onStop()
       //queue.cancelAll(TAG)
        if (queue != null){
            queue.stop()
            queue.cancelAll(TAG)
            Toast.makeText(context, "Se cancelo", Toast.LENGTH_SHORT).show()
        }
    }

}