package com.miempresa.greenpure.ui.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.miempresa.greenpure.R
import com.miempresa.greenpure.ui.aire.Elementos
import com.miempresa.greenpure.ui.aire.InfoAire
import org.json.JSONException
import java.io.IOException

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var searchPlace: SearchView
    private var mapView: View? = null
    private lateinit var mMap: GoogleMap
    private val TAG: String = "MapFragment"

    val GOOD_AIR = 30
    val MODERATE_AIR = 50
    val REGULAR_AIR = 70
    var CALIDAD_ESTADO = "NaN"

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
        }
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap?.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.map_styles
                    )
            )
            if (!success!!) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }

        if(context?.let { ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) } ==
                PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true)
            val locationButton= (mapView?.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById<View>(
                    Integer.parseInt(
                            "2"
                    )
            )
            // My custumize MyLocation Buttom
            val layoutParams: RelativeLayout.LayoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            layoutParams.setMargins(0, 30, 30, 30)
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)
        }

        // To call API
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-16.4002619, -71.5630447), 10f))
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(LayoutInflater.from(getActivity())));
        agregarMarcador(-34.0, 151.0, "Sydney", "95", "23","1")
        agregarMarcador(-16.3988084, -71.5390943, "Plaza de armas",  "25", "23", "2")
        agregarMarcador(-16.3446185, -71.5690884, "Cerro Colorado",  "35", "23","3")
        agregarMarcador(-16.3874759, -71.5439161, "Mirador de Yanahuara",  "65", "23","4")

        mMap.setOnInfoWindowClickListener(this)
    }

    override fun onInfoWindowClick(marker: Marker?) {
        println("CLICK EN EL INFO MARCADOR")
        if (marker != null) {
            Toast.makeText(context, "INFO MARKER" + marker.title, Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, InfoAire::class.java)
            intent.putExtra("id", marker.title)
            startActivity(intent)
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Set the fragment
        mapFragment = childFragmentManager?.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapView = mapFragment.view
        mapFragment?.getMapAsync(this)

        // Search place
        searchPlace = view.findViewById(R.id.searchPlace)
        searchPlace.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println("SUBMIT DE LA BUSQUEDA")
                var location = searchPlace.query.toString()
                var addressList: List<Address>? = null
                if (location != null || location != "") {
                    var geocoder: Geocoder = Geocoder(context)
                    try {
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    var address: Address = addressList?.get(0)!!
                    var latLng = LatLng(address.latitude, address.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return view
    }

    // Add marker
    fun agregarMarcador(
            lat: Double,
            long: Double,
            distrito: String,
            calidad: String,
            temperatura: String,
            id: String,
    ) {

        val img = calidadAireMarcador(calidad.toInt())
        var markerInfo = MarkerInfo(distrito, CALIDAD_ESTADO, calidad, temperatura)
        val gson = Gson()
        val markerInfoString: String = gson.toJson(markerInfo)
        val marker = mMap.addMarker(
                MarkerOptions()
                        .position(LatLng(lat, long))
                        .icon(context?.let {
                            bitmapDescriptorFromVector(
                                    it,
                                    img
                            )
                        })
                        .title(id)
                        .snippet(markerInfoString)
        )
        marker.tag = 0

    }

    fun calidadAireMarcador(calidad: Int): Int {
        when{
            calidad in 0..GOOD_AIR -> {
                CALIDAD_ESTADO = "Bueno"
                return R.drawable.ic_good_place
            }
            calidad in GOOD_AIR..MODERATE_AIR -> {
                CALIDAD_ESTADO = "Moderado"
                return R.drawable.ic_moderate_place
            }
            calidad in MODERATE_AIR..REGULAR_AIR -> {
                CALIDAD_ESTADO = "Regular"
                return R.drawable.ic_regular_place
            }
            calidad > REGULAR_AIR -> {
                CALIDAD_ESTADO = "Mala"
                return R.drawable.ic_bad_place
            }
            else -> return R.drawable.ic_good_place
        }
        return R.drawable.ic_good_place
    }

    // Convert images
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                    intrinsicWidth,
                    intrinsicHeight,
                    Bitmap.Config.ARGB_8888
            )
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    // Function to call API
    /*fun crearLista(): ArrayList<Elementos> {
        var llenarLista = ArrayList<Elementos>()

        AsyncTask.execute {
            val queue = Volley.newRequestQueue(activity)
            val url = getString(R.string.urlAPI) + "/dato/"
            val stringRequest = JsonArrayRequest(url,
                    { response ->
                        try {
                            for (i in 0 until response.length()) {
                                val id = response.getJSONObject(i).getString("id")
                                val ubicacion = response.getJSONObject(i).getString("Ubicacion")
                                val lugar = response.getJSONObject(i).getString("Lugar")
                                val humedad = response.getJSONObject(i).getString("Humedad")
                                val temperatura = response.getJSONObject(i).getString("Temperatura")
                                val calor = response.getJSONObject(i).getString("Calor")
                                val concentracion = response.getJSONObject(i).getString("Concentracion")
                                val sensorHumo = response.getJSONObject(i).getBoolean("SensorHumo")
                                val sensorMetano = response.getJSONObject(i).getBoolean("SensorMetano")
                                val fecha = response.getJSONObject(i).getString("fecha")
                                llenarLista.add(
                                        Elementos(
                                                id.toInt(),
                                                ubicacion, lugar,
                                                humedad.toDouble(), temperatura.toDouble(),
                                                calor.toDouble(), concentracion.toDouble(),
                                                sensorHumo, sensorMetano,
                                                fecha,
                                                linearDataSet

                                        )
                                )
                            }

                        } catch (e: JSONException) {
                            Toast.makeText(
                                    activity,
                                    "Error al obtener los datos",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }, {
                Toast.makeText(
                        activity,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                ).show()
            })
            queue.add(stringRequest)
        }

        return llenarLista
    }*/

}