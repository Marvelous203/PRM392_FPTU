package com.example.lab1.ui.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.lab1.R
import android.net.Uri
import android.provider.Settings

class SendNotificationActivity : AppCompatActivity() {
    
    private lateinit var etNotificationTitle: EditText
    private lateinit var etNotificationMessage: EditText
    private lateinit var btnSendNotification: Button
    private lateinit var btnBack: Button
    private lateinit var tvNotificationCount: TextView
    // Remove this line: private lateinit var btnOpenSettings: Button
    
    private val CHANNEL_ID = "notification_channel"
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
    private var notificationCount = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_notification)
        
        initViews()
        createNotificationChannel()
        setupClickListeners()
        updateNotificationCount()
        
        // Kiểm tra và yêu cầu quyền notification
        checkNotificationPermission()
    }
    
    private fun initViews() {
        etNotificationTitle = findViewById(R.id.et_notification_title)
        etNotificationMessage = findViewById(R.id.et_notification_message)
        btnSendNotification = findViewById(R.id.btn_send_notification)
        btnBack = findViewById(R.id.btn_back_notification)
        tvNotificationCount = findViewById(R.id.tv_notification_count)
        // Remove this line: btnOpenSettings = findViewById(R.id.btn_open_settings)
    }
    
    private fun setupClickListeners() {
        btnSendNotification.setOnClickListener {
            if (hasNotificationPermission()) {
                sendNotification()
            } else {
                // Hiển thị dialog hỏi người dùng
                showPermissionDialog()
            }
        }
        
        // Remove this click listener:
        // btnOpenSettings.setOnClickListener {
        //     openNotificationSettings()
        // }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun showPermissionDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Cần quyền Notification")
            .setMessage("App cần quyền gửi notification để hoạt động. Bạn có muốn mở cài đặt để bật quyền không?")
            .setPositiveButton("Mở Settings") { _, _ ->
                openNotificationSettings()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
    
    private fun openNotificationSettings() {
        try {
            // Cách 1: Mở trực tiếp notification settings của app
            val intent = Intent().apply {
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    }
                    else -> {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.fromParts("package", packageName, null)
                    }
                }
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback: mở app info nếu không thể mở notification settings
            openAppInfo()
        }
    }
    
    private fun openAppInfo() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Không thể mở cài đặt. Vui lòng vào Settings > Apps > ${getString(R.string.app_name)} > Notifications", Toast.LENGTH_LONG).show()
        }
    }
    
    // Kiểm tra khi user quay lại app
    override fun onResume() {
        super.onResume()
        
        // Kiểm tra lại quyền notification khi user quay lại
        if (hasNotificationPermission()) {
            Toast.makeText(this, "Đã bật quyền notification thành công!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!hasNotificationPermission()) {
                requestNotificationPermission()
            }
        }
    }
    
    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Không cần quyền cho Android < 13
        }
    }
    
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        }
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        when (requestCode) {
            NOTIFICATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Đã cấp quyền notification!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Cần cấp quyền để gửi notification!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "App Notifications"
            val descriptionText = "Channel for sending app notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH // Thay đổi thành HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableLights(true)
                enableVibration(true)
            }
            
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun sendNotification() {
        // Kiểm tra quyền trước khi gửi
        if (!hasNotificationPermission()) {
            showPermissionExplanation()
            return
        }
        
        val title = etNotificationTitle.text.toString().trim()
        val message = etNotificationMessage.text.toString().trim()
        
        if (title.isEmpty()) {
            etNotificationTitle.error = "Vui lòng nhập tiêu đề"
            return
        }
        
        if (message.isEmpty()) {
            etNotificationMessage.error = "Vui lòng nhập nội dung"
            return
        }
        
        // Intent để mở app khi click vào notification
        val intent = Intent(this, SendNotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Thay đổi thành HIGH
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Thêm âm thanh, rung
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Hiển thị trên lock screen
        
        try {
            with(NotificationManagerCompat.from(this)) {
                if (areNotificationsEnabled()) {
                    notify(System.currentTimeMillis().toInt(), builder.build())
                    notificationCount++
                    updateNotificationCount()
                    
                    Toast.makeText(this@SendNotificationActivity, "Đã gửi notification thành công!", Toast.LENGTH_SHORT).show()
                    
                    // Clear input fields
                    etNotificationTitle.text.clear()
                    etNotificationMessage.text.clear()
                } else {
                    Toast.makeText(this@SendNotificationActivity, "Notification bị tắt trong cài đặt!", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Lỗi bảo mật: ${e.message}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi gửi notification: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun showPermissionExplanation() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Quyền Notification bị tắt")
            .setMessage("App cần quyền gửi notification để hoạt động. Nhấn 'Mở Settings' để bật quyền trong cài đặt.")
            .setPositiveButton("Mở Settings") { _, _ ->
                openNotificationSettings()
            }
            .setNegativeButton("Để sau") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "Bạn có thể bật quyền bất cứ lúc nào trong Settings", Toast.LENGTH_LONG).show()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun updateNotificationCount() {
        tvNotificationCount.text = "Số notification đã gửi: $notificationCount"
    }
}