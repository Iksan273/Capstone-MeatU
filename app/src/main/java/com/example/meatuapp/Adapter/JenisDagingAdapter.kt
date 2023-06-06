package com.example.meatuapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meatuapp.Data.JenisDaging
import com.example.meatuapp.databinding.CardInfoDagingBinding

class JenisDagingAdapter (private val listJenisDaging:List<JenisDaging>):
        RecyclerView.Adapter<JenisDagingAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: CardInfoDagingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardInfoDagingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listJenisDaging.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.txtName.text=listJenisDaging[position].name
            binding.txtDeskripsi.text=listJenisDaging[position].description
        }
    }
}