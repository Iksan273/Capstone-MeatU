package com.example.meatuapp

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.meatuapp.ViewModel.LoginViewModel
import com.example.meatuapp.ViewModel.PredictViewModel
import com.example.meatuapp.ViewModel.PredictViewModelFactory
import com.example.meatuapp.ViewModel.UserViewModelFactory
import com.example.meatuapp.databinding.FragmentScanBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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

        val predictFactory = PredictViewModelFactory.getInstance(requireActivity().application)
        val predictViewModel = ViewModelProvider(this, predictFactory)[PredictViewModel::class.java]
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btncam.setOnClickListener {
            takePhoto()
        }
        binding.btnGallery.setOnClickListener {
            takeFromGallery()
        }
        binding.button3.setOnClickListener {
            if (myfile != null) {
                val file = reduceFileImage(myfile as File)
                binding.progressBar.isVisible = true
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestImageFile
                )
                predictViewModel.Analyze(imageMultipart)
                    .observe(viewLifecycleOwner) { response ->
                        Log.d("predict", response.predict.toString())
                        if (response != null && response.predict.isNotEmpty()) {
                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                requireContext(),
                                "Gambar Sedang dalam proses!",
                                Toast.LENGTH_SHORT
                            ).show()
//                            myfile = null
                            val intent = Intent(requireContext(), PredictActivity::class.java)
                            intent.putExtra("prediction", response.predict)
                            intent.putExtra("imagePath", myfile?.absolutePath)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                requireContext(),
                                "Gagal memproses gambar",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Silahkan Memilih atau mengambil gambar terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }

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