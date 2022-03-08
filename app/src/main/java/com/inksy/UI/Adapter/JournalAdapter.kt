package com.inksy.UI.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.Journals
import com.inksy.R
import com.inksy.UI.Activities.ViewOnlyJournal
import com.inksy.UI.Constants

class JournalAdapter(
    var context: Context,
    var list: Array<Int>,
    var type: String,
    var ionclick: iOnClickListerner
) : RecyclerView.Adapter<JournalAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var img: ImageView
        lateinit var like: ImageView
        lateinit var comment: ImageView
        lateinit var cardView: CardView
        lateinit var name: TextView
        lateinit var desc: TextView
        lateinit var imgrecent: ImageView
        lateinit var tvrecent: TextView

        lateinit var like_counter: TextView
        lateinit var comment_counter: TextView
        var like_count: Int = 0
        var comment_count: Int = 0
        fun bind() {

            name = itemView.findViewById(R.id.name)
            desc = itemView.findViewById(R.id.text)
            img = itemView.findViewById(R.id.image)
            like = itemView.findViewById(R.id.like)
            comment = itemView.findViewById(R.id.comment)
            like_counter = itemView.findViewById(R.id.like_count)
            comment_counter = itemView.findViewById(R.id.comment_count)
            cardView = itemView.findViewById(R.id.cardView)
            imgrecent = itemView.findViewById(R.id.imgrecent)
            tvrecent = itemView.findViewById(R.id.tvrecent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_journal, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()


        if (position == 0) {
            holder.imgrecent.visibility = View.VISIBLE
            holder.tvrecent.visibility = View.VISIBLE
        }

        try {
            Glide.with(context).load(list[position]).into(holder.img)
        } catch (e: NullPointerException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
        holder.name.setOnClickListener {

        }
        holder.desc.setOnClickListener {
            context.startActivity(
                Intent(context, ViewOnlyJournal::class.java).putExtra(Constants.journalType, type)
            )
        }
        holder.cardView.setOnClickListener {
            context.startActivity(
                Intent(context, ViewOnlyJournal::class.java).putExtra(Constants.journalType, type)
            )
        }

        holder.like.setOnClickListener {

            if (holder.like_count == 0) {
                holder.like_count++
                holder.like_counter.text = "06"
            } else if (holder.like_count == 1) {
                holder.like_count--
                holder.like_counter.text = "05"
            }

            Log.d("-----", holder.like_count.toString())
        }
        holder.comment.setOnClickListener {

            if (holder.comment_count == 0) {
                holder.comment_count++
                holder.comment_counter.text = "10"
                ionclick.onclick(position)
            } else if (holder.comment_count == 1) {
                holder.comment_count--
                holder.comment_counter.text = "09"
            }
            Log.d("-----", holder.comment_count.toString())


        }


    }

    fun openActivity(list : List<Journals>){
        context.startActivity(
            Intent(context, ViewOnlyJournal::class.java).putExtra(Constants.journalType, type)
        )
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