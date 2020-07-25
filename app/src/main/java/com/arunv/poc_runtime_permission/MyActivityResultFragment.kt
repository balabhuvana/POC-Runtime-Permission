package com.arunv.poc_runtime_permission


import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_my_activity_result.*

/**
 * A simple [Fragment] subclass.
 */
class MyActivityResultFragment : Fragment() {

    var activityRequestPermission: ActivityResultLauncher<String>? =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Log.i("-----> ", "Result $it")
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_activity_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLaunchCamera.setOnClickListener {
            activityRequestPermission!!.launch(Manifest.permission.CAMERA)
        }

    }


}
