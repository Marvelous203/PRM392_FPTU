package com.example.lab1.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lab1.R

class CameraPermissionActivity : AppCompatActivity() {
    
    private lateinit var btnTakePhoto: Button
    private lateinit var btnRequestPermission: Button
    private lateinit var btnBack: Button
    private lateinit var imageView: ImageView
    private lateinit var tvPermissionStatus: TextView
    
    private val CAMERA_PERMISSION_CODE = 100
    
    // ActivityResultLauncher cho camera
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageBitmap = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                result.data?.extras?.getParcelable("data", Bitmap::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.extras?.getParcelable<Bitmap>("data")
            }
            imageBitmap?.let {
                imageView.setImageBitmap(it)
                Toast.makeText(this, "Chụp ảnh thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    // ActivityResultLauncher cho nhiều quyền
    private val multiplePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val grantedPermissions = permissions.filterValues { it }.keys
        val deniedPermissions = permissions.filterValues { !it }.keys
        
        if (grantedPermissions.isNotEmpty()) {
            Toast.makeText(this, "Đã cấp ${grantedPermissions.size} quyền", Toast.LENGTH_SHORT).show()
        }
        
        if (deniedPermissions.isNotEmpty()) {
            Toast.makeText(this, "Từ chối ${deniedPermissions.size} quyền", Toast.LENGTH_SHORT).show()
        }
        
        updatePermissionStatus()
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_permission)
        
        initViews()
        updatePermissionStatus()
        setupClickListeners()
        setupButtons()
    }
    
    private fun initViews() {
        btnTakePhoto = findViewById(R.id.btn_take_photo)
        btnRequestPermission = findViewById(R.id.btn_request_permission)
        btnBack = findViewById(R.id.btn_back_camera)
        imageView = findViewById(R.id.iv_captured_image)
        tvPermissionStatus = findViewById(R.id.tv_permission_status)
    }
    
    private fun setupClickListeners() {
        btnTakePhoto.setOnClickListener {
            if (checkCameraPermission()) {
                openCamera()
            } else {
                Toast.makeText(this, "Cần cấp quyền camera trước!", Toast.LENGTH_SHORT).show()
            }
        }
        
        btnRequestPermission.setOnClickListener {
            requestCameraPermission()
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    // Thêm button để request tất cả quyền
    private fun setupButtons() {
        findViewById<Button>(R.id.btn_request_all_permissions)?.setOnClickListener {
            requestAllPermissions()
        }
        
        findViewById<Button>(R.id.btn_open_settings)?.setOnClickListener {
            openAppSettings()
        }
    }
    
    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )) {
            Toast.makeText(
                this,
                "Ứng dụng cần quyền camera để chụp ảnh",
                Toast.LENGTH_LONG
            ).show()
        }
        
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }
    
    private fun requestAllPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE
        )
        
        multiplePermissionLauncher.launch(permissions)
    }
    
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }
    
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
    
    private fun updatePermissionStatus() {
        val hasPermission = checkCameraPermission()
        tvPermissionStatus.text = if (hasPermission) {
            "✅ Đã có quyền camera"
        } else {
            "❌ Chưa có quyền camera"
        }
        
        btnTakePhoto.isEnabled = hasPermission
        btnRequestPermission.isEnabled = !hasPermission
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Đã cấp quyền camera!", Toast.LENGTH_SHORT).show()
                    updatePermissionStatus()
                } else {
                    Toast.makeText(this, "Quyền camera bị từ chối!", Toast.LENGTH_SHORT).show()
                    updatePermissionStatus()
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        updatePermissionStatus()
    }
}