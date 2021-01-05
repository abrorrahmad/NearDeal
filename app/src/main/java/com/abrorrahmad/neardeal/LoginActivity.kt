package com.abrorrahmad.neardeal

import LoginRespone
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.abrorrahmad.neardeal.data.ApiClient
import com.abrorrahmad.neardeal.data.ApiEndpoint
import com.abrorrahmad.neardeal.util.PopupUtil
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    internal var getUsername: String? = null
    internal var getPassword: String? = null
    var username: String = ""
    var password: String = ""

    internal var loginResponse: LoginRespone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val sharedPreferences = getSharedPreferences("com.rapitechsolution.neardeal",
            Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)



        if (isLoggedIn) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_login.setOnClickListener {
            username = et_email?.text.toString()
            password = et_password?.text.toString()

            if (username.equals("") || username.length == 0 || password.equals("") || password.length == 0 ){
                Toast.makeText(applicationContext, "segera isi terlebih dahulu", Toast.LENGTH_SHORT).show()
            }else{
                login()
            }
        }
    }



    private fun login() {
        PopupUtil.showLoading(this@LoginActivity, "Logging In", "Please Wait . . .")
        username = et_email?.text.toString()
        password = et_password?.text.toString()

        val apiEndPoint = ApiClient.getClient(this@LoginActivity).create(ApiEndpoint::class.java)
        val call = apiEndPoint.login(username, password)
        call.enqueue(object : Callback<LoginRespone> {
            override fun onResponse(call: Call<LoginRespone>, response: Response<LoginRespone>) {
                PopupUtil.dismissDialog()
                loginResponse = response.body()
                if (loginResponse != null) {
                    if (loginResponse!!.success!!) {
                        val sharedPreferences = getSharedPreferences("com.rapitechsolution.neardeal",
                            Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        //Mengubah valueuntuk masing masing key sharedPreference
                        editor.putString("username", username)
                        editor.putString("password", password)
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()
                        //Menjalankan perintah intent secara langsung
                        runOnUiThread {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "" + "Username atau Password Salah", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Response is Null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginRespone>, t: Throwable) {

            }
        })
    }
}


