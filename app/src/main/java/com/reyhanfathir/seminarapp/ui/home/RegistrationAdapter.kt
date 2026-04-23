package com.reyhanfathir.seminarapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reyhanfathir.seminarapp.databinding.ItemRegistrationBinding
import com.reyhanfathir.seminarapp.model.Registration
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistrationAdapter(
    private var items: List<Registration>,
    private val onDeleteClick: (Registration) -> Unit
) : RecyclerView.Adapter<RegistrationAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id"))

    fun submit(newItems: List<Registration>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRegistrationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemRegistrationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Registration) {
            binding.tvInitial.text = item.nama.firstOrNull()?.uppercase() ?: "?"
            binding.tvNama.text = item.nama
            binding.tvGender.text = item.gender
            binding.tvEmail.text = item.email
            binding.tvPhone.text = item.phone
            binding.tvSeminar.text = item.seminar
            binding.tvTime.text = dateFormat.format(Date(item.timestamp))
            binding.btnDelete.setOnClickListener { onDeleteClick(item) }
        }
    }
}
