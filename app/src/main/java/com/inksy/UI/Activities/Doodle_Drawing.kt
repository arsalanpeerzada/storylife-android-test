package com.inksy.UI.Activities


import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.inksy.R
import com.inksy.UI.Constants
import com.inksy.databinding.ActivityDoodleDrawingBinding


class Doodle_Drawing : AppCompatActivity() {

    lateinit var binding: ActivityDoodleDrawingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoodleDrawingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fragment = intent.getStringExtra("fragment").toString()


        binding.edit.setOnClickListener {
            startActivity(Intent(this@Doodle_Drawing,PackActivity::class.java))
        }

        if (fragment == Constants.fragment_pending) {
            binding.subtext.visibility = View.GONE
            binding.edit.visibility = View.VISIBLE
            binding.line1Title.text = "Pack of"
            binding.line2Title.text = "Last updated on"
            binding.line3Title.text = "Response"
            binding.line1value.text = "27"
            binding.line2value.text = "02 Oct 21"
            binding.line3value.text = "Admin"

        } else if (fragment == Constants.fragment_approved) {
            binding.subtext.visibility = View.VISIBLE
            binding.edit.visibility = View.GONE
            binding.line1Title.text = "Your Earnings"
            binding.line2Title.text = "Sale"
            binding.line3Title.text = "Upload Date"
            binding.line1value.text = "$105.93"
            binding.line2value.text = "27"
            binding.line3value.text = "02 Oct 21 "
        }

        binding.back.setOnClickListener {
            onBackPressed()
            this.finish()
        }

        binding.line3value.setOnClickListener() {

            if (fragment == Constants.fragment_pending) {
                onButtonShowPopupWindowClick(it)
            }
        }
        binding.line3Title.setOnClickListener {
            if (fragment == Constants.fragment_pending) {
                onButtonShowPopupWindowClick(it)
            }
        }

    }


    private fun onButtonShowPopupWindowClick(view: View?) {

        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.dialog_artistpanel, null)

        // create the popup window
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = true // lets taps outside the popup also dismiss it
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        // dismiss the popup window when touched
        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }
    }
}