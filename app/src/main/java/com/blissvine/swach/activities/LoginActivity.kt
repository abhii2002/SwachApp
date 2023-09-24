package com.blissvine.swach.activities

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.blissvine.swach.R
import com.blissvine.swach.database.Authentication
import com.blissvine.swach.databinding.ActivityLoginBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }


        val text = binding.tvRegister.text
        val spannableString = SpannableString(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {

            override fun onClick(widget: View) {

                val options = ActivityOptions.makeSceneTransitionAnimation(this@LoginActivity)
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent, options.toBundle())


            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.isFakeBoldText = true
                ds.color = Color.parseColor("#222934")
            }

        }

        val indexStart = binding.tvRegister.length() - 8
        val endIndex = binding.tvRegister.length() - 0


        spannableString.setSpan(
            clickableSpan,
            indexStart,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvRegister.text = (spannableString)
        binding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
        //end

        binding.tvForgetPassword.setOnClickListener(this@LoginActivity)
        binding.btnLogin.setOnClickListener {
            loginUser()
        }


    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.tv_forget_password -> {

                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)

                }

                R.id.btn_login -> {


                }
            }
        }
    }


    fun loginUserRetro(email: String, password: String) {


        val retrofit = Retrofit.Builder()
            .baseUrl("https://swachh-w8p5.onrender.com")
            .build()

        val service = retrofit.create(Authentication::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)

        val jsonObjectString = jsonObject.toString()

        val reqBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObjectString)

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.Login(reqBody)

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    hideProgressDialog()
                    Log.d("Pretty Printed JSON :", prettyJson)
                    val intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)

                } else  {
                    hideProgressDialog()
                    showErrorSnackBar(response.code().toString(), true)

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }



    private fun loginUser() {

        val email: String = binding.etLoginEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etLoginPassword.text.toString().trim { it <= ' ' }

        if (validateRegisterDetails() && validateEmail(email) && validatePassword(password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            loginUserRetro(email, password)

        }

    }

    private fun validateRegisterDetails(): Boolean {
        return when {

            TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim() { it <= ' ' }) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.err_msg_enter_email),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            TextUtils.isEmpty(binding.etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.err_msg_enter_password),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }


            else -> {
                true
            }

        }
    }


    private fun validateLoginDetails(): Boolean {
        return when {

            TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }


            TextUtils.isEmpty(binding.etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            else -> {

                true
            }
        }
    }




    private fun validateEmail(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }


    private fun validatePassword(password: String): Boolean {
        if (password.length < 8) {
            showErrorSnackBar("Minimum 8 Character Password", true)
            return false
        }
        if (!password.matches(".*[A-Z].*".toRegex())) {
            showErrorSnackBar("Password Must Contain 1 Upper-case Character", true)
            return false
        }
        if (!password.matches(".*[a-z].*".toRegex())) {
            showErrorSnackBar("Password Must Contain 1 Lower-case Character", true)
            return false
        }
        if (!password.matches(".*[@#/$%^&+=].*".toRegex())) {
            showErrorSnackBar("Password Must Contain 1  Special Character (@#/\$%^&+=)", true)
            return false
        } else {
            return true
        }

    }


    fun userLoggedInSuccess() {
        hideProgressDialog()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))


    }




}


