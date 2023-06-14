package com.example.meatuapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.meatuapp.Data.JenisDaging
import com.example.meatuapp.ViewModel.LoginViewModel
import com.example.meatuapp.ViewModel.UserViewModelFactory
import com.example.meatuapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val userFactory = UserViewModelFactory.getInstance(requireActivity().application)
        val loginViewModel = ViewModelProvider(this, userFactory)[LoginViewModel::class.java]



        loginViewModel.getUser().observe(viewLifecycleOwner) { userLogin ->
            if (userLogin.nama.isNotEmpty()) {
                binding.txtName.setText(userLogin.nama)
            }
        }
        binding.card2.setOnClickListener {
            val intent = Intent(requireContext(), CiriDagingActivity::class.java)
            startActivity(intent)
        }
        binding.card1.setOnClickListener {
            val intent = Intent(requireContext(), JenisDagingActivity::class.java)
            startActivity(intent)
        }
        binding.card3.setOnClickListener {
            val intent = Intent(requireContext(), MeatUActivity::class.java)
            startActivity(intent)
        }

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}