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
import com.inksy.UI.Activities.ChatActivity

class ChatAdapter(
    var context: Context,
    var list: Array<String>,
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var tv: TextView
        fun bind() {

            tv = itemView.findViewById(R.id.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
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
            context.startActivity(Intent(context, ChatActivity::class.java))
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