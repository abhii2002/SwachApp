package com.blissvine.swach.activities



import android.app.Dialog
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.blissvine.swach.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth



open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    private lateinit var mProgressDialog: Dialog


    fun showErrorSnackBar(message: String, errorMessage: Boolean){
         val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if(errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor(this@BaseActivity,
                R.color.colorSnackBarError
            ))
        }else {
             snackBarView.setBackgroundColor(ContextCompat.getColor(this@BaseActivity,
                 R.color.colorSnackBarSuccess
             ))
        }
        snackBar.show()
    }

    fun showProgressDialog(text: String){
        // Initializing our progress dialog variable with a new dialog
        mProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
      The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        // assigning the tv_progress_text to the text we pass in the function
        // TODO -> Check if it is working
        mProgressDialog.findViewById<TextView>(R.id.tv_progress_text).text = text

        // Start the dialog and display it on screen
        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    fun doubleBackToExit(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        },2000)

    }


    fun getCurrentUserID(): String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

}