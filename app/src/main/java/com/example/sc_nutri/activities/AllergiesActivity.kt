package com.example.sc_nutri.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.ActivityAllergiesBinding
import org.json.JSONArray

class AllergiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllergiesBinding
    private var isNAPressed = false
    private var isMilkPressed = false
    private var isEggsPressed = false
    private var isPeanutsPressed = false
    private var isTreenutsPressed = false
    private var isSoyPressed = false
    private var isWheatPressed = false
    private var isFishPressed = false
    private var isShellfishPressed = false
    private var isSesamePressed = false
    private var isSulfitesPressed = false
    private var allergiesList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAllergiesContinue.setOnClickListener {
            val intent = Intent(this, FoodPreferencesActivity::class.java)

            if (isNAPressed) {
                allergiesList.add("N/A")
            }
            if (isMilkPressed) {
                allergiesList.add("Milk")
            }
            if (isEggsPressed) {
                allergiesList.add("Eggs")
            }
            if (isPeanutsPressed) {
                allergiesList.add("Peanuts")
            }
            if (isTreenutsPressed) {
                allergiesList.add("Tree Nuts")
            }
            if (isSoyPressed) {
                allergiesList.add("Soy")
            }
            if (isWheatPressed) {
                allergiesList.add("Wheat")
            }
            if (isFishPressed) {
                allergiesList.add("Fish")
            }
            if (isShellfishPressed) {
                allergiesList.add("Shellfish")
            }
            if (isSesamePressed) {
                allergiesList.add("Sesame")
            }
            if (isSulfitesPressed) {
                allergiesList.add("Sulfites")
            }
            val jsonArray = JSONArray()
            for (item in allergiesList) {
                jsonArray.put(item)
            }

            val jsonString = jsonArray.toString()
            val sharedPrefs = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("allergies", jsonString)
            editor.apply()

            startActivity(intent)
        }

        binding.tvNa.setOnClickListener {
            isNAPressed = !isNAPressed
            updateTextViewBackground(binding.tvNa, isNAPressed)

            if (isNAPressed) {
                binding.tvMilk.isClickable = false
                binding.tvMilk.isFocusable = false
                binding.tvEggs.isClickable = false
                binding.tvEggs.isFocusable = false
                binding.tvPeanuts.isClickable = false
                binding.tvPeanuts.isFocusable = false
                binding.tvTreeNuts.isClickable = false
                binding.tvTreeNuts.isFocusable = false
                binding.tvSoy.isClickable = false
                binding.tvSoy.isFocusable = false
                binding.tvWheat.isClickable = false
                binding.tvWheat.isFocusable = false
                binding.tvFish.isClickable = false
                binding.tvFish.isFocusable = false
                binding.tvShellfish.isClickable = false
                binding.tvShellfish.isFocusable = false
                binding.tvSesame.isClickable = false
                binding.tvSesame.isFocusable = false
                binding.tvSulfites.isClickable = false
                binding.tvSulfites.isFocusable = false
                isMilkPressed = false
                isEggsPressed = false
                isPeanutsPressed = false
                isTreenutsPressed = false
                isSoyPressed = false
                isWheatPressed = false
                isFishPressed = false
                isShellfishPressed = false
                isSesamePressed = false
                isSulfitesPressed = false

                binding.tvMilk.setBackgroundResource(R.drawable.textview_disabled_background)
                binding.tvEggs.setBackgroundResource(R.drawable.textview_disabled_background)
                binding.tvPeanuts.setBackgroundResource(R.drawable.textview_disabled_background)
                binding.tvTreeNuts.setBackgroundResource(R.drawable.textview_disabled_background)
                binding.tvSoy.setBackgroundResource(R.drawable.textview_disabled_background)
                binding.tvWheat.setBackgroundResource(R.drawable.textview_disabled_background)
                binding.tvFish.setBackgroundResource(R.drawable.textview_disabled_background)
                binding.tvShellfish.setBackgroundResource(R.drawable.textview_disabled_background)
                binding.tvSesame.setBackgroundResource(R.drawable.textview_disabled_background)
                binding.tvSulfites.setBackgroundResource(R.drawable.textview_disabled_background)
            } else {
                binding.tvMilk.isClickable = true
                binding.tvMilk.isClickable = true
                binding.tvEggs.isClickable = true
                binding.tvEggs.isFocusable = true
                binding.tvPeanuts.isClickable = true
                binding.tvPeanuts.isFocusable = true
                binding.tvTreeNuts.isClickable = true
                binding.tvTreeNuts.isFocusable = true
                binding.tvSoy.isClickable = true
                binding.tvSoy.isFocusable = true
                binding.tvWheat.isClickable = true
                binding.tvWheat.isFocusable = true
                binding.tvFish.isClickable = true
                binding.tvFish.isFocusable = true
                binding.tvShellfish.isClickable = true
                binding.tvShellfish.isFocusable = true
                binding.tvSesame.isClickable = true
                binding.tvSesame.isFocusable = true
                binding.tvSulfites.isClickable = true
                binding.tvSulfites.isFocusable = true
                isMilkPressed = false
                isEggsPressed = false
                isPeanutsPressed = false
                isTreenutsPressed = false
                isSoyPressed = false
                isWheatPressed = false
                isFishPressed = false
                isShellfishPressed = false
                isSesamePressed = false
                isSulfitesPressed = false

                binding.tvMilk.setBackgroundResource(R.drawable.shape_textview_rounded)
                binding.tvEggs.setBackgroundResource(R.drawable.shape_textview_rounded)
                binding.tvPeanuts.setBackgroundResource(R.drawable.shape_textview_rounded)
                binding.tvTreeNuts.setBackgroundResource(R.drawable.shape_textview_rounded)
                binding.tvSoy.setBackgroundResource(R.drawable.shape_textview_rounded)
                binding.tvWheat.setBackgroundResource(R.drawable.shape_textview_rounded)
                binding.tvFish.setBackgroundResource(R.drawable.shape_textview_rounded)
                binding.tvShellfish.setBackgroundResource(R.drawable.shape_textview_rounded)
                binding.tvSesame.setBackgroundResource(R.drawable.shape_textview_rounded)
                binding.tvSulfites.setBackgroundResource(R.drawable.shape_textview_rounded)
            }
        }

        binding.tvMilk.setOnClickListener {
            isMilkPressed = !isMilkPressed
            updateTextViewBackground(binding.tvMilk, isMilkPressed)
        }

        binding.tvEggs.setOnClickListener {
            isEggsPressed = !isEggsPressed
            updateTextViewBackground(binding.tvEggs, isEggsPressed)
        }

        binding.tvPeanuts.setOnClickListener {
            isPeanutsPressed = !isPeanutsPressed
            updateTextViewBackground(binding.tvPeanuts, isPeanutsPressed)
        }

        binding.tvTreeNuts.setOnClickListener {
            isTreenutsPressed = !isTreenutsPressed
            updateTextViewBackground(binding.tvTreeNuts, isTreenutsPressed)
        }

        binding.tvSoy.setOnClickListener {
            isSoyPressed = !isSoyPressed
            updateTextViewBackground(binding.tvSoy, isSoyPressed)
        }

        binding.tvWheat.setOnClickListener {
            isWheatPressed = !isWheatPressed
            updateTextViewBackground(binding.tvWheat, isWheatPressed)
        }

        binding.tvFish.setOnClickListener {
            isFishPressed = !isFishPressed
            updateTextViewBackground(binding.tvFish, isFishPressed)
        }

        binding.tvShellfish.setOnClickListener {
            isShellfishPressed = !isShellfishPressed
            updateTextViewBackground(binding.tvShellfish, isShellfishPressed)
        }

        binding.tvSesame.setOnClickListener {
            isSesamePressed = !isSesamePressed
            updateTextViewBackground(binding.tvSesame, isSesamePressed)
        }

        binding.tvSulfites.setOnClickListener {
            isSulfitesPressed = !isSulfitesPressed
            updateTextViewBackground(binding.tvSulfites, isSulfitesPressed)
        }
    }

    private fun updateTextViewBackground(textview: TextView, isTextViewPressed: Boolean) {
        if (isTextViewPressed) {
            textview.setBackgroundResource(R.drawable.textview_highlighted_background)
            textview.setTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            textview.setBackgroundResource(R.drawable.shape_textview_rounded)
            textview.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }
}