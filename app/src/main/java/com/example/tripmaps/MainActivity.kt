package com.example.tripmaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tripmaps.ui.map.MapFragment
import org.osmdroid.config.Configuration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MapFragment())
            .commit()
    }
}