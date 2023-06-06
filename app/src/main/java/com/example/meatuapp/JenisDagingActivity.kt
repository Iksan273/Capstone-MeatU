package com.example.meatuapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meatuapp.Adapter.JenisDagingAdapter
import com.example.meatuapp.Data.JenisDaging
import com.example.meatuapp.ViewModel.DagingViewModel
import com.example.meatuapp.ViewModel.DagingViewModelFactory
import com.example.meatuapp.databinding.ActivityJenisDagingBinding


class JenisDagingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJenisDagingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jenis_daging)

        binding = ActivityJenisDagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        binding.rxJenisDaging.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rxJenisDaging.addItemDecoration(itemDecoration)

        val dagingFactory=DagingViewModelFactory.getInstance(this)
        val dagingViewModel=ViewModelProvider(this,dagingFactory)[DagingViewModel::class.java]

        dagingViewModel.listJenisDaging.observe(this){
            daging->setData(daging)
        }


    }
    private fun setData(user: List<JenisDaging>) {
        val adapter = JenisDagingAdapter(user)
        binding.rxJenisDaging.adapter = adapter

    }
}