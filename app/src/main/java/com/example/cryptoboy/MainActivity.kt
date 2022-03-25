package com.example.cryptoboy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptoboy.fragment.DataFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, DataFragment())
                .commitNow()
        }


    }
}