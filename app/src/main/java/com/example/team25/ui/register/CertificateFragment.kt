package com.example.team25.ui.register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.bumptech.glide.Glide
import com.example.team25.BuildConfig
import com.example.team25.databinding.FragmentCertificateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CertificateFragment : Fragment() {
    private var _binding: FragmentCertificateBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by activityViewModels()
    private lateinit var transferUtility: TransferUtility
    private lateinit var s3Client: AmazonS3Client

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCertificateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initImageLauncher()
        loadInfo()
        observeRegistrationStatus()
        navigateToPrevious()
        setRegisterClickListener()
    }

    private fun loadInfo() {
        val certificateImageUrl = registerViewModel.certificateImage.value
        if (certificateImageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(certificateImageUrl)
                .into(binding.certificateUploadBtn)
        }
    }

    private fun initImageLauncher() {
        val getImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.certificateUploadBtn.setImageURI(it)
                binding.addImageImageView.visibility = View.GONE
                binding.addImageTextView.visibility = View.GONE

                registerViewModel.updateCertificateImage(it.toString())
            }
        }

        binding.certificateUploadBtn.setOnClickListener {
            getImageLauncher.launch("image/*")
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            registerViewModel.logManagerInfo()
            parentFragmentManager.popBackStack()
        }
    }

    private fun setRegisterClickListener() {
        binding.registerFinishBtn.setOnClickListener {
            val certificateImage = registerViewModel.certificateImage.value
            if (certificateImage.isEmpty()) {
                Toast.makeText(requireContext(), "증명서 이미지를 등록해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val profileImageUri = registerViewModel.profileImage.value
                uploadImageToS3(Uri.parse(profileImageUri), "images/profile")
                uploadImageToS3(Uri.parse(certificateImage), "images/certificate")

                Toast.makeText(requireContext(), "등록 신청 중입니다. 잠시만 기다려주세요.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun observeRegistrationStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.isRegistered.collect { isSuccess ->
                if (isSuccess) {
                    navigateToRegisterStatusActivity()
                }
            }
        }
    }

    private fun navigateToRegisterStatusActivity() {
        val intent = Intent(requireActivity(), RegisterStatusActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun uploadImageToS3(imageUri: Uri, folderPath: String) {
        val credentials: AWSCredentials =
            BasicAWSCredentials(BuildConfig.S3_ACCESS_KEY, BuildConfig.S3_SECRET_KEY)
        val region = Region.getRegion(Regions.AP_NORTHEAST_2)
        s3Client = AmazonS3Client(credentials, region)

        val name = registerViewModel.name.value
        val fileName = generateFileName(name)

        val s3FilePath = "$folderPath/$fileName"

        val tempFile = File(requireActivity().cacheDir, fileName)
        val inputStream = requireActivity().contentResolver.openInputStream(imageUri)

        inputStream.use { input ->
            FileOutputStream(tempFile).use { output ->
                input?.copyTo(output)
            }
        }

        transferUtility = TransferUtility.builder()
            .context(requireContext())
            .s3Client(s3Client)
            .build()

        val metadata = ObjectMetadata()
        metadata.contentType = requireActivity().contentResolver.getType(imageUri)

        val observer: TransferObserver = transferUtility.upload(
            "manager-app-storage",
            s3FilePath,
            tempFile,
            metadata
        )

        observer.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState?) {
                Log.d("S3 Upload", "Upload state: $state")
                if (state == TransferState.COMPLETED) {
                    val uploadedUrl = "https://manager-app-storage.s3.ap-northeast-2.amazonaws.com/$s3FilePath"
                    registerViewModel.updateUploadedImageUrl(folderPath, uploadedUrl)

                    if (folderPath == "images/certificate") {
                        registerViewModel.registerManager()
                    }

                    Log.d("S3 Upload", "Uploaded file URL: $uploadedUrl")
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                Log.d("S3 Upload", "Upload progress: $bytesCurrent/$bytesTotal")
            }

            override fun onError(id: Int, ex: Exception?) {
                Log.e("S3 Upload", "Upload error: ${ex?.message}")
            }
        })
    }

    private fun generateFileName(name: String): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyMMddHHmmss", Locale.getDefault())
        val currentDateTime = dateFormat.format(Date())

        return "$name$currentDateTime"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
