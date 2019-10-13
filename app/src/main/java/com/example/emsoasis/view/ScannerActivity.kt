package com.example.emsoasis.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.emsoasis.R
import com.example.emsoasis.viewmodel.ScannerViewModel
import com.example.emsoasis.viewmodel.ScannerViewModelFactory
import kotlinx.android.synthetic.main.activity_scanner.*

class ScannerActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var scannerViewModel: ScannerViewModel
    var qrCodes: MutableList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        val type = intent.getStringExtra("type")
        scannerViewModel = ViewModelProviders.of(this, ScannerViewModelFactory())[ScannerViewModel::class.java]

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        }



        codeScanner = CodeScanner(this, scanner)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            qrCodes.add(it.text)
        }

        codeScanner.errorCallback = ErrorCallback {

        }

        scanner.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        super.onPause()

        codeScanner.stopPreview()

    }

    override fun onResume() {
        super.onResume()

        codeScanner.startPreview()
    }
}
