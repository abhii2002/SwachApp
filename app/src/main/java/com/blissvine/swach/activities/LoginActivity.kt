package com.blissvine.swach.activities

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.blissvine.swach.R
import com.blissvine.swach.databinding.ActivityLoginBinding
import com.blissvine.swach.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else {
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


        spannableString.setSpan(clickableSpan, indexStart, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE )
        binding.tvRegister.text = (spannableString)
        binding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
        //end

        binding.tvForgetPassword.setOnClickListener(this@LoginActivity)
        binding.btnLogin.setOnClickListener(this@LoginActivity)


    }

    override fun onClick(view: View?) {
        if(view != null){
            when (view.id) {
                R.id.tv_forget_password -> {

                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)

                }
                R.id.btn_login ->{
                   // logInRegisteredUser()
                }
            }
        }
    }
}