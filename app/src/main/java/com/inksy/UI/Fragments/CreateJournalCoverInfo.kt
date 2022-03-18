package com.inksy.UI.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.google.gson.Gson
import com.inksy.Model.Categories
import com.inksy.R
import com.inksy.UI.Activities.Privacy
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentCreatejournalcoverBinding

class CreateJournalCoverInfo : Fragment() {

    lateinit var cameraUri: Uri
    var photoSelect = false
    private lateinit var tvTitle: TextView
    private lateinit var tvContinue: TextView
    var numberPicker: NumberPicker? = null
    private lateinit var bottomSheetDialog: RoundedBottomSheetDialog
    lateinit var binding: FragmentCreatejournalcoverBinding
    private val PICK_REQUEST = 53
    lateinit var tinydb: TinyDB
    var selectedCategoyId: Int = 0
    var categoriesList: ArrayList<Categories> = ArrayList()
    lateinit var data: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatejournalcoverBinding.inflate(layoutInflater)

        tinydb = TinyDB(requireContext())
        var string = tinydb.getListString("categoriesList")

        cameraUri = Uri.parse("asdasdasd")

        data = arrayOf(
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a",
            "a"
        )

        for (i in 0 until string.size) {
            var gson = Gson()
            var categories = gson.fromJson(string[i], Categories::class.java)
            data[i] = categories.categoryName!!
            categoriesList.add(categories)
        }



        binding.category.setOnClickListener {
            openRoundBottomSheet()
        }

        var tinyDB = TinyDB(requireContext())
        tinyDB.remove("jsondata")


        binding.ivBack.setOnClickListener {
            val action =
                CreateJournalCoverInfoDirections.actionCreateJournalCoverInfoToCreateJournalBackgroundBorderColor()
            findNavController().navigate(action)
        }

        binding.checked.setOnClickListener {


            Toast.makeText(requireContext(), "checked", Toast.LENGTH_SHORT).show()
            if (selectedCategoyId == 0){
                Toast.makeText(requireContext(), "Please select category", Toast.LENGTH_SHORT).show()
            }else if(binding.title.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please enter title", Toast.LENGTH_SHORT).show()
            }else if (binding.description.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please enter description", Toast.LENGTH_SHORT).show()
            }else if (photoSelect){
                Toast.makeText(requireContext(), "Please select coverphoto", Toast.LENGTH_SHORT).show()
            }else {

                var bundle = Bundle()
                bundle.putInt("categoryId", selectedCategoyId)
                bundle.putString("title", binding.title.text.toString())
                bundle.putString("description", binding.description.text.toString())
                bundle.putString("categoryName", binding.category.text.toString())
                bundle.putString("uri", cameraUri.toString())

                findNavController().navigate(
                    R.id.action_CreateJournalCoverInfo_to_createJournalIndex,
                    bundle
                )
            }
        }

        binding.audienceSetting.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), Privacy::class.java))
        }

        binding.image.setOnClickListener {
            val intent2 = Intent()
            intent2.type = "image/*"
            intent2.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent2, "Select Picture"), PICK_REQUEST
            )
        }
        return binding.root
    }

    private fun openRoundBottomSheet() {
        bottomSheetDialog = RoundedBottomSheetDialog(requireContext())
        val bottomDialogView: View = LayoutInflater.from(requireContext())
            .inflate(R.layout.bottom_dialog_wheel, null)
        bottomSheetDialog.setContentView(bottomDialogView)

        numberPicker = bottomDialogView.findViewById<NumberPicker>(R.id.numberPicker)
        tvContinue = bottomDialogView.findViewById<TextView>(R.id.tvContinue)
        tvTitle = bottomDialogView.findViewById<TextView>(R.id.tvTitle)

        tvTitle.text = getString(R.string.select_category)

        numberPicker?.minValue = 0 //from array first value
        numberPicker?.maxValue = categoriesList.size - 1 //to array last value

        numberPicker?.displayedValues = data
        numberPicker?.wrapSelectorWheel = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker?.textColor = resources.getColor(R.color.black)
        }


        tvContinue.setOnClickListener(View.OnClickListener { view1: View? ->

            binding.category.setText(categoriesList[numberPicker?.value!!].categoryName)
            selectedCategoyId = categoriesList[numberPicker?.value!!].id!!
            bottomSheetDialog.dismiss()
        })

        bottomSheetDialog.show()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_REQUEST) {
                cameraUri = data!!.data!!

                val uri = data.data

                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().applicationContext.contentResolver,
                    uri
                )

                if (uri != null) {
                    cameraUri = uri
                }

//                var file: File = File(cameraUri.path)
//
//                GlobalScope.launch {
//
//                    file = Compressor.compress(requireContext(), file) {
//                        // your own extension
//                        quality(80) // combine with compressor constraint
//                        format(Bitmap.CompressFormat.JPEG)
//                    }
//
//                    cameraUri = Uri.fromFile(file)
//                }

                Glide.with(requireContext()).load(cameraUri).into(binding.coverImage)
                binding.coverImage.scaleType = ImageView.ScaleType.CENTER_CROP

            }
        }

    }


}