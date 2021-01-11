package com.miempresa.greenpure.ui.estadistica

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.miempresa.greenpure.R

class EstadisticaFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view:View =
                inflater.inflate(R.layout.fragment_estadistica, container, false)

        val linear_estadistica = view.findViewById<LineChart>(R.id.linear_estadistica)

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
            val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(
                    ContextCompat.getColor(requireContext(), R.color.color1),
                    ContextCompat.getColor(requireContext(), R.color.color2),
                    ContextCompat.getColor(requireContext(), R.color.color3),
                    ContextCompat.getColor(requireContext(), R.color.color4),
                    ContextCompat.getColor(requireContext(), R.color.color5)
            )
            )
            linearDataSet.setFillDrawable(gradientDrawable)
        val linearData = LineData(linearDataSet)

        linear_estadistica.setData(linearData)
        linear_estadistica.getDescription().setText("Linear Chart Example")
        linear_estadistica.animateY(2000)

        return view
    }
}