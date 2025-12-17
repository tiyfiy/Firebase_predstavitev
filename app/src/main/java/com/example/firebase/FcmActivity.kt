package com.example.firebase

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.firebase.databinding.ActivityFcmBinding
import com.google.firebase.messaging.FirebaseMessaging

class FcmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFcmBinding
    private val CHANNEL_ID = "firebase_demo_channel"
    private val NOTIFICATION_PERMISSION_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFcmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        getFCMToken()
        setupClickListeners()
        checkForNotificationIntent()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Firebase Demo"
            val descriptionText = "Channel for Firebase Cloud Messaging demo"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                binding.tvToken.text = "Napaka pri pridobivanju tokena: ${task.exception?.message}"
                return@addOnCompleteListener
            }

            val token = task.result
            binding.tvToken.text = token
        }
    }

    private fun setupClickListeners() {
        binding.btnCopyToken.setOnClickListener {
            val token = binding.tvToken.text.toString()
            if (token.isNotEmpty() && !token.startsWith("Napaka") && !token.startsWith("Pridobivanje")) {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("FCM Token", token)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Token kopiran", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Token še ni na voljo", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSendNotification.setOnClickListener {
            val title = binding.etNotificationTitle.text.toString().trim()
            val body = binding.etNotificationBody.text.toString().trim()

            if (title.isEmpty() || body.isEmpty()) {
                Toast.makeText(this, "Vnesite naslov in vsebino", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sendLocalNotification(title, body)
        }
    }

    private fun sendLocalNotification(title: String, body: String) {
        // Check for notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
                return
            }
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }

        binding.tvLastNotification.text = "Naslov: $title\n\nVsebina: $body"
        Toast.makeText(this, "Obvestilo poslano", Toast.LENGTH_SHORT).show()

        // Clear input fields
        binding.etNotificationTitle.setText("")
        binding.etNotificationBody.setText("")
    }

    private fun checkForNotificationIntent() {
        // Check if activity was opened from notification
        intent.extras?.let { bundle ->
            val title = bundle.getString("title")
            val body = bundle.getString("body")

            if (title != null && body != null) {
                binding.tvLastNotification.text = "Naslov: $title\n\nVsebina: $body"
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Dovoljenje odobreno", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Dovoljenje zavrnjeno - obvestila ne bodo delovala",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
