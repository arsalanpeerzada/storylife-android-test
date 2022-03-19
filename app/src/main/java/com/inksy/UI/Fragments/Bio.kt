package com.inksy.UI.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.inksy.R
import com.inksy.UI.Activities.MainActivity
import com.inksy.UI.ViewModel.LoginView
import com.inksy.Utils.FileUtil
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentBioBinding
import java.io.File

class Bio : Fragment() {

    lateinit var loginView: LoginView
    private lateinit var cameraUri: Uri
    private val PICK_REQUEST = 53
    lateinit var binding: FragmentBioBinding
    lateinit var tinyDB: TinyDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentBioBinding.inflate(layoutInflater)
        tinyDB = TinyDB(requireContext())

        cameraUri = Uri.parse("asdasdas")
        binding.button.setOnClickListener() {
            if (binding.name1.text.isNullOrEmpty()) {
                binding.nameError.visibility = View.VISIBLE
            }
            if (binding.summary.text.isNullOrEmpty()) {
                binding.summary.error = getString(R.string.bioError)
            }

            if (!binding.name1.text.isNullOrEmpty() && !binding.summary.text.isNullOrEmpty()) {
                binding.loader.visibility = View.VISIBLE

                val token = tinyDB.getString("token")

                var file: File = FileUtil.from(requireContext(), cameraUri)

                loginView = ViewModelProviders.of(requireActivity())[LoginView::class.java]
                loginView.init()
                loginView.profile(
                    binding.name1.text.toString(),
                    binding.summary.text.toString(),
                    file,
                    token!!
                )?.observe(requireActivity()) {
                    binding.loader.visibility = View.GONE
                    if (it?.status == 1) {

                        tinyDB.putString("fullname", it.data?.fullName)
                        tinyDB.putString("bio", it?.data?.bio)
                        tinyDB.putInt("isprofilecompleted", it.data?.isProfileCompleted!!)

                        if (it?.data?.avatar.isNullOrBlank()) {

                        } else {
                            tinyDB.putString("avatar", it?.data?.avatar)
                        }

                        requireContext().startActivity(
                            Intent(
                                requireContext(),
                                MainActivity::class.java
                            )
                        )
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }

        binding.circleImageView2.setOnClickListener() {
            val intent2 = Intent()
            intent2.type = "image/*"
            intent2.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent2, "Select Picture"), PICK_REQUEST
            )
        }

        return binding.root
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
                Glide.with(requireContext()).load(bitmap).into(binding.circleImageView2)

            }
        }

    }


}