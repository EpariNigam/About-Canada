package com.example.aboutcanada.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aboutcanada.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment(), MainFragment::class.java.simpleName).commit()
        }
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }
}