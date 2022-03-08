package com.inksy.UI.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.example.FeaturedPack
import com.example.example.Pack
import com.inksy.R
import com.inksy.UI.Activities.PackActivity
import com.inksy.UI.Constants

class ArtworkAdapter(
    var context: Context,
    var list: ArrayList<*>,
    var type: String
) : RecyclerView.Adapter<ArtworkAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {


        lateinit var image: ImageView
        lateinit var title: TextView
        lateinit var price_Tag: TextView
        fun bind() {
            image = itemView.findViewById(R.id.image)
            title = itemView.findViewById(R.id.title)
            price_Tag = itemView.findViewById(R.id.myprice)

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

            if (type == "Feature") {
                var mylist = list as ArrayList<FeaturedPack>

                setup1(holder, mylist, position)
            } else {
                var mylist = list as ArrayList<Pack>
                setup(holder, mylist, position)
            }


        } catch (e: NullPointerException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(context, PackActivity::class.java)
                    .putExtra("fromAdapter", true)
            )

        }
    }

    private fun setup(holder: ViewHolder, mylist: ArrayList<Pack>, position: Int) {
        holder.title.text = mylist[position].packTitle
        val number2digits: String = String.format("%.2f", mylist[position].price)
        holder.price_Tag.text = number2digits
        Glide.with(context).load(Constants.BASE_IMAGE + mylist[position].coverImage)
            .into(holder.image)
    }

    private fun setup1(holder: ViewHolder, mylist: ArrayList<FeaturedPack>, position: Int) {
        holder.title.text = mylist[position].packTitle
        val number2digits: String = String.format("%.2f", mylist[position].price)
        holder.price_Tag.text = number2digits
        Glide.with(context).load(Constants.BASE_IMAGE + mylist[position].coverImage)
            .into(holder.image)
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