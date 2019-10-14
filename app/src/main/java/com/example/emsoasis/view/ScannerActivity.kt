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
import androidx.lifecycle.Observer
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
import java.lang.Exception
import java.util.HashSet

class ScannerActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var scannerViewModel: ScannerViewModel
    var qrCodes: MutableSet<String> = LinkedHashSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        val type = intent.getStringExtra("type")
        val eventId = intent.getStringExtra("eventId")
        val teamId = intent.getStringExtra("teamId")
        Log.d("Info", "$type, $eventId, $teamId")
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
            Log.d("Scanner Activity", "Decoded code = ${it.text}")
            qrCodes.add(it.text)
            runOnUiThread {
                Log.d("Scanner Activity", "Size of String = ${qrCodes.toList().size}")
                count.text = "Count: ${qrCodes.toList().size}"
            }
        }

        scannerViewModel.isSuccessful.observe(this, Observer {
            if(it) {
                progress_scanner.visibility = View.INVISIBLE
                Toast.makeText(this, "Sucessful", Toast.LENGTH_LONG).show()
                qrCodes = LinkedHashSet()
                count.text = "Count: ${qrCodes.toList().size}"
            } else {
                progress_scanner.visibility = View.INVISIBLE
                // Toast.makeText(this, "unSucessful", Toast.LENGTH_LONG).show()
            }
        })

        scannerViewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

       bttn_add.setOnClickListener {
           try {
               when (type) {

                   "team" -> {
                       scannerViewModel.addTeam(
                           eventId.toInt(),
                           name.text.toString(),
                           qrCodes.toList().drop(0),
                           qrCodes.toList().first()
                       )
                   }

                   "player" -> {
                       scannerViewModel.addMember(eventId.toInt(), teamId.toInt(), qrCodes.toList())
                   }

                   else -> {
                       qrCodes = LinkedHashSet()
                   }
               }
           } catch (e: Exception) {
                Log.e("Scanner Activity", "Error in adding button = ${e.toString()}")
           }
       }

           codeScanner.errorCallback = ErrorCallback {
               runOnUiThread {
                   Toast.makeText(this, "Error in scanning code", Toast.LENGTH_LONG).show()
                   Log.e("Scanner Activity", "Error occoured in Scanning code = ${it.toString()}")
               }

           }

           scanner.setOnClickListener {
               it.isClickable = false
               progress_scanner.visibility = View.VISIBLE
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
