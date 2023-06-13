package com.example.meatuapp

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.meatuapp.databinding.FragmentScanBinding
import java.io.File

class ScanFragment : Fragment() {
    companion object {
        private var myfile: File? = null
        private var path: String = ""
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 15

        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ScanViewModel

    private var _binding: FragmentScanBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btncam.setOnClickListener {
            takePhoto()
        }
        binding.btnGallery.setOnClickListener {
            takeFromGallery()
        }
        binding.button3.setOnClickListener {
            val intent = Intent(requireContext(), PredictActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val myFile = File(path)

            myFile.let { file ->
                myfile = file
                Glide.with(requireContext())
                    .load(myFile)
                    .into(binding.imageView)
            }
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireContext().packageManager)

        createTempFile(requireContext().applicationContext).also { file ->
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.meatuapp.mycamera",
                file
            )
            path = file.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectImg = result.data?.data as Uri
            selectImg.let { uri ->
                val myUri = uriToFile(uri, requireContext())
                myfile = myUri
                Glide.with(this)
                    .load(myfile)
                    .into(binding.imageView)
            }
        }
    }

    private fun takeFromGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }




}