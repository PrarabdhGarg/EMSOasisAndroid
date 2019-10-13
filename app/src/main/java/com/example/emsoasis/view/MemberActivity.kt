package com.example.emsoasis.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emsoasis.R
import kotlinx.android.synthetic.main.activity_member.*

class MemberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        members.adapter = MemberAdapter()


        addMember.setOnClickListener {
            startActivity(Intent(this, ScannerActivity::class.java).also {
                it.putExtra("type", "player")
            })
        }
    }
}
