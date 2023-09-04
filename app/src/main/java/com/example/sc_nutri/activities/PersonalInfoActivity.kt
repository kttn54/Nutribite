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

        val sharedPref = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        binding.btnPersonalInfoContinue.setOnClickListener {
            val weight = binding.etWeight.toString()
            val height = binding.etHeight.toString()
            val gender = binding.spinnerGender.selectedItem.toString()
            val fitnessLevel = binding.spinnerFitnessLevel.selectedItem.toString()

            editor.putString("weight", weight)
            editor.putString("height", height)
            editor.putString("gender", gender)
            editor.putString("fitnessLevel", fitnessLevel)
            editor.apply()

            val startIntent = Intent(this, AllergiesActivity::class.java)
            startActivity(startIntent)
        }
    }

    private fun initialiseUI() {
        val genderOptions = resources.getStringArray(R.array.genders)
        binding.spinnerGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)

        val fitnessActivityOptions = resources.getStringArray(R.array.fitness_level)
        binding.spinnerFitnessLevel.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fitnessActivityOptions)
    }
}