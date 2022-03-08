package com.inksy.UI.Fragments

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.inksy.Interfaces.OnDialogClickListener
import com.inksy.UI.Activities.*
import com.inksy.UI.Activities.List
import com.inksy.UI.Constants
import com.inksy.UI.Dialogs.TwoButtonDialog
import com.inksy.UI.ViewModel.LogoutView
import com.inksy.databinding.FragmentMoreInfoBinding

class MoreInfo : Fragment() {


    lateinit var binding: FragmentMoreInfoBinding
    lateinit var logoutView: LogoutView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreInfoBinding.inflate(layoutInflater)

        binding.tvDoodle.setOnClickListener() {
            openNewActivity(DoodleStore::class.java)
        }
        binding.imageDooble.setOnClickListener { openNewActivity(DoodleStore::class.java) }
        binding.tvDescDoodle.setOnClickListener { openNewActivity(DoodleStore::class.java) }

        binding.tvimgBlockList.setOnClickListener { openNewActivity(List::class.java) }
        binding.imgBlockList.setOnClickListener { openNewActivity(List::class.java) }
        binding.subtvimgBlockList.setOnClickListener { openNewActivity(List::class.java) }

        binding.tvArtist.setOnClickListener { openNewActivity(ArtisePanel::class.java) }
        binding.imgArtist.setOnClickListener { openNewActivity(ArtisePanel::class.java) }
        binding.subtvArtist.setOnClickListener { openNewActivity(ArtisePanel::class.java) }


        binding.tvMore.setOnClickListener { openNewActivity(OtherActivity::class.java) }
        binding.tvMoreDesc.setOnClickListener { openNewActivity(OtherActivity::class.java) }
        binding.imgMore.setOnClickListener { openNewActivity(OtherActivity::class.java) }


        binding.tvContactUs.setOnClickListener { openNewActivity(Contact_Form::class.java) }
        binding.tvContactUsDesc.setOnClickListener { openNewActivity(Contact_Form::class.java) }
        binding.imgtvContactUs.setOnClickListener { openNewActivity(Contact_Form::class.java) }

        binding.LogoutTitle.setOnClickListener { openDialog() }
        binding.logoutImg.setOnClickListener { openDialog() }
        binding.logoutSub.setOnClickListener { openDialog() }

        return binding.root

    }

    private fun openNewActivity(clazz: Class<*>) {
        requireContext().startActivity(Intent(requireContext(), clazz))
    }


    private fun openDialog() {
        val twoButtonDialog: TwoButtonDialog = TwoButtonDialog(
            requireActivity(), "Log Out",
            getString(com.inksy.R.string.logout),
            getString(R.string.yes),
            getString(R.string.no),
            object : OnDialogClickListener {
                override fun onDialogClick(callBack: String?) {
                    if (callBack == "Yes") {

                        logout()
                    } else {

                    }
                }
            })
        twoButtonDialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        twoButtonDialog.show()
    }


    fun logout() {
        val shared = context?.getSharedPreferences(
            Constants.APP_NAME,
            Context.MODE_PRIVATE
        )

        var token = shared?.getString("token", " ")

        logoutView = ViewModelProviders.of(requireActivity())[LogoutView::class.java]
        logoutView.init()
        logoutView.logout(token)?.observe(requireActivity()) {

            if (it?.status == 1) {

                val edit = shared?.edit()
                edit?.clear()
                edit?.apply()

                requireContext().startActivity(
                    Intent(
                        requireContext(),
                        StartingActivity::class.java
                    )
                )

            } else {
                Toast.makeText(requireContext(), it?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}