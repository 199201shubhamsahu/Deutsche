package com.example.root.deutsche

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.support.design.widget.NavigationView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import android.support.v4.view.GravityCompat
import android.support.annotation.NonNull
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View


class MainActivity : NavigationView.OnNavigationItemSelectedListener, AppCompatActivity () {


    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: AuthStateListener

    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mDrawerToggleButton: ActionBarDrawerToggle

    lateinit var Alphabets: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        Alphabets = findViewById(R.id.alphabets)

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
                var menu = navigationView.menu
                var userName = menu.findItem(R.id.nav_username)
                userName.title = user.displayName.toString()
                navigationView.setNavigationItemSelectedListener(this)

                Alphabets.setOnClickListener {
                    val intent = Intent(applicationContext, AlphabetsActivity::class.java)
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
