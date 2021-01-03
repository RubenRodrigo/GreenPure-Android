package com.miempresa.greenpure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnVerMapa.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        btnVerGraficaBar.setOnClickListener{
            val intent = Intent(this, BarChartActivity2::class.java)
            startActivity(intent)
        }

        btnVerGraficaLinear.setOnClickListener{
            val intent = Intent(this, LinearChartAcitivity::class.java)
            startActivity(intent)
        }

        btnVerMenuBottom.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}