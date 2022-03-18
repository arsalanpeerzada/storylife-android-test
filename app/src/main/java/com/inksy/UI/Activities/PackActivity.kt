package com.inksy.UI.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.inksy.UI.Adapter.PackAdapter
import com.inksy.UI.Constants
import com.inksy.UI.ViewModel.DoodleView
import com.inksy.Utils.TinyDB
import com.inksy.databinding.ActivityPackBinding

class PackActivity : AppCompatActivity() {
    private val PICK_IMAGE_BACKGROUND = 2
    private val CAMERA_REQUEST = 52
    private val PICK_REQUEST = 53
    lateinit var doodleView: DoodleView
    lateinit var binding: ActivityPackBinding
    lateinit var tinydb: TinyDB
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tinydb = TinyDB(this)
        token = tinydb.getString("token").toString()
        doodleView = ViewModelProvider(this)[DoodleView::class.java]
        doodleView.init()

        var i = intent.getBooleanExtra("fromAdapter", false)

        if (i) {
            binding.slider.visibility = View.GONE
            binding.editTitle.visibility = View.GONE
            binding.sliderleft.visibility = View.GONE
            binding.sliderright.visibility = View.GONE
            binding.buyNow.visibility = View.VISIBLE
            binding.price.visibility = View.GONE
            binding.checked.visibility = View.GONE
            binding.price.text = "Pack Price : $2.50"

        } else {
            binding.slider.visibility = View.VISIBLE
            binding.editTitle.visibility = View.VISIBLE
            binding.sliderleft.visibility = View.VISIBLE
            binding.sliderright.visibility = View.VISIBLE
            binding.buyNow.visibility = View.GONE
            binding.price.visibility = View.VISIBLE
            binding.checked.visibility = View.VISIBLE
        }

        binding.rvPackTitle.adapter = PackAdapter(this@PackActivity)

        binding.editTitle.setOnClickListener {
            binding.title.isEnabled = !binding.title.isEnabled
            binding.title.requestFocus()
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
            this.finish()
        }
        binding.checked.setOnClickListener {
            startActivity(
                Intent(this, ArtistDashboard::class.java).putExtra(
                    "activity",
                    Constants.packActivity
                )
            )

            this.finish()
        }
        binding.buyNow.setOnClickListener {

            this.finish()
        }
        binding.slider.addOnChangeListener { slider, value, fromUser ->
            val number2digits: Double = String.format("%.2f", value).toDouble()
            binding.price2.text = "Your Price is : $${number2digits}"
        }

        binding.imageView14.setOnClickListener {
            val intent2 = Intent()
            intent2.type = "image/*"
            intent2.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent2, "Select Picture"), PICK_IMAGE_BACKGROUND
            )
        }

        //  upload(token, binding.title.text.toString(), binding.price2.text.toString())
    }

    fun upload(token: String, packTitle: String, packPrice: String, list: ArrayList<Int>) {

        //  doodleView.createPack(token, packTitle, packPrice, "1", list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_REQUEST) {
                var cameraUri = data!!.data!!

                val uri = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(
                    applicationContext.contentResolver,
                    uri
                )

                val input = contentResolver?.openInputStream(cameraUri)
                val image = BitmapFactory.decodeStream(input, null, null)


            } else if (requestCode == PICK_IMAGE_BACKGROUND) {
                var cameraUri = data!!.data!!

                val uri = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(
                    applicationContext.contentResolver,
                    uri
                )
                binding.imageView14.setImageBitmap(bitmap)


            } else if (requestCode == CAMERA_REQUEST) {
//                val selectedFilePath: String = FileUtil.getPath(this, cameraUri)
//                val file = File(selectedFilePath)
//                val compressedImageFile: File? = null
//                try {
//                    var cameraUri = Uri.fromFile(compressedImageFile)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//                selectedFile = File(cameraUri.path!!)

            }
        }

    }
}