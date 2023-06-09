package com.example.meatuapp.ui.dashboard

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meatuapp.ViewModel.LoginViewModel
import com.example.meatuapp.ViewModel.UserViewModelFactory
import com.example.meatuapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }



    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val userFactory = UserViewModelFactory.getInstance(requireActivity().application)
        val loginViewModel = ViewModelProvider(this, userFactory)[LoginViewModel::class.java]

        loginViewModel.getUser().observe(viewLifecycleOwner) { userLogin ->
            if (userLogin.nama.isNotEmpty()) {
                binding.txtName.setText(userLogin.nama)
                binding.txtUsername.setText(userLogin.email)
            }
        }

        binding.btnLogout.setOnClickListener {
            loginViewModel.deleteUser()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}