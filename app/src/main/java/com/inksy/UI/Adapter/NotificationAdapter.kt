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

class NotificationAdapter(
    var context: Context,
    var list: Array<String>,
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var tv: TextView
        fun bind() {

            tv = itemView.findViewById(R.id.name)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notifications, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.bind()

            if (position % 4 == 0){
                holder.tv.text = "Your follower Kim just created a journal."
            }else if (position % 4 == 1){
                holder.tv.text = "Emma Watson just follow your journal."
            }else if (position % 4 == 2){
                holder.tv.text = "Robert DaCosta follow you."
            }else if (position % 4 == 3){
                holder.tv.text = "Admin marked your journal as private."
            }

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

//    fun setFilter(countryModels: List<Get_Doctors_Model.myList>, _searchText: String) {
//        doc_list = ArrayList<Get_Doctors_Model.myList>()
//        doc_list.addAll(countryModels)
//        this.searchText = _searchText
//        notifyDataSetChanged()
//    }


}