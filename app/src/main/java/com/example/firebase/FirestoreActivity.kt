package com.example.firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.ActivityFirestoreBinding
import com.example.firebase.databinding.ItemFirestoreBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Item(
    val id: String = "",
    val name: String = "",
    val description: String = ""
)

class FirestoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirestoreBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: ItemAdapter
    private val items = mutableListOf<Item>()
    private var selectedItemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore
        db = Firebase.firestore

        setupRecyclerView()
        setupClickListeners()
        loadItems()
    }

    private fun setupRecyclerView() {
        adapter = ItemAdapter(
            items = items,
            onDeleteClick = { item -> deleteItem(item) },
            onItemClick = { item -> selectItem(item) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (name.isNotEmpty()) {
                addItem(name, description)
            } else {
                Toast.makeText(this, "Ime ne sme biti prazno", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUpdate.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (selectedItemId != null && name.isNotEmpty()) {
                updateItem(selectedItemId!!, name, description)
            } else {
                Toast.makeText(
                    this,
                    "Izberite element in vnesite ime",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addItem(name: String, description: String) {
        val item = hashMapOf(
            "name" to name,
            "description" to description,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("items")
            .add(item)
            .addOnSuccessListener {
                Toast.makeText(this, "Element dodan", Toast.LENGTH_SHORT).show()
                clearFields()
                loadItems()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Napaka: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateItem(id: String, name: String, description: String) {
        val updates = hashMapOf<String, Any>(
            "name" to name,
            "description" to description
        )

        db.collection("items")
            .document(id)
            .update(updates)
            .addOnSuccessListener {
                Toast.makeText(this, "Element posodobljen", Toast.LENGTH_SHORT).show()
                clearFields()
                selectedItemId = null
                loadItems()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Napaka: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteItem(item: Item) {
        db.collection("items")
            .document(item.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Element izbrisan", Toast.LENGTH_SHORT).show()
                loadItems()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Napaka: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadItems() {
        db.collection("items")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Toast.makeText(this, "Napaka pri nalaganju: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                    return@addSnapshotListener
                }

                items.clear()
                snapshot?.documents?.forEach { doc ->
                    val item = Item(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        description = doc.getString("description") ?: ""
                    )
                    items.add(item)
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun selectItem(item: Item) {
        selectedItemId = item.id
        binding.etName.setText(item.name)
        binding.etDescription.setText(item.description)
        Toast.makeText(this, "Izbran: ${item.name}", Toast.LENGTH_SHORT).show()
    }

    private fun clearFields() {
        binding.etName.setText("")
        binding.etDescription.setText("")
    }
}

class ItemAdapter(
    private val items: List<Item>,
    private val onDeleteClick: (Item) -> Unit,
    private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemFirestoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.tvName.text = item.name
            binding.tvDescription.text = item.description

            binding.btnDelete.setOnClickListener {
                onDeleteClick(item)
            }

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemFirestoreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
