package com.example.firebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnAuth.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }

        binding.btnFirestore.setOnClickListener {
            startActivity(Intent(this, FirestoreActivity::class.java))
        }

        binding.btnRealtimeDb.setOnClickListener {
            startActivity(Intent(this, RealtimeDbActivity::class.java))
        }

        binding.btnFcm.setOnClickListener {
            startActivity(Intent(this, FcmActivity::class.java))
        }
    }
}