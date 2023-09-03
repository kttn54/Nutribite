package com.example.sc_nutri.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.ActivityAllergiesBinding

class AllergiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllergiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}