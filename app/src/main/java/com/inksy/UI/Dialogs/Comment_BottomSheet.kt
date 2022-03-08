package com.inksy.UI.Dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inksy.UI.Adapter.CommentAdapter
import com.inksy.databinding.FragmentCommentBottomSheetBinding

class Comment_BottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: FragmentCommentBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommentBottomSheetBinding.inflate(layoutInflater)

        var list = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)

        binding.commentList.adapter = CommentAdapter(requireContext(), list)

        binding.ivBack.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}