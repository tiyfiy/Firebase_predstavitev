package com.example.firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.ActivityRealtimeDbBinding
import com.example.firebase.databinding.ItemMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Message(
    val id: String = "",
    val sender: String = "",
    val message: String = "",
    val timestamp: Long = 0
)

class RealtimeDbActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealtimeDbBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealtimeDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        database = Firebase.database.reference
        auth = Firebase.auth

        // Check if user is logged in
        if (auth.currentUser == null) {
            Toast.makeText(
                this,
                "Prijavite se v Authentication za uporabo chata",
                Toast.LENGTH_LONG
            ).show()
        }

        setupRecyclerView()
        setupClickListeners()
        loadMessages()
    }

    private fun setupRecyclerView() {
        adapter = MessageAdapter(messages)
        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMessages.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.btnSend.setOnClickListener {
            val messageText = binding.etMessage.text.toString().trim()

            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
            } else {
                Toast.makeText(this, "Vnesite sporočilo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendMessage(messageText: String) {
        val currentUser = auth.currentUser
        val senderEmail = currentUser?.email ?: "Anonimen"

        val message = Message(
            id = "",
            sender = senderEmail,
            message = messageText,
            timestamp = System.currentTimeMillis()
        )

        database.child("messages").push().setValue(message)
            .addOnSuccessListener {
                binding.etMessage.setText("")
                // Scroll to bottom
                binding.recyclerViewMessages.scrollToPosition(messages.size - 1)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Napaka: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadMessages() {
        database.child("messages").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                message?.let {
                    val messageWithId = it.copy(id = snapshot.key ?: "")
                    messages.add(messageWithId)
                    adapter.notifyItemInserted(messages.size - 1)
                    binding.recyclerViewMessages.scrollToPosition(messages.size - 1)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Handle message updates if needed
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val messageId = snapshot.key
                val index = messages.indexOfFirst { it.id == messageId }
                if (index != -1) {
                    messages.removeAt(index)
                    adapter.notifyItemRemoved(index)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Not used in this implementation
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@RealtimeDbActivity,
                    "Napaka: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

class MessageAdapter(
    private val messages: List<Message>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.tvSender.text = message.sender
            binding.tvMessage.text = message.message
            binding.tvTimestamp.text = dateFormat.format(Date(message.timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size
}
