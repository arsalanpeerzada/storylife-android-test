package com.inksy.UI.Activities

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import com.bumptech.glide.Glide
import com.inksy.Model.Journals
import com.inksy.R
import com.inksy.UI.Constants
import com.inksy.UI.Dialogs.Comment_BottomSheet
import com.inksy.UI.Dialogs.ReportDialog
import com.inksy.databinding.ActivityViewOnlyJournalBinding


class ViewOnlyJournal() : AppCompatActivity() {

    lateinit var binding: ActivityViewOnlyJournalBinding
    lateinit var like: ImageView
    lateinit var comment: ImageView

    lateinit var like_counter: TextView
    lateinit var comment_counter: TextView

    var like_count: Int = 0
    var comment_count: Int = 0
    var followcounter: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOnlyJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        like = findViewById(R.id.like)
        comment = findViewById(R.id.comment)
        like_counter = findViewById(R.id.like_count)
        comment_counter = findViewById(R.id.comment_count)

        var journalData: Journals? = null

        journalData = intent.getSerializableExtra("data") as? Journals

        if (journalData != null) {
            like_count = journalData?.likesCount!!
            comment_count = journalData?.commentsCount!!

            like_counter.text = like_count.toString()
            comment_counter.text = comment_count.toString()
        }



        followcounter = 0
        binding.followCount.text = "0"
        var string = intent.getStringExtra(Constants.journalType)

        if (string.equals(Constants.people)) {
            Glide.with(this).load(getDrawable(R.drawable.follow)).into(binding.follow)


        } else {

            Glide.with(this).load(getDrawable(R.drawable.unfollowing)).into(binding.follow)

        }

        binding.like.setOnClickListener {

            if (journalData != null) {
                if (journalData.isJournalLike == 0) {
                    like_count++
                    like_counter.text = like_count.toString()
                    journalData.isJournalLike = 1
                } else if (like_count == 1) {
                    journalData.isJournalLike = 0
                    like_count--
                    like_counter.text = like_count.toString()
                }

                Log.d("-----", like_count.toString())
            } else {
                Toast.makeText(this, "Journal data is empty", Toast.LENGTH_SHORT).show()
            }
        }


        binding.comment.setOnClickListener {
            if (comment_count == 0) {
                comment_count++
                comment_counter.text = comment_count.toString()

                var comment = Comment_BottomSheet().let {
                    it.show(supportFragmentManager, "")

                }
            }
            Log.d("-----", comment_count.toString())
        }

        binding.follow.setOnClickListener()
        {
            if (followcounter == 0) {
                Glide.with(this).load(getDrawable(R.drawable.unfollowing)).into(binding.follow)

                followcounter++
                binding.followCount.text = "01"
            } else {
                followcounter--
                binding.followCount.text = "00"
                Glide.with(this).load(getDrawable(R.drawable.follow)).into(binding.follow)

            }
        }


        binding.more.setOnClickListener {
            val contextWrapper = ContextThemeWrapper(this, R.style.popupMenuStyle)
            val popupMenu = PopupMenu(
                contextWrapper, binding.more
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

                        if (journalData != null) {
                            ReportDialog(
                                this,
                                this@ViewOnlyJournal,
                                this@ViewOnlyJournal,
                                this@ViewOnlyJournal,
                                journalData.id!!.toString()
                            ).show()
                        } else {
                            Toast.makeText(this, "Journal data is empty", Toast.LENGTH_SHORT).show()
                        }

                        return@OnMenuItemClickListener true
                    }
                    R.id.block -> {
                        return@OnMenuItemClickListener true
                    }
                    R.id.View -> {
                        return@OnMenuItemClickListener true
                    }

                    else -> false
                }


            })
            popupMenu.inflate(R.menu.view_journal_popup)
            popupMenu.show()

            if (string.equals(Constants.people)) {

                popupMenu.menu.findItem(R.id.Report).isVisible = false
                popupMenu.menu.findItem(R.id.block).isVisible = false

            } else {
                popupMenu.menu.findItem(R.id.Delete).isVisible = false
                popupMenu.menu.findItem(R.id.edit).isVisible = false
                popupMenu.menu.findItem(R.id.block).isVisible = false

            }

        }


        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}