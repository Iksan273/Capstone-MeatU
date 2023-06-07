package com.example.meatuapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meatuapp.Data.CiriDaging
import com.example.meatuapp.databinding.CiriDagingBinding


class CiriDagingAdapter (private val listCiriDaging:List<CiriDaging>):
    RecyclerView.Adapter<CiriDagingAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: CiriDagingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CiriDagingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCiriDaging.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.textViewTitle.text=listCiriDaging[position].title
            binding.textViewDescription.text=listCiriDaging[position].description
            Glide.with(binding.root.context)
                .load(listCiriDaging[position].url)
                .into(binding.imageView)
        }
    }

    }