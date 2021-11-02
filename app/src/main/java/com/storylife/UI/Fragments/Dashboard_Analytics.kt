package com.storylife.UI.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.storylife.UI.Adapter.DashboardAdapter
import com.storylife.databinding.FragmentDashboardAnalyticsBinding

class Dashboard_Analytics : Fragment() {


    private lateinit var lineChart: LineChart
    lateinit var binding: FragmentDashboardAnalyticsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardAnalyticsBinding.inflate(layoutInflater)
        lineChart = binding.lineChart
        val lineDataSet = LineDataSet(lineChartDataSet(), "data set")
        val iLineDataSets: ArrayList<ILineDataSet> = ArrayList()
        iLineDataSets.add(lineDataSet)

        binding.rvDashboard.adapter = DashboardAdapter(requireContext())

        val lineData = LineData(iLineDataSets)
        lineChart.setData(lineData)
        lineChart.invalidate()


        //if you want set background color use below method
        //lineChart.setBackgroundColor(Color.RED);

        // set text if data are are not available


        //if you want set background color use below method
        //lineChart.setBackgroundColor(Color.RED);

        // set text if data are are not available
        lineChart.setNoDataText("Data not Available")

        //you can modify your line chart graph according to your requirement there are lots of method available in this library

        //now customize line chart


        //you can modify your line chart graph according to your requirement there are lots of method available in this library

        //now customize line chart
        lineDataSet.color = Color.BLUE
        lineDataSet.setCircleColor(Color.GREEN)
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.lineWidth = 5f
        lineDataSet.circleRadius = 10f
        lineDataSet.circleHoleRadius = 10f
        lineDataSet.valueTextSize = 10f
        lineDataSet.valueTextColor = Color.BLACK

        return binding.root

    }

    private fun lineChartDataSet(): ArrayList<Entry>? {
        val dataSet = ArrayList<Entry>()
        dataSet.add(Entry(0F, 40F))
        dataSet.add(Entry(1F, 10F))
        dataSet.add(Entry(2F, 15F))
        dataSet.add(Entry(3F, 12F))
        dataSet.add(Entry(4F, 20F))
        dataSet.add(Entry(5F, 50F))
        dataSet.add(Entry(6F, 23F))
        dataSet.add(Entry(7F, 34F))
        dataSet.add(Entry(8F, 12F))
        return dataSet
    }


}