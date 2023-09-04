package com.example.sc_nutri.activities

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
            val intent = Intent(this, AllergiesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initialiseUI() {
        val genderOptions = resources.getStringArray(R.array.genders)
        binding.spinnerGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)

        val fitnessActivityOptions = resources.getStringArray(R.array.fitness_level)
        binding.spinnerGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fitnessActivityOptions)
    }
}