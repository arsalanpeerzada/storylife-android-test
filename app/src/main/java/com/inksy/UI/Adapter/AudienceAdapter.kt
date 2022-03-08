package com.inksy.UI.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.inksy.R
import com.inksy.UI.Activities.People

class AudienceAdapter(
    var context: Context,
    var list: Array<String>,
) : RecyclerView.Adapter<AudienceAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var tv: TextView
        fun bind() {

            tv = itemView.findViewById(R.id.name)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_audience, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.bind()

            holder.tv.text = list[position]


        } catch (e: NullPointerException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, People::class.java))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}