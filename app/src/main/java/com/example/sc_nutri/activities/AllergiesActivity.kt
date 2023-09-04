package com.example.sc_nutri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.ActivityAllergiesBinding

class AllergiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllergiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAllergiesContinue.setOnClickListener {
            val intent = Intent(this, FoodPreferencesActivity::class.java)
            startActivity(intent)
        }
    }
}