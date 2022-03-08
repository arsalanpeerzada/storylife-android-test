package com.inksy.UI.Fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.inksy.R
import com.inksy.UI.Activities.Doodle_Drawing
import com.inksy.UI.Adapter.DashboardApprovedAdapter
import com.inksy.UI.Constants
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Utils.SwipeHelper
import com.inksy.Utils.SwipeHelper.UnderlayButtonClickListener
import com.inksy.databinding.FragmentDashboardPendingBinding

class DashboardPending : Fragment() {
    private var toast: Toast? = null
    lateinit var binding: FragmentDashboardPendingBinding
    lateinit var _adapter: DashboardApprovedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardPendingBinding.inflate(layoutInflater)


        _adapter = DashboardApprovedAdapter(requireContext(), object : iOnClickListerner {
            override fun onclick(position: Int) {
                requireContext().startActivity(
                    Intent(
                        requireContext(),
                        Doodle_Drawing::class.java
                    ).putExtra("fragment", Constants.fragment_pending)
                )
            }
        })

        binding.rvDashboardApproved.adapter = _adapter



        object : SwipeHelper(requireContext(), binding.rvDashboardApproved, false) {

            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {


                // More Button
                underlayButtons?.add(SwipeHelper.UnderlayButton(
                    "Delete",
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_delete_24
                    ),
                    Color.parseColor("#FFFFFFFF"), Color.parseColor("#F81A00"),
                    UnderlayButtonClickListener { pos: Int ->

                        Toast.makeText(
                            requireContext(),
                            "Item Deleted CLicked at Position: $pos",
                            Toast.LENGTH_SHORT
                        ).show()
                        _adapter.notifyItemChanged(pos)
                    }

                ))
            }


        }
        return binding.root
    }


    private fun toast(text: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
        toast?.show()
    }




}


