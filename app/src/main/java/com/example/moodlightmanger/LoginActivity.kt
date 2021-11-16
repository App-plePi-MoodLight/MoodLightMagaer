package com.example.moodlightmanger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.example.moodlight.api.ServerClient
import com.example.moodlightmanger.model.AuthModel
import com.example.moodlightmanger.model.LoginModel
import com.example.moodlightmanger.question.QuestionActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<AppCompatButton>(R.id.loginBtn).setOnClickListener {

            val email: String = findViewById<EditText>(R.id.loginIdEtv).text.toString()
            val password: String = findViewById<EditText>(R.id.loginPasswordEtv).text.toString()

            when {
                email.equals("") -> {
                    errorVisible("이메일을 입력해주세요.")
                }
                password.equals("") -> {
                    errorVisible("비밀번호를 입력해주세요.")
                }
                else -> {
                    val loginModel : LoginModel = LoginModel(email, password)
                    ServerClient.getApiService().login(loginModel)
                        .enqueue(object : Callback<LoginModel> {

                            override fun onResponse(
                                call: Call<LoginModel>,
                                response: Response<LoginModel>
                            ) {
                                if (response.isSuccessful) {
                                    ServerClient.accessToken = response.body()!!.accessToken
                                    // Sign in success, update UI with the signed-in user's information

                                    ServerClient.getApiService().getUserInformation()
                                        .enqueue(object : Callback<AuthModel> {

                                            override fun onResponse(call: Call<AuthModel>, response: Response<AuthModel>) {
                                                if (response.isSuccessful) {

                                                    val authModel : AuthModel = response.body()!!

                                                    if (authModel.is_admin) {
                                                        Log.d("Login", "signInWithEmail:success")
                                                        val intent: Intent =
                                                            Intent(applicationContext, QuestionActivity::class.java)
                                                        startActivity(intent)
                                                        finish()
                                                    } else {
                                                        errorVisible("관리자 계정이 아닙니다. 다시 입력해주세요.")
                                                    }

                                                }
                                            }

                                            override fun onFailure(call: Call<AuthModel>, t: Throwable) {
                                                t.printStackTrace()
                                            }

                                        })

                                } else {
                                    errorVisible("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.")
                                }
                            }

                            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                                t.printStackTrace()
                            }

                        })
                }
            }
        }
    }

    private fun errorVisible(errorText : String) : Unit {
        findViewById<ImageView>(R.id.loginErrorIv).isVisible = true
        findViewById<TextView>(R.id.loginErrorTv).text = errorText
        findViewById<TextView>(R.id.loginErrorTv).isVisible = true
    }


}