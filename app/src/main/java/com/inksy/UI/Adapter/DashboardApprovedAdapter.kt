package com.inksy.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.inksy.R
import com.inksy.Interfaces.iOnClickListerner

class DashboardApprovedAdapter(
    var context: Context,
    private var idashboardApproved: iOnClickListerner
) : RecyclerView.Adapter<DashboardApprovedAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboardapproved    , parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.bind()

            holder.itemView.setOnClickListener(){
                idashboardApproved.onclick(position = position)
            }
        } catch (e: NullPointerException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return 10
    }


}