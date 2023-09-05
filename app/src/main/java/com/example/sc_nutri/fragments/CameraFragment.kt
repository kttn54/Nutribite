package com.example.sc_nutri.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.sc_nutri.FileRepository
import com.example.sc_nutri.FileViewModel
import com.example.sc_nutri.FileViewModelFactory
import com.example.sc_nutri.R
import com.example.sc_nutri.activities.AllergiesActivity
import com.example.sc_nutri.activities.Constants
import com.example.sc_nutri.activities.MainActivity
import com.example.sc_nutri.databinding.FragmentCameraBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment: Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private lateinit var viewModel: FileViewModel
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private var photoFile: File?= null
    private var croppedFilePath: String? = null
    private var croppedFile: File? = null
    private var filePath: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        setupButtonBindings()

        // Add the back press callback
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)

        outputDirectory = getOutputDirectory(requireContext())

        if (allPermissionGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
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

    private fun setupButtonBindings() {
        binding.btnStartCamera.setOnClickListener {
            binding.btnStartCamera.visibility = View.GONE
            binding.btnCancelCamera.visibility = View.VISIBLE
            binding.btnCapture.visibility = View.VISIBLE
            binding.tvText.visibility = View.GONE
            binding.ivClearImageExample.visibility = View.GONE
            binding.cameraViewFinder.visibility = View.VISIBLE
        }

        binding.btnCancelCamera.setOnClickListener {
            binding.btnStartCamera.visibility = View.VISIBLE
            binding.btnCancelCamera.visibility = View.GONE
            binding.btnCapture.visibility = View.GONE
            binding.tvText.visibility = View.VISIBLE
            binding.ivClearImageExample.visibility = View.VISIBLE
            binding.cameraViewFinder.visibility = View.GONE
        }

        binding.btnCapture.setOnClickListener {
            binding.btnCapture.visibility = View.GONE
            binding.btnCancelCamera.visibility = View.GONE
            binding.btnAnalyse.visibility = View.VISIBLE
            binding.btnRetake.visibility = View.VISIBLE
            takePhoto()
        }

        binding.btnRetake.setOnClickListener {
            binding.btnCapture.visibility = View.VISIBLE
            binding.btnRetake.visibility = View.GONE
            binding.btnAnalyse.visibility = View.GONE
            binding.btnCancelCamera.visibility = View.VISIBLE
            binding.ivCroppedImage.visibility = View.GONE
            binding.cameraViewFinder.visibility = View.VISIBLE
        }

        binding.btnAnalyse.setOnClickListener {
            val fileName = "test_image"
            val file = File(requireContext().cacheDir, fileName)

            Log.d("FilePath", "File path: ${file.absolutePath}")

            try {
                val inputStream = requireContext().assets.open("sausagesinfo.jpg")
                val outputStream = FileOutputStream(file)

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            uploadImage(file)

            //uploadImage(croppedFile!!)
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val currentTime = SimpleDateFormat(Constants.FILE_NAME_FORMAT,
            Locale.getDefault())
            .format(System
                .currentTimeMillis())
        filePath = "IMG_$currentTime.jpg"

        photoFile = File(
            outputDirectory,
            filePath!!
        )

        val outputOption = ImageCapture
            .OutputFileOptions
            .Builder(photoFile!!)
            .build()

        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo Saved"

                    Log.d(Constants.TAG,"Photo image path is $savedUri")

                    CropImage.activity(savedUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(requireActivity(), this@CameraFragment)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(Constants.TAG,
                        "onError: ${exception.message}",
                        exception)
                }
            }
        )
    }

    private fun uploadImage(file: File) {
        if (file.exists()) {
            Log.d(Constants.TAG, "File path: ${file.absolutePath}")

            viewModel.uploadImage(file)
        } else {
            Log.e(Constants.TAG, "File not found: ${file.absolutePath}")
        }
    }

    private fun getOutputDirectory(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }

        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val croppedUri = result.uri

                // Convert the cropped URI to a File object
                croppedFile = File(croppedUri.path ?: "")

                if (croppedFile!!.exists()) {
                    croppedFilePath = croppedFile!!.absolutePath
                    displayCroppedImage()
                    Log.d(Constants.TAG,"Crop image path is $filePath")
                } else {
                    Log.e(Constants.TAG, "Cropped file not found")
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(requireContext(),
                    "Crop Image: Error",
                    Toast.LENGTH_LONG).show()

                Log.e(Constants.TAG, "Crop image error")
            }
        }
    }

    private fun displayCroppedImage() {
        binding.ivCroppedImage.visibility = View.VISIBLE
        binding.cameraViewFinder.visibility = View.GONE
        binding.ivCroppedImage.setImageURI(Uri.fromFile(File(croppedFilePath ?: "")))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionGranted()) {
                startCamera()
            } else {
                Toast.makeText(requireContext(),
                    "Permissions not granted",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider
            .getInstance(requireContext())

        cameraProviderFuture.addListener({

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()


            val preview = Preview.Builder()
                .build()
                .also { mPreview ->
                    mPreview.setSurfaceProvider(
                        binding.cameraViewFinder.surfaceProvider
                    )
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector,
                    preview, imageCapture
                )

            } catch (e: Exception) {
                Log.d(Constants.TAG, "startCamera Fail:", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionGranted(): Boolean {
        val context = requireContext()
        return Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                context, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (CropImage.isExplicitCameraPermissionRequired(requireContext())) {
                binding.btnCapture.visibility = View.VISIBLE
                binding.btnCancelCamera.visibility = View.VISIBLE
            } else {
                isEnabled = false // Disable the callback and allow the default behavior
                requireActivity().onBackPressed()
            }
        }
    }
}