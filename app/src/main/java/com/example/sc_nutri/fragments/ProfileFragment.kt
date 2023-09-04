package com.example.sc_nutri.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.FragmentProfileBinding
import org.json.JSONArray

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var weight: String
    private lateinit var height: String
    private lateinit var gender: String
    private var bmr = 0
    private lateinit var fitnessLevel: String
    private var allergiesList: ArrayList<String> = ArrayList()

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
    }

    private fun getProfileInformation() {
        val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        weight = sharedPref.getString("weight", "")!!
        height = sharedPref.getString("height", "")!!
        gender = sharedPref.getString("gender", "")!!
        fitnessLevel = sharedPref.getString("fitnessLevel", "")!!

        val jsonString = sharedPref.getString("allergies", "")

        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            allergiesList.add(jsonArray.getString(i))
        }

        Log.d("test","allergies is $allergiesList")
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbarProfile
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Profile"
            setDisplayHomeAsUpEnabled(false)
        }
    }

}