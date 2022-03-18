package com.inksy.UI.Fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.Activities.StartingActivity
import com.inksy.UI.Adapter.DashboardAdapter
import com.inksy.UI.ViewModel.DoodleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentDashboardAnalyticsBinding

class Dashboard_Analytics : Fragment() {
    lateinit var doodleView: DoodleView
    private lateinit var lineChart: LineChart
    lateinit var binding: FragmentDashboardAnalyticsBinding
    lateinit var tinydb: TinyDB
    var token = ""
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
        tinydb = TinyDB(requireContext())
        token = tinydb.getString("token").toString()
        doodleView = ViewModelProvider(this)[DoodleView::class.java]
        doodleView.init()

        getData()
        val lineDataSet = LineDataSet(lineChartDataSet(), "data set")
        val iLineDataSets: ArrayList<ILineDataSet> = ArrayList()
        iLineDataSets.add(lineDataSet)


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

    private fun getData() {

        doodleView.artistDashboard(token)?.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    var artistAnalytics = it?.data?.data

                    var list_logo = arrayOf(
                        R.drawable.royalties_this_month,
                        R.drawable.approved_art,
                        R.drawable.pending_art,
                        R.drawable.royalties_this_month,
                        R.drawable.royalties_this_month,
                        R.drawable.royalties_this_month,
                        R.drawable.royalties_this_month,
                        R.drawable.royalties_this_month,
                        R.drawable.royalties_this_month
                    )

                    var list_name = arrayOf(
                        "Total Pack",
                        "Approved Art",
                        "Pending Art",
                        "Total Sales",
                        "Today Sales",
                        "Monthly Sales",
                        "Yearly Sales",
                        "Total Received",
                        "Total Earned"
                    )

                    var value = intArrayOf(
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,

                        )

                    binding.rvDashboard.adapter = DashboardAdapter(
                        requireContext(),
                        list_logo,
                        list_name,
                        value
                    )

                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    requireContext().startActivity(
                        Intent(
                            requireContext(),
                            StartingActivity::class.java
                        )
                    )
                    Toast.makeText(requireContext(), "Token Expired", Toast.LENGTH_SHORT).show()
                    Toast.makeText(requireContext(), it?.data?.message, Toast.LENGTH_SHORT).show()
                }

            }
        }
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