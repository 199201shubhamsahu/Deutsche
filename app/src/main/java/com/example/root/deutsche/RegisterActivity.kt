package com.example.root.deutsche

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*

class RegisterActivity : AppCompatActivity(){

    lateinit var mRegBtn: Button
    lateinit var mRegEmail: EditText
    lateinit var mRegPassword: EditText
    lateinit var mRegName: EditText
    lateinit var mLoginLink: TextView

    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        mRegBtn = findViewById(R.id.sign_up_button)
        mRegEmail = findViewById(R.id.reg_email_text)
        mRegPassword = findViewById(R.id.reg_password_text)
        mRegName = findViewById(R.id.reg_name)
        mLoginLink = findViewById(R.id.yes_account_text)

        mLoginLink.setOnClickListener(){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }

        mRegBtn.setOnClickListener(){
            val name = mRegName.text.toString().trim()
            val email = mRegEmail.text.toString().trim()
            val password = mRegPassword.text.toString().trim()


            if(TextUtils.isEmpty(name)){
                mRegName.error = "Enter name"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(email)){
                mRegEmail.error = "Enter email"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(password)){
                mRegPassword.error = "Enter passwors"
                return@setOnClickListener
            }

            createUser(name, email, password)
        }
    }

    private fun createUser(name: String, email: String, password: String){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val currentUser = mAuth.currentUser
                        val uid = currentUser!!.uid

                        val userMap = HashMap<String, String>()
                        userMap["name"]= name
                        userMap["email"]= email

                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                        mDatabase.setValue(userMap).addOnCompleteListener(OnCompleteListener { task ->

                            if(task.isSuccessful){



                                val profileUpdates = UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build()

                                currentUser.updateProfile(profileUpdates)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                            }
                                        }
                                val intent = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })


                    } else {
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }


    }
}