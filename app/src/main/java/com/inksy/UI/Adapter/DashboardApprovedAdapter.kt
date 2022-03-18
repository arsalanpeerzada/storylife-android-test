package com.inksy.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.example.DoodlePack
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.R
import com.inksy.UI.Constants

class DashboardApprovedAdapter(
    var context: Context,
    var doodlePendinglist: ArrayList<DoodlePack>,
    private var idashboardApproved: iOnClickListerner
) : RecyclerView.Adapter<DashboardApprovedAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var image: ImageView
        lateinit var title: TextView
        lateinit var size: TextView
        lateinit var amountCount: TextView
        lateinit var saleCount: TextView


        fun bind() {
            image = itemView.findViewById(R.id.Image)
            title = itemView.findViewById(R.id.packtitle)
            size = itemView.findViewById(R.id.packsize)
            amountCount = itemView.findViewById(R.id.salesCount)
            saleCount = itemView.findViewById(R.id.amountCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboardapproved, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.bind()

            holder.title.text = doodlePendinglist[position].packTitle
            holder.size.text = "Pack of Doodle"
            holder.amountCount.text = doodlePendinglist[position].price.toString()
            holder.saleCount.text = doodlePendinglist[position].salesCount.toString()

            Glide.with(context).load(Constants.BASE_IMAGE + doodlePendinglist[position].coverImage)
                .into(holder.image)


            holder.itemView.setOnClickListener() {
                idashboardApproved.onclick(position = position)
            }
        } catch (e: NullPointerException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return doodlePendinglist.size
    }


}