package com.example.meatuapp.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meatuapp.Adapter.CiriDagingAdapter
import com.example.meatuapp.Data.CiriDaging
import com.example.meatuapp.R
import com.example.meatuapp.ViewModel.CiriDagingViewModel
import com.example.meatuapp.ViewModel.CiriDagingViewModelFactory

import com.example.meatuapp.databinding.ActivityCiriDagingBinding


class CiriDagingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCiriDagingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciri_daging)

        binding = ActivityCiriDagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        binding.rvCiriDaging.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvCiriDaging.addItemDecoration(itemDecoration)

        var ciriFactory=CiriDagingViewModelFactory.getInstance(this)
        var ciriDagingViewModel= ViewModelProvider(this,ciriFactory)[CiriDagingViewModel::class.java]

        ciriDagingViewModel.listCiriDaging.observe(this){
                ciriDaging->setData(ciriDaging)
        }

    }
    private fun setData(data: List<CiriDaging>) {
        val adapter = CiriDagingAdapter(data)
        binding.rvCiriDaging.adapter = adapter

    }
}