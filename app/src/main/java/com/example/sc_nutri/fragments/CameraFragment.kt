package com.example.sc_nutri.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.sc_nutri.databinding.FragmentCameraBinding

class CameraFragment: Fragment() {

    companion object {
        private const val REQUEST_CAMERA_CODE = 1
    }

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
        //getPermissions()
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbarCamera
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Image Analysis"
            setDisplayHomeAsUpEnabled(false)
        }
    }

/*
    private fun getPermissions() {
        var permissionsList = mutableListOf<String>()

        if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.CAMERA)
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }*/
}