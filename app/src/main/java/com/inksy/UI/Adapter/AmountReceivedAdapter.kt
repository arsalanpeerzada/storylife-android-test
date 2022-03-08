package com.inksy.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.inksy.R

class AmountReceivedAdapter(
    var context: Context,
) : RecyclerView.Adapter<AmountReceivedAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind() {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_amoun_received, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.bind()
        } catch (e: NullPointerException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return 10
    }

//    fun setFilter(countryModels: List<Get_Doctors_Model.myList>, _searchText: String) {
//        doc_list = ArrayList<Get_Doctors_Model.myList>()
//        doc_list.addAll(countryModels)
//        this.searchText = _searchText
//        notifyDataSetChanged()
//    }


}