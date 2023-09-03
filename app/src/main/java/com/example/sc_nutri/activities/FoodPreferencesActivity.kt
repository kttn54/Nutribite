package com.example.sc_nutri.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.ActivityFoodPreferencesBinding

class FoodPreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodPreferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}