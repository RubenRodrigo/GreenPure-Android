package com.miempresa.greenpure

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.activity_linear_chart_acitivity.*


class LinearChartAcitivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_chart_acitivity)

        val visitors: ArrayList<Entry> = ArrayList()
        visitors.add(Entry(2015f, 10f))
        visitors.add(Entry(2016f, 12f))
        visitors.add(Entry(2017f, 14f))
        visitors.add(Entry(2018f, 15f))
        visitors.add(Entry(2019f, 16f))
        visitors.add(Entry(2020f, 13f))
        visitors.add(Entry(2021f, 17f))
        visitors.add(Entry(2022f, 14f))
        visitors.add(Entry(2023f, 16f))
        visitors.add(Entry(2024f, 18f))
        visitors.add(Entry(2025f, 15f))
        visitors.add(Entry(2026f, 13f))

        var linearDataSet = LineDataSet(visitors, "Visitors")
        linearDataSet.setColors(ColorTemplate.MATERIAL_COLORS.asList())
        linearDataSet.setValueTextColor(Color.BLACK)
        linearDataSet.valueTextSize = 16f
        linearDataSet.setDrawFilled(true)
        if(Utils.getSDKInt() >= 18){
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(
                    ContextCompat.getColor(this, R.color.color1),
                    ContextCompat.getColor(this, R.color.color2),
                    ContextCompat.getColor(this, R.color.color3),
                    ContextCompat.getColor(this, R.color.color4),
                    ContextCompat.getColor(this, R.color.color5)
                )
            )
            linearDataSet.setFillDrawable(gradientDrawable)
        }

        val linearData = LineData(linearDataSet)

        linearChart.setData(linearData)
        linearChart.getDescription().setText("Linear Chart Example")
        linearChart.animateY(2000)

    }
}