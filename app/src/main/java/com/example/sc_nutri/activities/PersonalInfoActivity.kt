package com.example.sc_nutri.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.ActivityPersonalInfoBinding

class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialiseUI()

        binding.btnPersonalInfoContinue.setOnClickListener {
            val sharedPref = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            val weight = binding.etWeight.text.toString().toInt()
            val height = binding.etHeight.text.toString().toInt()
            val age = binding.etAge.text.toString().toInt()
            val gender = binding.spinnerGender.selectedItem.toString()
            val fitnessLevel = binding.spinnerFitnessLevel.selectedItem.toString()
            val weightPreferences = binding.spinnerGainLoseWeight.selectedItem.toString()

            editor.putInt("weight", weight)
            editor.putInt("height", height)
            editor.putInt("age", age)
            editor.putString("gender", gender)
            editor.putString("fitnessLevel", fitnessLevel)
            editor.putString("weightPreference", weightPreferences)
            editor.apply()

            val startIntent = Intent(this, AllergiesActivity::class.java)
            startActivity(startIntent)
        }
    }

    private fun initialiseUI() {
        val genderOptions = resources.getStringArray(R.array.genders)
        binding.spinnerGender.adapter = ArrayAdapter(this, R.layout.custom_spinner_item, R.id.spinner_item_text, genderOptions)

        val fitnessActivityOptions = resources.getStringArray(R.array.fitnessLevel)
        binding.spinnerFitnessLevel.adapter = ArrayAdapter(this, R.layout.custom_spinner_item, R.id.spinner_item_text, fitnessActivityOptions)

        val weightPreferencesOptions = resources.getStringArray(R.array.weightPreferences)
        binding.spinnerGainLoseWeight.adapter = ArrayAdapter(this, R.layout.custom_spinner_item, R.id.spinner_item_text, weightPreferencesOptions)

    }
}