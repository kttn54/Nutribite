package com.example.sc_nutri.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sc_nutri.FileViewModel
import com.example.sc_nutri.R
import com.example.sc_nutri.Resource
import com.example.sc_nutri.activities.MainActivity
import com.example.sc_nutri.databinding.FragmentProfileBinding
import com.example.sc_nutri.databinding.FragmentRecommendationDetailsBinding
import kotlinx.coroutines.flow.collect

class RecommendationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRecommendationDetailsBinding
    private lateinit var viewModel: FileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendationDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setRecommendationDetails()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbarRecommendationDetails
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Recommendation Analysis"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setRecommendationDetails() {
        val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        val recommendationResult = sharedPref.getString("recommendation_result", "")

        val reasonsString = sharedPref.getString("recommendation_reasons", "")
        val reasonsArray = reasonsString?.split("|~|")?.toTypedArray()
        val ingredientsString = sharedPref.getString("recommendation_ingredients", "")
        val ingredientsArray = ingredientsString?.split("|~|")?.toTypedArray()
        val nutritionalAnalysisString = sharedPref.getString("recommendation_nutritional_analysis", "")
        val nutritionalAnalysisArray = nutritionalAnalysisString?.split("|~|")?.toTypedArray()

        binding.bsmTvRecommendationSentence.text = recommendationResult

        for (reason in reasonsArray!!) {
            binding.bsmTvReasonsText.append("$reason\n")
        }
        binding.bsmTvReasonsText.text = binding.bsmTvReasonsText.text.substring(0, binding.bsmTvReasonsText.text.length - 1)
        for (ingredientsAnalysis in ingredientsArray!!) {
            binding.bsmTvIngredientsEvaluationDescription.append("$ingredientsAnalysis\n")
        }
        binding.bsmTvIngredientsEvaluationDescription.text = binding.bsmTvIngredientsEvaluationDescription.text.substring(0, binding.bsmTvIngredientsEvaluationDescription.text.length - 1)
        for (nutritionalAnalysis in nutritionalAnalysisArray!!) {
            binding.bsmTvNutritionalAnalysisText.append("$nutritionalAnalysis\n")
        }
        binding.bsmTvNutritionalAnalysisText.text = binding.bsmTvNutritionalAnalysisText.text.substring(0, binding.bsmTvNutritionalAnalysisText.text.length - 1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.d("test", "is the back button pressed?")
                // Handle the back button press here
                // Navigate back to the CameraFragment with the BottomSheetFragment showing
                requireActivity().supportFragmentManager.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleError(message: String) {
        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_LONG).show()
    }
}