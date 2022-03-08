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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.inksy.Interfaces.OnChangeStateClickListener
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.Journals
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.Activities.CreateActivity
import com.inksy.UI.Activities.ViewAll
import com.inksy.UI.Adapter.BookAdapter
import com.inksy.UI.Constants
import com.inksy.UI.Dialogs.Comment_BottomSheet
import com.inksy.UI.ViewModel.EditProfileView
import com.inksy.UI.ViewModel.JournalView
import com.inksy.UI.ViewModel.PeopleView
import com.inksy.Utils.FileUtil
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentProfileBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class Profile : Fragment(), iOnClickListerner, OnChangeStateClickListener {

    var list: ArrayList<Journals>? = ArrayList()
    var token: String = ""
    lateinit var peopleView: PeopleView
    lateinit var editProfileView: EditProfileView
    override fun onclick(position: Int) {
        super.onclick(position)
        Comment_BottomSheet().show(childFragmentManager, " ");
    }

    lateinit var tinydb: TinyDB
    lateinit var binding: FragmentProfileBinding
    private lateinit var cameraUri: Uri
    private val PICK_REQUEST = 53
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)

        peopleView = ViewModelProvider(this)[PeopleView::class.java]
        peopleView.init()

        tinydb = TinyDB(requireContext())
        binding.name.text = tinydb.getString("fullname")
        binding.followpeople.text = "Followed by ${tinydb.getString("followers")} People"
        binding.points.text = tinydb.getString("points")
        binding.bio.text = tinydb.getString("bio")

        token = tinydb.getString("token")!!
        var id = tinydb.getString("id")!!.toInt()
        getdetails(id, token)

        editProfileView = ViewModelProviders.of(requireActivity())[EditProfileView::class.java]

        var list = arrayOf(
            R.drawable.red_book,
            R.drawable.book_green,
            R.drawable.book_blue,
            R.drawable.red_book,
            R.drawable.book_green,
            R.drawable.book_blue,
            R.drawable.red_book, R.drawable.book_green, R.drawable.book_blue,
        )

        binding.createJournal.setOnClickListener {  requireContext().startActivity(Intent(requireContext(), CreateActivity::class.java)) }
//
//        binding.rvFriends.adapter = BookAdapter(requireContext(), list, Constants.person, this)
//
//        binding.rvHealth.adapter = BookAdapter(requireContext(), list, Constants.person, this)
//
//        binding.rvHealth2.adapter = BookAdapter(requireContext(), list, Constants.person, this)


        binding.seeall1.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ViewAll::class.java).putExtra(Constants.activity,
                Constants.sub_journalViewAll))

        }
        binding.seeall2.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ViewAll::class.java).putExtra(Constants.activity,Constants.sub_journalViewAll))

        }
        binding.seeall3.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ViewAll::class.java).putExtra(Constants.activity,Constants.sub_journalViewAll))

        }

        binding.write.setOnClickListener {
            val action = ProfileDirections.actionProfileToEditProfile()
            findNavController().navigate(action)
        }
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
            requireActivity().finish()
        }


        binding.camera.setOnClickListener() {
            val intent2 = Intent()
            intent2.type = "image/*"
            intent2.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent2, "Select Picture"), PICK_REQUEST
            )
        }


        binding.followpeople.setOnClickListener() {
            var intent = Intent(requireContext(), ViewAll::class.java).putExtra(
                "activity",
                Constants.peopleViewAll
            )
            requireContext().startActivity(intent)
        }

        return binding.root
    }

    fun getdetails(id: Int, token: String) {
        peopleView.userDetail(id, token)?.observe(requireActivity()) { it ->
            when (it.status) {
                Status.ERROR -> {

                }
                Status.SUCCESS -> {
//                    Toast.makeText(this, it.data?.data?.fullName, Toast.LENGTH_SHORT).show()

//                    binding.followedPeople.text =
//                        "Followed by ${it.data?.data?.followerCount} People"

                    binding.points.text = it.data?.data?.points.toString()


                    list = it.data?.data?.journals

                    if (list?.size == 0) {
                        binding.textView3.visibility = View.GONE
                        binding.textView4.visibility = View.GONE
                        binding.rvFriends.visibility = View.GONE
                        binding.seeall1.visibility = View.GONE

                         } else {
                        binding.textView3.visibility = View.VISIBLE
                        binding.textView4.visibility = View.VISIBLE
                        binding.rvFriends.visibility = View.VISIBLE
                        binding.seeall1.visibility = View.VISIBLE

                        binding.rvFriends.adapter =
                            BookAdapter(requireContext(), list!!, " ", this, this)

                    }
                }
                Status.LOADING -> {

                }
            }

            //it.data?.data?.fullName
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_REQUEST) {
                cameraUri = data!!.data!!

                val uri = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().applicationContext.contentResolver,
                    uri
                )
                Glide.with(requireContext()).load(bitmap).into(binding.circleImageView)
                var isFromCam = false
                var requestFile: RequestBody

                if (cameraUri != null) {
                    try {
                        if (isFromCam) {
                            requestFile = RequestBody.create(
                                "jpg".toMediaTypeOrNull(),
                                FileUtil.from(requireContext(), cameraUri)
                            )
                        } else {
                            if (requireContext().contentResolver.getType(cameraUri) != null) {
                                requestFile = RequestBody.create(
                                    requireContext().contentResolver.getType(cameraUri)!!
                                        .toMediaTypeOrNull(),
                                    FileUtil.from(requireContext(), cameraUri)
                                )
                            } else {
                                requestFile =
                                    RequestBody.create(
                                        ".png".toMediaTypeOrNull(),
                                        FileUtil.from(requireContext(), cameraUri)
                                    )
                            }
                        }

                        var name = tinydb.getString("fullname")
                        var bio = tinydb.getString("bio")
                        var token = tinydb.getString("token")

//                        uploadImage(name, bio, requestFile, token)


                        editProfileView.profile(
                            name!!,
                            bio!!,
                            requestFile,
                            token!!
                        )?.observe(requireActivity()) {
                            if (it?.status == 1) {

                                tinydb.putString("avatar", it.data?.avatar)
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                            } else {
                                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
                            }
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(
                            requireContext(),
                            e.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }

    }

    override fun onStateChange(position: Int, like: Boolean) {
        super.onStateChange(position, like)

        if (like) {
            likeJournal(list?.get(position)?.id, like)
        } else {

        }
    }

    private fun likeJournal(id: Int?, like: Boolean) {
        val journalView: JournalView =
            ViewModelProviders.of(requireActivity())[JournalView::class.java]
        journalView.init()
        journalView.journalLike(
            id.toString(),
            token
        )?.observe(requireActivity()) {

            if (it?.data?.status == 1) {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}