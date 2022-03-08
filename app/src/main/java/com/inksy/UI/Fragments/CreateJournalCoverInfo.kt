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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.inksy.R
import com.inksy.UI.Activities.Privacy
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentCreatejournalcoverBinding

class CreateJournalCoverInfo : Fragment() {

    private lateinit var cameraUri: Uri
    private lateinit var tvTitle: TextView
    private lateinit var tvContinue: TextView
    var numberPicker: NumberPicker? = null
    private lateinit var bottomSheetDialog: RoundedBottomSheetDialog
    lateinit var binding: FragmentCreatejournalcoverBinding
    private val PICK_REQUEST = 53
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatejournalcoverBinding.inflate(layoutInflater)

        binding.subtext.setOnClickListener {
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
            val action =
                CreateJournalCoverInfoDirections.actionCreateJournalCoverInfoToCreateJournalIndex()
            findNavController().navigate(action)
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


        val data = arrayOf(
            "Health",
            "Motivational",
            "Spiritual",
            "Tech",
            "Mindfulness",
            "Trending",
            "Goals",
            "Planning"
        )

        numberPicker?.minValue = 0 //from array first value
        numberPicker?.maxValue = data.size - 1 //to array last value

        numberPicker?.displayedValues = data
        numberPicker?.wrapSelectorWheel = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker?.textColor = resources.getColor(R.color.black)
        }


        tvContinue.setOnClickListener(View.OnClickListener { view1: View? ->

            binding.subtext.setText(data[numberPicker?.value!!])

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

                Glide.with(requireContext()).load(cameraUri).into(binding.coverImage)
                binding.coverImage.scaleType = ImageView.ScaleType.CENTER_CROP

            }
        }

    }


}