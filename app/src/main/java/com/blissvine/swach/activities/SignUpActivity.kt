package com.blissvine.swach.activities

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.blissvine.swach.R
import com.blissvine.swach.databinding.ActivitySignUpBinding
import com.blissvine.swach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : BaseActivity() {
    private lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
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



        //  setUpActionBar()

        val text = binding.tvLogin.text
        val spannableString = SpannableString(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {

            override fun onClick(widget: View) {

//                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
//                finish()

                onBackPressed()

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.isFakeBoldText = true
                ds.color = Color.parseColor("#222934")
            }

        }

        val indexStart = binding.tvLogin.length() - 5
        val endIndex = binding.tvLogin.length() - 0


        spannableString.setSpan(
            clickableSpan,
            indexStart,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvLogin.text = (spannableString)
        binding.tvLogin.movementMethod = LinkMovementMethod.getInstance()
        //end
        binding.btnRegister.setOnClickListener {
            registerUser()
        }






    }


    private fun registerUser() {

        val email: String =  binding.etRegisterEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etRegisterPassword.text.toString().trim { it <= ' ' }

        if (validateRegisterDetails() && validateEmail(email) && validatePassword(password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->


                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val user = User(
                            firebaseUser.uid,
                            binding.etRegisterName.text.toString().trim { it <= ' ' },
                            binding.etRegisterEmail.text.toString().trim() { it <= ' ' }
                        )

                        FireStoreClass().registerUser(this@RegisterActivity, user)

//                    FirebaseAuth.getInstance().signOut()
//                    finish()

                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)

                    }
                }
        }

    }


    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etRegisterName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }


            TextUtils.isEmpty(binding.etRegisterEmail.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(binding.etRegisterPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }


            !binding.cbTermsAndCondition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_agree_terms_and_condition),
                    true
                )
                false
            }

            else -> {
                true
            }


        }
    }


    private fun validateEmail(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showErrorSnackBar("Invalid Email", true)
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

    fun userRegistrationSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@SignUpActivity,
            resources.getString(R.string.register_success), Toast.LENGTH_SHORT
        ).show()

    }


}