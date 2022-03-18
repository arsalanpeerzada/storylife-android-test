package com.inksy.UI.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inksy.Interfaces.OnChangeStateClickListener
import com.inksy.Model.PeopleListModel
import com.inksy.R
import com.inksy.UI.Activities.People
import com.inksy.UI.Constants

class PeopleAdapter(
    var context: Context,
    var list: ArrayList<PeopleListModel>,
    var search: Boolean,
    var onChangeStateClickListener: OnChangeStateClickListener
) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var tv: TextView
        lateinit var imgrecent: ImageView
        lateinit var tvrecent: TextView
        lateinit var bio: TextView
        lateinit var peopleImage: ImageView
        lateinit var followClick: ImageView

        lateinit var points: TextView
        fun bind() {

            tv = itemView.findViewById(R.id.name)
            imgrecent = itemView.findViewById(R.id.imgrecent)
            tvrecent = itemView.findViewById(R.id.tvrecent)
            bio = itemView.findViewById(R.id.text)
            points = itemView.findViewById(R.id.text2)
            peopleImage = itemView.findViewById(R.id.peopleImage)
            followClick = itemView.findViewById(R.id.followClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_people, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.bind()


            if (list[position].avatar != null) {
                Glide.with(context).load(Constants.BASE_IMAGE + list[position].avatar)
                    .into(holder.peopleImage)
            } else {
                Glide.with(context).clear(holder.peopleImage);
                holder.peopleImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.avatar_placeholder
                    )
                );
            }


            holder.tv.text = list[position].fullName
            holder.bio.text = list[position].bio
            holder.points.text = list[position].points.toString()

            if (list[position].isFollowed == 1) {
                Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.unfollowing))
                    .into(holder.followClick)
            }

            if (!search)
                if (position == 0) {
                    holder.tvrecent.visibility = View.VISIBLE
                    holder.imgrecent.visibility = View.VISIBLE
                }


        } catch (e: NullPointerException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }

        holder.followClick.setOnClickListener {
            if (list[position].isFollowed == 0) {
                Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.unfollowing))
                    .into(holder.followClick)
                list[position].isFollowed = 1
                onChangeStateClickListener.onStateChange(position, true)

            } else {
                Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.follow))
                    .into(holder.followClick)
                list[position].isFollowed = 0
                onChangeStateClickListener.onStateChange(position, false)
            }
        }

        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(context, People::class.java).putExtra(
                    "Data",
                    list[position]
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}