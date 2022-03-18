package com.inksy.UI.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.inksy.Interfaces.OnDialogClickListener
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.Dialogs.TwoButtonDialog
import com.inksy.UI.ViewModel.DoodleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.ActivityArtisePanelBinding

class ArtisePanel : AppCompatActivity() {

    lateinit var binding: ActivityArtisePanelBinding
    lateinit var doodleView: DoodleView
    lateinit var tinydb: TinyDB
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtisePanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tinydb = TinyDB(this)
        token = tinydb.getString("token").toString()
        doodleView = ViewModelProvider(this)[DoodleView::class.java]
        doodleView.init()

        binding.iamin.setOnClickListener {

            val twoButtonDialog: TwoButtonDialog = TwoButtonDialog(
                this, getString(R.string.app_name),
                getString(com.inksy.R.string.artist_writeup),
                getString(com.inksy.R.string.iamsure),
                "No",
                object : OnDialogClickListener {
                    override fun onDialogClick(callBack: String?) {
                        if (callBack == "Yes") {
                            makeartist()

                                binding.loader.visibility = View.VISIBLE

                        } else {

                        }
                    }
                })
            twoButtonDialog.window!!.setBackgroundDrawableResource(R.color.transparent)
            twoButtonDialog.show()

        }

        binding.ManagePayment.setOnClickListener {
            startActivity(Intent(this@ArtisePanel, PaymentActivity::class.java))


        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun makeartist() {

        doodleView.artistMake(token)?.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loader.visibility = View.GONE
                    this@ArtisePanel.startActivity(
                        Intent(
                            this@ArtisePanel,
                            ArtistDashboard::class.java
                        )
                    )
                    this@ArtisePanel.finish()
                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    Toast.makeText(this, it?.data?.message, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}