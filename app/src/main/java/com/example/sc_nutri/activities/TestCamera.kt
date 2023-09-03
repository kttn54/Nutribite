package com.example.sc_nutri.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.sc_nutri.*
import com.example.sc_nutri.R
import com.example.sc_nutri.databinding.ActivityTestCameraBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TestCamera : AppCompatActivity() {

    private lateinit var binding: ActivityTestCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var viewModel: FileViewModel
    var photoFile: File ?= null
    var filePath: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        outputDirectory = getOutputDirectory()

        val fileRepository = FileRepository()
        val viewModelFactory = FileViewModelFactory(fileRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FileViewModel::class.java]

        if (allPermissionGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnTakePhoto.setOnClickListener {
            takePhoto()
            binding.btnTakePhoto.visibility = View.GONE
            binding.btnRetake.visibility = View.VISIBLE
        }

        binding.btnRetake.setOnClickListener {
            binding.btnTakePhoto.visibility = View.VISIBLE
            binding.btnRetake.visibility = View.GONE
            startCamera()
        }
    }

    private fun uploadImage(file: File) {
        if (file.exists()) {
            Log.d(Constants.TAG, "File path: ${file.absolutePath}")

            viewModel.uploadImage(file)
        } else {
            Log.e(Constants.TAG, "File not found: ${file.absolutePath}")
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }

        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
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
            outputOption, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo Saved"

                    Toast.makeText(this@TestCamera,
                        "$msg $savedUri",
                        Toast.LENGTH_LONG).show()

                    Log.d(Constants.TAG,"Photo image path is $savedUri")

                    CropImage.activity(savedUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this@TestCamera)
                }



                override fun onError(exception: ImageCaptureException) {
                    Log.e(Constants.TAG,
                    "onError: ${exception.message}",
                    exception)
                }
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val croppedUri = result.uri

                // Convert the cropped URI to a File object
                val croppedFile = File(croppedUri.path ?: "")

                if (croppedFile.exists()) {
                    uploadImage(croppedFile)
                    Log.d(Constants.TAG,"Crop image path is $filePath")
                } else {
                    Log.e(Constants.TAG, "Cropped file not found")
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(this@TestCamera,
                    "Crop Image: Error",
                    Toast.LENGTH_LONG).show()

                Log.e(Constants.TAG, "Crop image error")
            }
        }
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
                Toast.makeText(this,
                    "Permissions not granted",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider
            .getInstance(this)

        cameraProviderFuture.addListener({

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()


            val preview = Preview.Builder()
                .build()
                .also { mPreview ->
                    mPreview.setSurfaceProvider(
                        binding.viewFinder.surfaceProvider
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
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }

    override fun onBackPressed() {
        // Check if the cropping activity is active
        if (CropImage.isExplicitCameraPermissionRequired(this)) {
            binding.btnTakePhoto.visibility = View.VISIBLE
            super.onBackPressed()
        } else {
            // Handle the back press as usual for other cases
            super.onBackPressed()
        }
    }
}