package com.arunv.poc_runtime_permission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchFragment()

    }

    private fun launchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.myFrameLayout, FragmentWithRuntimePermission()).commit()
    }
}
