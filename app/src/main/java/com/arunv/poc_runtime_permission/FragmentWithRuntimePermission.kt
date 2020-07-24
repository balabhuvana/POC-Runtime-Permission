package com.arunv.poc_runtime_permission


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_fragment_with_runtime_permission.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentWithRuntimePermission : Fragment() {

    private lateinit var activityCameraRequestPermission: ActivityResultLauncher<String>
    private lateinit var activityRequestStoragePermission: ActivityResultLauncher<String>

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
                Log.i("-----> ", "$it")
                if (it) {
                    openCamera()
                }
            }

        activityRequestStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                Log.i("-----> ", "$it")
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnOpenCamera.setOnClickListener {
            activityCameraRequestPermission.launch(Manifest.permission.CAMERA)
        }

        btnReadStorage.setOnClickListener {
            activityRequestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(cameraIntent)
    }

}
