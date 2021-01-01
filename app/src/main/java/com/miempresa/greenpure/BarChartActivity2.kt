package com.miempresa.greenpure

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_bar_chart2.*

class BarChartActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart2)

        val visitors: ArrayList<BarEntry> = ArrayList()
        visitors.add(BarEntry(2015.toFloat(), 120.toFloat()))
        visitors.add(BarEntry(2016.toFloat(), 320.toFloat()))
        visitors.add(BarEntry(2017.toFloat(), 520.toFloat()))

        var barDataSet = BarDataSet(visitors, "Visitors")
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS.asList())
        barDataSet.setValueTextColor(Color.BLACK)
        barDataSet.valueTextSize = 16f

        val barData = BarData(barDataSet)

        barChart.setFitBars(true)
        barChart.setData(barData)
        barChart.getDescription().setText("Bar Chart Example")
        barChart.animateY(2000)
    }
}