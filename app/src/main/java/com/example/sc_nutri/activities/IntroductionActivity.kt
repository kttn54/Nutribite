package com.example.sc_nutri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.ActivityIntroductionBinding

class IntroductionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroductionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLetsGetStarted.setOnClickListener {
            val intent = Intent(this, FoodPreferencesActivity::class.java)
            startActivity(intent)
        }
    }
}