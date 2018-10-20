package com.example.root.deutsche

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_alphabets.*


class AlphabetsActivity: NavigationView.OnNavigationItemSelectedListener, AppCompatActivity () {//TODO 3


    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mFirebaseDatabase: FirebaseDatabase
    lateinit var mDatabaseReference: DatabaseReference
//    lateinit var stringArray: ArrayList<String?>
//    lateinit var alphabetsList: MutableList<String>
    val itemList: ArrayList<String> = ArrayList()

    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mDrawerToggleButton: ActionBarDrawerToggle

    lateinit var Alphabets: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alphabets)//TODO 4

        mAuth = FirebaseAuth.getInstance()

    }

    public override fun onStart() {
        super.onStart()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {

                mDrawerLayout = findViewById(R.id.drawer_layout_alphabets)//TODO 1
                mDrawerToggleButton = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
                mDrawerLayout.addDrawerListener(mDrawerToggleButton)
                mDrawerToggleButton.syncState()

                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                val navigationView = findViewById<NavigationView>(R.id.nav_view_alphabets)//TODO 2
                var menu = navigationView.menu
                var userName = menu.findItem(R.id.nav_username)
                userName.title = user.displayName.toString()
                navigationView.setNavigationItemSelectedListener(this)


                mFirebaseDatabase = FirebaseDatabase.getInstance()
                mDatabaseReference = mFirebaseDatabase.getReference("Alphabets")

//                val stringArray = arrayListOf<String>()
//                stringArray.add("asasa")
//                stringArray.add("kaka")

//                val adapter = ArrayAdapter<String>(this, R.layout.activity_alphabets, stringArray)
//                val listView = findViewById<ListView>(R.id.list_view_alphabets)
//                listView.adapter = adapter




                addItems()
                recycler_list.layoutManager = LinearLayoutManager(this)
                recycler_list.adapter = RecyclerAdapter(itemList, this)


            }
        }


    }

    fun addItems() {
        itemList.add("dog")
        itemList.add("cat")
        itemList.add("owl")
        itemList.add("cheetah")
        itemList.add("raccoon")
        itemList.add("bird")
        itemList.add("snake")
        itemList.add("lizard")
        itemList.add("hamster")
        itemList.add("bear")
        itemList.add("lion")
        itemList.add("tiger")
        itemList.add("horse")
        itemList.add("frog")
        itemList.add("fish")
        itemList.add("shark")
        itemList.add("turtle")
        itemList.add("elephant")
        itemList.add("cow")
        itemList.add("beaver")
        itemList.add("bison")
        itemList.add("porcupine")
        itemList.add("rat")
        itemList.add("mouse")
        itemList.add("goose")
        itemList.add("deer")
        itemList.add("fox")
        itemList.add("moose")
        itemList.add("buffalo")
        itemList.add("monkey")
        itemList.add("penguin")
        itemList.add("parrot")
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
