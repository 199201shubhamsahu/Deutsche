package com.example.root.deutsche

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.support.design.widget.NavigationView
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class User{
    var name: String =""
    var email: String =""
    var marks: Int = 0
}

class MainActivity : NavigationView.OnNavigationItemSelectedListener, AppCompatActivity () {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: AuthStateListener
    private lateinit var mUserDatabaseReference: DatabaseReference

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mDrawerToggleButton: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        mAuthListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null ) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                mDrawerLayout = findViewById(R.id.drawer_layout)
                mDrawerToggleButton = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
                mDrawerLayout.addDrawerListener(mDrawerToggleButton)
                mDrawerToggleButton.syncState()

                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                val navigationView = findViewById<NavigationView>(R.id.nav_view)
                val menu = navigationView.menu
                val userName = menu.findItem(R.id.nav_username)
                userName.title = "Welcome, "+user.displayName.toString()

                mUserDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.uid)
                val navMarks = menu.findItem(R.id.navMarks)

                mUserDatabaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val value = dataSnapshot.getValue(User::class.java)
                        navMarks.title = "Marks: "+value!!.marks

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(applicationContext, "Can't fetch data", Toast.LENGTH_SHORT).show()
                    }
                })

                navigationView.setNavigationItemSelectedListener(this)

                alphabets.setOnClickListener {
                    val intent = Intent(applicationContext, AlphabetsActivity::class.java)
                    startActivity(intent)
                }

                numbers.setOnClickListener {
                    val intent = Intent(applicationContext, NumbersActivity::class.java)
                    startActivity(intent)
                }

                test_textView.setOnClickListener {
                    val intent = Intent(applicationContext, TestActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        mAuth.addAuthStateListener(mAuthListener)
    }

    public override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(mDrawerToggleButton.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}