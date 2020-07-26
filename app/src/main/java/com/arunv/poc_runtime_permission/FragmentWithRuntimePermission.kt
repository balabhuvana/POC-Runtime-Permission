package com.arunv.poc_runtime_permission


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fragment_with_runtime_permission.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentWithRuntimePermission : Fragment() {

    private lateinit var activityCameraRequestPermission: ActivityResultLauncher<String>
    private lateinit var activityRequestStoragePermission: ActivityResultLauncher<String>
    private lateinit var activityMultipleRequestPermission: ActivityResultLauncher<Array<String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_fragment_with_runtime_permission,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityCameraRequestPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    printLogEntry("activityCameraRequestPermission ", "Permission Granted")
                    openCamera()
                } else {
                    Snackbar.make(
                        this.requireView(),
                        R.string.camera_ui_require_string,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

        activityRequestStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    printLogEntry("activityRequestStoragePermission ", "Permission Granted")
                } else {
                    Snackbar.make(
                        this.requireView(),
                        R.string.storage_ui_require_string,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

        activityMultipleRequestPermission =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it.size >= 0) {
                    if (it.containsKey(Manifest.permission.CAMERA)) {
                        if (it.getValue(Manifest.permission.CAMERA)) {
                            printLogEntry("-----> ", "Camera Permission Granted")
                        } else {
                            printLogEntry("-----> ", "Camera Permission Not Granted")
                        }
                    } else if (it.containsKey(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (it.getValue(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            printLogEntry("-----> ", "READ_EXTERNAL_STORAGE Permission Granted")
                        } else {
                            printLogEntry("-----> ", "READ_EXTERNAL_STORAGE Permission Not Granted")
                        }
                    }
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnOpenCamera.setOnClickListener {
            handleCameraPermission()
        }

        btnReadStorage.setOnClickListener {
            handleStoragePermission()
        }

        btnMultiplePermission.setOnClickListener {
            handleMultiplePermission()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(cameraIntent)
    }

    private fun printLogEntry(methodName: String, message: String) {
        Log.i("-----> ", "Method name: $methodName - Message - $message")
    }

    private fun handleCameraPermission() {
        when {
            requireActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                printLogEntry("handleCameraPermission ", "All ready granted")
                openCamera()
            }
            requireActivity().shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {

                printLogEntry("setOnClickListener", "Show UI to user")

                Snackbar.make(
                    this.requireView(),
                    R.string.camera_ui_require_string,
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.action) {
                    activityCameraRequestPermission.launch(Manifest.permission.CAMERA)
                }.show()
            }
            else -> {
                activityCameraRequestPermission.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun handleStoragePermission() {
        when {
            requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                printLogEntry("handleStoragePermission ", "All ready granted")
            }
            requireActivity().shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {

                Snackbar.make(
                    this.requireView(),
                    R.string.storage_ui_require_string,
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.action) {
                    activityRequestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }.show()
            }
            else -> {
                activityRequestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun handleMultiplePermission() {
        val handleMultiplePermission: Array<String> =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

        activityMultipleRequestPermission.launch(handleMultiplePermission)
    }
}
