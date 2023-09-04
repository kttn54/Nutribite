package com.example.sc_nutri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sc_nutri.FileRepository
import com.example.sc_nutri.FileViewModel
import com.example.sc_nutri.FileViewModelFactory
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.ActivityMainBinding
import com.example.sc_nutri.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val viewModel: FileViewModel by lazy {
        val fileRepository = FileRepository()
        val fileViewModelFactory = FileViewModelFactory(fileRepository)
        ViewModelProvider(this, fileViewModelFactory)[FileViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.host_fragment)
        binding.btmNav.setupWithNavController(navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val fragment = supportFragmentManager.findFragmentById(R.id.cameraFragment)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }
}