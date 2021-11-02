package com.storylife.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.storylife.R

class ArtworkAdapter(
    var context: Context,
) : RecyclerView.Adapter<ArtworkAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {



        fun bind() {



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artwork, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.bind()




        } catch (e: NullPointerException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }




        holder.itemView.setOnClickListener {
//            context.startActivity(
//                Intent(context, OkaDoc_Detail::class.java)
//                    .putExtra("name", doc_list[position].fullName)
//                    .putExtra("designation", doc_list[position].jobTitle)
//                    .putExtra("image", doc_list[position].profilePicture)
//                    .putExtra("role", doc_list[position].jobRole)
//                    .putExtra("function", doc_list[position].jobFuction)
//
//
//            )



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