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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.Journals
import com.inksy.R
import com.inksy.UI.Activities.ViewOnlyJournal
import com.inksy.UI.Constants
import java.io.Serializable

class JournalAdapter(
    var context: Context,
    var list: ArrayList<Journals>,
    var type: String,
    var ionclick: iOnClickListerner
) : RecyclerView.Adapter<JournalAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var view: ImageView
        lateinit var like: ImageView
        lateinit var comment: ImageView
        lateinit var cardView: ConstraintLayout
        lateinit var name: TextView
        lateinit var desc: TextView
        lateinit var imgrecent: ImageView
        lateinit var tvrecent: TextView

        lateinit var journalImage: ImageView
        lateinit var journalTitle: TextView
        lateinit var journalDesc: TextView

        lateinit var like_counter: TextView
        lateinit var comment_counter: TextView
        var like_count: Int = 0
        var comment_count: Int = 0
        fun bind() {

            name = itemView.findViewById(R.id.name)
            desc = itemView.findViewById(R.id.text)
            like = itemView.findViewById(R.id.like)
            comment = itemView.findViewById(R.id.comment)
            like_counter = itemView.findViewById(R.id.like_count)
            comment_counter = itemView.findViewById(R.id.comment_count)
            cardView = itemView.findViewById(R.id.cardView)
            imgrecent = itemView.findViewById(R.id.imgrecent)
            tvrecent = itemView.findViewById(R.id.tvrecent)
            view = itemView.findViewById(R.id.view)
            journalImage = itemView.findViewById(R.id.journalImage)
            journalDesc = itemView.findViewById(R.id.journalDesc)
            journalTitle = itemView.findViewById(R.id.journalText)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_journal, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        holder.imgrecent.visibility = View.VISIBLE
        holder.tvrecent.visibility = View.VISIBLE
        try {
            holder.bind()
            Glide.with(context).load(Constants.BASE_THUMBNAIL + list[position].coverImage)
                .into(holder.journalImage)
            holder.journalTitle.text = list[position].title
            holder.journalDesc.text = list[position].description
            holder.name.text = list[position].title
            holder.desc.text = list[position].description

            holder.like_counter.text = list[position].likesCount.toString()
            holder.comment_counter.text = list[position].commentsCount.toString()

            when (list[position].coverBc) {
                "blue" -> {
                    holder.view.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.journalBlue);
                }
                "green" -> {
                    holder.view.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.journalGreen);
                }
                "red" -> {
                    holder.view.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.journalRed);
                }
                "purple" -> {
                    holder.view.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.journalPurple);
                }
            }

        } catch (e: NullPointerException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }


        if (position == 0) {
            holder.imgrecent.visibility = View.VISIBLE
            holder.tvrecent.visibility = View.VISIBLE
        }

//        try {
//            Glide.with(context).load(list[position]).into(holder.img)
//        } catch (e: NullPointerException) {
//            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
//        }
        holder.name.setOnClickListener {

        }
        holder.desc.setOnClickListener {
            var data = list[position]
            context.startActivity(
                Intent(context, ViewOnlyJournal::class.java).putExtra(Constants.journalType, type)
                    .putExtra("data", data as Serializable)
            )
        }
        holder.cardView.setOnClickListener {
            var data = list[position]
            context.startActivity(
                Intent(context, ViewOnlyJournal::class.java).putExtra(Constants.journalType, type)
                    .putExtra("data", data as Serializable)
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

        holder.itemView.setOnClickListener() {
            var data = list[position]
            context.startActivity(
                Intent(context, ViewOnlyJournal::class.java).putExtra(Constants.journalType, type)
                    .putExtra("data", data as Serializable)
            )
        }


    }

    fun openActivity(list: List<Journals>) {

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