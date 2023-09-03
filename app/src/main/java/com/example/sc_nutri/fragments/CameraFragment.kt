package com.example.sc_nutri.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.sc_nutri.databinding.FragmentCameraBinding

class CameraFragment: Fragment() {

    private lateinit var binding: FragmentCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()

        binding.btnStartCamera.setOnClickListener {
            binding.btnStartCamera.visibility = View.GONE
            binding.btnCancelCamera.visibility = View.VISIBLE
            binding.btnCapture.visibility = View.VISIBLE
            binding.tvText.visibility = View.GONE
            binding.ivClearImageExample.visibility = View.GONE
        }

        binding.btnCancelCamera.setOnClickListener {
            binding.btnStartCamera.visibility = View.VISIBLE
            binding.btnCancelCamera.visibility = View.GONE
            binding.btnCapture.visibility = View.GONE
            binding.tvText.visibility = View.VISIBLE
            binding.ivClearImageExample.visibility = View.VISIBLE
        }

        binding.btnCapture.setOnClickListener {
            binding.btnCapture.visibility = View.GONE
            binding.btnCancelCamera.visibility = View.GONE
            binding.btnAnalyse.visibility = View.VISIBLE
            binding.btnRetake.visibility = View.VISIBLE
        }

        binding.btnRetake.setOnClickListener {
            binding.btnCapture.visibility = View.VISIBLE
            binding.btnRetake.visibility = View.GONE
            binding.btnAnalyse.visibility = View.GONE
            binding.btnCancelCamera.visibility = View.VISIBLE
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbarCamera
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Image Analysis"
            setDisplayHomeAsUpEnabled(false)
        }
    }
}