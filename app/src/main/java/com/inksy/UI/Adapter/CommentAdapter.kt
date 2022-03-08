package com.inksy.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.inksy.R

class CommentAdapter(
    var context: Context,
    var list: Array<Int>
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {


    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var more: ImageView
        lateinit var reply: TextView
        lateinit var like: TextView
        lateinit var reply_count: TextView
        lateinit var like_count: TextView
        lateinit var newComment: EditText
        lateinit var send: ImageView

        fun bind() {

            more = itemView.findViewById(R.id.more)
            reply = itemView.findViewById(R.id.comment)
            like = itemView.findViewById(R.id.like)
            reply_count = itemView.findViewById(R.id.comment_count)
            like_count = itemView.findViewById(R.id.like_count)
            newComment = itemView.findViewById(R.id.newcomment)
            send = itemView.findViewById(R.id.send)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.bind()
        if (position == 0) {
            holder.more.visibility = View.VISIBLE

            holder.reply.text = "Reply"
        } else {

            holder.reply.text = "Comment"
        }

        holder.like.setOnClickListener() {
            holder.like.setTextColor(context.resources.getColor(R.color.appBlue))
            holder.like_count.text = "501"
        }

        holder.reply.setOnClickListener() {
            holder.newComment.visibility = View.VISIBLE
            holder.send.visibility = View.VISIBLE
        }

        holder.send.setOnClickListener(){
            holder.newComment.visibility = View.GONE
            holder.send.visibility = View.GONE
        }
        holder.more.setOnClickListener {
            val contextWrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)
            val popupMenu = PopupMenu(
                contextWrapper, holder.more
            )
            popupMenu.setForceShowIcon(true)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->

                when (item.itemId) {
                    R.id.Delete -> {

                        return@OnMenuItemClickListener true
                    }
                    R.id.edit -> {

                        return@OnMenuItemClickListener true
                    }
                    R.id.Report -> {
                        return@OnMenuItemClickListener true
                    }
                    else -> false
                }


            })
            popupMenu.inflate(R.menu.comment_pop_up)
            popupMenu.show()

            if (position == 0) {
                popupMenu.menu.findItem(R.id.Report).isVisible = false
                popupMenu.menu.findItem(R.id.edit).isVisible = true

            } else {
                popupMenu.menu.findItem(R.id.Report).isVisible = true
                popupMenu.menu.findItem(R.id.edit).isVisible = false
            }


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