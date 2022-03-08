package com.inksy.UI.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inksy.UI.Dialogs.TwoButtonDialog
import com.inksy.Interfaces.OnDialogClickListener
import com.inksy.R
import com.inksy.databinding.ActivityArtisePanelBinding

class ArtisePanel : AppCompatActivity() {

    lateinit var binding: ActivityArtisePanelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtisePanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iamin.setOnClickListener {

            val twoButtonDialog: TwoButtonDialog = TwoButtonDialog(
                this,getString(R.string.app_name),
                getString(com.inksy.R.string.artist_writeup),
                getString(com.inksy.R.string.iamsure),
                "No",
                object : OnDialogClickListener {
                    override fun onDialogClick(callBack: String?) {
                        if (callBack == "Yes") {
                            this@ArtisePanel.startActivity(
                                Intent(
                                    this@ArtisePanel,
                                    ArtistDashboard::class.java
                                )
                            )
                            this@ArtisePanel.finish()
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
}