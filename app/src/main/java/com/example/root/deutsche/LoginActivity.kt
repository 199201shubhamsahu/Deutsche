package com.example.root.deutsche

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener


class LoginActivity : AppCompatActivity(){

    lateinit var mLoginBtn: Button
    lateinit var mLoginEmail: EditText
    lateinit var mLoginPassword: EditText
    lateinit var mRegisterLink: TextView
    lateinit var mForgotPass: TextView

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLoginBtn = findViewById(R.id.sign_in_button)
        mLoginEmail = findViewById(R.id.email_text)
        mLoginPassword = findViewById(R.id.password_text)
        mRegisterLink = findViewById(R.id.no_account_text)
        mForgotPass = findViewById(R.id.forgot_pass_text)


        mAuth = FirebaseAuth.getInstance()

        mRegisterLink.setOnClickListener(){
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }
        mLoginBtn.setOnClickListener(){
            val email = mLoginEmail.text.toString().trim()
            val password = mLoginPassword.text.toString().trim()

            if(TextUtils.isEmpty(email)){
                mLoginEmail.error = "Enter email"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(password)){
                mLoginPassword.error = "Enter passwors"
                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String){

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}