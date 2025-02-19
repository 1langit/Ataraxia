package com.cemerlang.ataraxia.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cemerlang.ataraxia.data.api.ApiClient
import com.cemerlang.ataraxia.data.PrefManager
import com.cemerlang.ataraxia.R
import com.cemerlang.ataraxia.databinding.ActivityLoginBinding
import com.cemerlang.ataraxia.ui.dashboard.DashboardActivity
import com.cemerlang.ataraxia.ui.dashboard.UserDataActivity
import com.cemerlang.ataraxia.data.models.dashboard.UserDataResponse
import com.cemerlang.ataraxia.data.models.auth.LoginRequest
import com.cemerlang.ataraxia.data.models.auth.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = Color.parseColor("#92A0FF")

        prefManager = PrefManager.getInstance(this)
        if (prefManager.getToken().isNotBlank()) {
            val newIntent = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(newIntent)
            finish()
        }

        with(binding) {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                if (validateLogin(email, password)) {
                    loginUser(email, password)
//                    stub()
                }
            }
            btnRegister.setOnClickListener {
                val newIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(newIntent)
                finish()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val loginCall = ApiClient.getApiInstance().login(loginRequest)
        loginCall.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    prefManager.apply {
                        saveToken(response.body()!!.token)
                        saveUid(data.id)
                        saveEmail(data.email)
                        saveFirstName(data.first_name)
                        saveLastName(data.last_name)
                    }
                    Toast.makeText(this@LoginActivity, "Berhasil masuk", Toast.LENGTH_SHORT).show()
                    redirectUser()
                } else {
                    val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                    val errorMessage = errorJson?.getString("error")
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun redirectUser() {
        val userDataCall = ApiClient.getApiInstance().getUserData("Bearer ${prefManager.getToken()}")
        userDataCall.enqueue(object: Callback<UserDataResponse> {
            override fun onResponse(call: Call<UserDataResponse>, response: Response<UserDataResponse>) {
                val newIntent = Intent(this@LoginActivity,
                    if (response.isSuccessful) {
                        DashboardActivity::class.java
                    } else {
                        UserDataActivity::class.java
                    }
                )
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(newIntent)
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
                val newIntent = Intent(this@LoginActivity, DashboardActivity::class.java)
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(newIntent)
            }
        })
    }

    private fun validateLogin(email: String, password: String): Boolean {
        with(binding) {
            inputEmail.error = if (email.isBlank()) "Tidak boleh kosong" else null
            inputEmail.isErrorEnabled = email.isBlank()

            inputPassword.error = if (password.isBlank()) "Tidak boleh kosong" else null
            inputPassword.isErrorEnabled = password.isBlank()

            return email.isNotBlank() && password.isNotBlank()
        }
    }

    private fun stub() {
        Toast.makeText(this@LoginActivity, "Berhasil masuk", Toast.LENGTH_SHORT).show()
        prefManager.apply {
            saveToken("temp")
            saveUid(0)
            saveEmail("tara@gmail.com")
            saveFirstName("Tara")
            saveLastName("Uttara")
        }
        val newIntent = Intent(this@LoginActivity, DashboardActivity::class.java)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(newIntent)
        finish()
    }
}