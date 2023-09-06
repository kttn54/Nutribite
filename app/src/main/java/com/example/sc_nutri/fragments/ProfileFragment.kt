package com.example.sc_nutri.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.FragmentProfileBinding
import org.json.JSONArray

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var weight = 0
    private var height = 0
    private lateinit var gender: String
    private lateinit var foodPreferences: String
    private var bmr = 0
    private lateinit var fitnessLevel: String
    private lateinit var weightPreference: String
    private var allergiesList: ArrayList<String> = ArrayList()
    private var allergiesListDropdown: ArrayList<Int> = ArrayList()
    private var allergies: String = ""
    private var allergiesArray = arrayOf("N/A", "Milk", "Eggs", "Peanuts", "Tree Nuts", "Soy", "Wheat", "Fish", "Shellfish", "Sesame", "Sulfites")
    val selectedAllergies = BooleanArray(11)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()

        getProfileInformation()
        setupProfileData()
        setUINotClickable()

        binding.btnToolbar.setOnClickListener {
            setUIToBeEditable()
            binding.btnConfirm.visibility = View.VISIBLE
        }

        binding.btnConfirm.setOnClickListener {
            binding.btnConfirm.visibility = View.GONE
            saveProfileInformation()
            setUINotClickable()
        }

        binding.rlAllergiesList.setOnClickListener {
            showAllergiesList()
        }
    }

    private fun showAllergiesList() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select allergies")
        builder.setCancelable(false)

        builder.setMultiChoiceItems(allergiesArray, selectedAllergies) { dialog, which, isChecked ->
            if (isChecked) {
                allergiesListDropdown.add(which)
            } else {
                allergiesListDropdown.remove(which)
            }
        }.setPositiveButton("Ok") { dialog, which ->
            val stringBuilder = StringBuilder()
            for (i in 0 until allergiesListDropdown.size) {
                stringBuilder.append(allergiesArray[allergiesListDropdown[i]])
                // Check condition
                if (i != allergiesListDropdown.size - 1) {
                    // When i value is not equal to the last index, add a comma
                    stringBuilder.append(", ")
                }
            }
            // Setting selected allergies to TextView
            binding.tvAllergiesList.text = stringBuilder.toString()
        }.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }.setNeutralButton("Clear all") { dialog, which ->
            // Clearing all selected allergies on clear all click
            for (i in selectedAllergies.indices) {
                selectedAllergies[i] = false
            }
            allergiesListDropdown.clear()
            binding.tvAllergiesList.text = ""
        }.show()
    }

    private fun saveProfileInformation() {

    }

    private fun setUIToBeEditable() {
        binding.apply {
            etProfileHeight.isFocusable = true
            etProfileHeight.isClickable = true
            etProfileWeight.isFocusable = true
            etProfileWeight.isClickable = true
            tvAllergiesList.isFocusable = true
            tvAllergiesList.isClickable = true
            etProfileFoodPreferences.isFocusable = true
            etProfileFoodPreferences.isClickable = true
            spinnerProfileGender.isEnabled = true
            spinnerProfileFitnessActivity.isEnabled = true
            ivAllergies.visibility = View.VISIBLE
        }
    }

    private fun setUINotClickable() {
        binding.apply {
            etProfileHeight.isFocusable = false
            etProfileHeight.isClickable = false
            etProfileWeight.isFocusable = false
            etProfileWeight.isClickable = false
            tvAllergiesList.isFocusable = false
            tvAllergiesList.isClickable = false
            etProfileFoodPreferences.isFocusable = false
            etProfileFoodPreferences.isClickable = false
            spinnerProfileGender.isEnabled = false
            spinnerProfileFitnessActivity.isEnabled = false
            ivAllergies.visibility = View.GONE
        }
    }

    private fun getProfileInformation() {
        val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        weight = sharedPref.getInt("weight", 0)
        height = sharedPref.getInt("height", 0)
        gender = sharedPref.getString("gender", "")!!
        fitnessLevel = sharedPref.getString("fitnessLevel", "")!!
        weightPreference = sharedPref.getString("weightPreference", "")!!
        foodPreferences = sharedPref.getString("foodPreferences", "")!!
        Log.d("test", "fitnessLevel is $fitnessLevel")

        val jsonString = sharedPref.getString("allergies", "")
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            allergiesList.add(jsonArray.getString(i))
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbarProfile
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Profile"
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setupProfileData() {
        for (i in 0 until allergiesList.size) {
            val builder = StringBuilder()
            if (allergiesList.size == 1) {
                builder.append(allergiesList[0])
            } else if( i == 0) {
                builder.append(allergies)
                    .append(allergiesList[i])
            } else {
                builder.append(allergies)
                    .append(", ")
                    .append(allergiesList[i])
            }

            allergies = builder.toString()
            binding.tvAllergiesList.text = allergies
        }

        binding.etProfileWeight.setText(weight.toString() + "kg")
        binding.etProfileHeight.setText(height.toString() + "cm")
        binding.etProfileFoodPreferences.setText(foodPreferences)

        val genderOptions = resources.getStringArray(R.array.genders)
        binding.spinnerProfileGender.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        val positionGender = (binding.spinnerProfileGender.adapter as ArrayAdapter<String>).getPosition(gender)
        binding.spinnerProfileGender.setSelection(positionGender)

        val fitnessLevelOptions = resources.getStringArray(R.array.fitnessLevel)
        binding.spinnerProfileFitnessActivity.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, fitnessLevelOptions)
        val positionFitness = (binding.spinnerProfileFitnessActivity.adapter as ArrayAdapter<String>).getPosition(fitnessLevel)
        binding.spinnerProfileFitnessActivity.setSelection(positionFitness)

        val weightPreferencesOptions = resources.getStringArray(R.array.weightPreferences)
        binding.spinnerProfileWeightPreferences.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, weightPreferencesOptions)
        val positionWeight = (binding.spinnerProfileWeightPreferences.adapter as ArrayAdapter<String>).getPosition(weightPreference)
        binding.spinnerProfileWeightPreferences.setSelection(positionWeight)
    }

}