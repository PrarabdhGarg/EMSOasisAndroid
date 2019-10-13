package com.example.emsoasis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.emsoasis.view.EventActivity
import com.example.emsoasis.viewmodel.LoginViewModel
import com.example.emsoasis.viewmodel.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val loginViewModel by lazy {
        ViewModelProviders.of(this, LoginViewModelFactory())[LoginViewModel::class.java]
    }
    lateinit  var errorObserver: Observer<String>
    lateinit  var isLoadingObserver: Observer<Boolean>
    lateinit  var navigatorObserver: Observer<Boolean>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bttn_login.setOnClickListener {
            if(edit_username.text.toString().isEmpty() || edit_password.text.toString().isEmpty()) {
                displayError("Please Donot leave any field blank")
            } else {
                loginViewModel.login(edit_username.text.toString(), edit_password.text.toString())
            }
        }

        errorObserver = Observer<String> {
            displayError(it)
        }

        isLoadingObserver = Observer<Boolean> {
            setProgressBarVisiblity(it)
        }

        navigatorObserver = Observer<Boolean> {
            if (it) {
                startActivity(Intent(this, EventActivity::class.java))
                finish()
            }
        }

        loginViewModel.error.observe(this, errorObserver)

        loginViewModel.isLoading.observe(this, isLoadingObserver)

        loginViewModel.changePage.observe(this, navigatorObserver)
    }

    private fun displayError(message: String) {
        Log.e("Login Activity", "Error displayed = $message")
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setProgressBarVisiblity(isLoading: Boolean) {
        when(isLoading) {
            true -> {
                progress_login.visibility = View.VISIBLE
            }
            false -> {
                progress_login.visibility = View.INVISIBLE
            }
        }
    }
}
