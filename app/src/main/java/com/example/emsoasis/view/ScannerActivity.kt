package com.example.emsoasis.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import java.util.HashSet

class ScannerActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var scannerViewModel: ScannerViewModel
    var qrCodes: Set<String> = HashSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        val type = intent.getStringExtra("type")
        val eventId = intent.getIntExtra("eventId", 0)
        val teamId = intent.getIntExtra("teamId", 0)
        scannerViewModel = ViewModelProviders.of(this, ScannerViewModelFactory())[ScannerViewModel::class.java]

        if (type == "player") {
            name.visibility = View.INVISIBLE
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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
            qrCodes.plus(it.text)
            count.text = "Count: ${qrCodes.size}"
        }

        // Add in button

       bttn_add.setOnClickListener {
           when(type){

               "team" -> {
                   scannerViewModel.addTeam(eventId, name.text.toString(), qrCodes.toList().drop(0), qrCodes.toList().first())
                   qrCodes = HashSet()
               }

               "player" -> {
                   scannerViewModel.addMember(eventId, teamId, qrCodes.toList())
                   qrCodes = HashSet()
               }

               else -> {
                   qrCodes = HashSet()
               }
           }

           codeScanner.errorCallback = ErrorCallback {
                Toast.makeText(this, "Error in scanning code", Toast.LENGTH_LONG).show()
               Log.e("Scanner Activity", "Error occoured in Scanning code = ${it.toString()}")
           }

           scanner.setOnClickListener {
               codeScanner.startPreview()
           }
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
