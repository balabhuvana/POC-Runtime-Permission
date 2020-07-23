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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_fragment_with_runtime_permission.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentWithRuntimePermission : Fragment() {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnOpenCamera.setOnClickListener {
            handleRunTimePermission(Manifest.permission.CAMERA, PERMISSION_REQUEST_CODE_CAMERA)
        }

        btnReadStorage.setOnClickListener {
            handleRunTimePermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                PERMISSION_REQUEST_CODE_STORAGE
            )
        }

        btnWriteStorage.setOnClickListener {

        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(cameraIntent)
    }

    private fun handleRunTimePermission(permissionName: String, permissionRequestCode: Int) {
        if (permissionRequestCode == PERMISSION_REQUEST_CODE_CAMERA) {
            when {
                ContextCompat.checkSelfPermission(
                    this.context!!,
                    permissionName
                ) == PackageManager.PERMISSION_GRANTED -> {
                    printLogFile("handleRunTimePermission", permissionName, "PERMISSION_GRANTED")
                    //openCamera()
                }
                shouldShowRequestPermissionRationale(permissionName) -> {
                    printLogFile(
                        "handleRunTimePermission",
                        permissionName,
                        "shouldShowRequestPermissionRationale"
                    )
                    requestPermissions(arrayOf(permissionName), permissionRequestCode)
                }
                else -> {
                    printLogFile(
                        "handleRunTimePermission",
                        permissionName,
                        "PERMISSION_NOT_GRANTED"
                    )
                    requestPermissions(arrayOf(permissionName), permissionRequestCode)
                }
            }
        } else if (permissionRequestCode == PERMISSION_REQUEST_CODE_STORAGE) {
            when {
                ContextCompat.checkSelfPermission(
                    this.context!!,
                    permissionName
                ) == PackageManager.PERMISSION_GRANTED -> {
                    printLogFile("handleRunTimePermission", permissionName, "PERMISSION_GRANTED")
                }
                shouldShowRequestPermissionRationale(permissionName) -> {
                    printLogFile(
                        "handleRunTimePermission",
                        permissionName,
                        "shouldShowRequestPermissionRationale"
                    )
                    requestPermissions(arrayOf(permissionName), permissionRequestCode)
                }
                else -> {
                    printLogFile(
                        "handleRunTimePermission",
                        permissionName,
                        "PERMISSION_NOT_GRANTED"
                    )
                    requestPermissions(arrayOf(permissionName), permissionRequestCode)
                }
            }
        }
    }

    private fun printLogFile(methodName: String, text: String, subText: String) {
        Log.i("-----> ", " $methodName - $text - $subText")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.

                    printLogFile("onRequestPermissionsResult", "", "PERMISSION_GRANTED")
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.

                    printLogFile("onRequestPermissionsResult", "", "PERMISSION_NOT_GRANTED")
                }
                return
            }
            PERMISSION_REQUEST_CODE_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.

                    printLogFile("onRequestPermissionsResult", "", "PERMISSION_GRANTED")
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.

                    printLogFile("onRequestPermissionsResult", "", "PERMISSION_NOT_GRANTED")
                }
                return
            }
        }
    }


    companion object {
        private const val PERMISSION_REQUEST_CODE_CAMERA = 10001
        private const val PERMISSION_REQUEST_CODE_STORAGE = 10002
    }

}
