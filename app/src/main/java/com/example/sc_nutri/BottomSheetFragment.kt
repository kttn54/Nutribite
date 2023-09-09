package com.example.sc_nutri

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sc_nutri.databinding.BottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var bsmBinding: BottomSheetFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bsmBinding = BottomSheetFragmentBinding.inflate(inflater, container, false)

        bsmBinding.btnBsRetake.setOnClickListener {
            dismiss()

        }
        bsmBinding.btnBsSeeMore.setOnClickListener {
            findNavController().navigate(R.id.action_cameraFragment_to_recommendationDetailsFragment)
        }

        getRecommendation()


        return bsmBinding.root
    }

    private fun getRecommendation() {
        val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        val recommendation = sharedPref.getString("recommendation_result", "")!!

        if (recommendation.contains("not recommended") || recommendation.contains("Not recommended")) {
            bsmBinding.ivRecommendation.setImageResource(R.drawable.rec_cross)
        } else {
            bsmBinding.ivRecommendation.setImageResource(R.drawable.rec_tick)
        }

        bsmBinding.bsmTvRecommendationText.text = recommendation
    }
}