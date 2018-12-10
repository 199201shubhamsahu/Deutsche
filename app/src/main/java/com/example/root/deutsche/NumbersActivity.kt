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
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_numbers.*

/*  Created by
 *   Shubham Sahu(pizzasahu)
 *   in 2018
 */
class Numbers{

    var numberdata: String =""
    var numberToWord: String =""
}

class NumbersActivity: NavigationView.OnNavigationItemSelectedListener, AppCompatActivity () {//TODO 3

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private lateinit var mFireBaseDatabase: FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mUserDatabaseReference: DatabaseReference
    val itemList1: ArrayList<String> = ArrayList()
    val itemList2: ArrayList<String> = ArrayList()
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mDrawerToggleButton: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers)//TODO 4

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

                mDrawerLayout = findViewById(R.id.drawer_layout_numbers)//TODO 1
                mDrawerToggleButton = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
                mDrawerLayout.addDrawerListener(mDrawerToggleButton)
                mDrawerToggleButton.syncState()

                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                val navigationView = findViewById<NavigationView>(R.id.nav_view_numbers)//TODO 2
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

                mFireBaseDatabase = FirebaseDatabase.getInstance()
                mDatabaseReference = mFireBaseDatabase.getReference("Numbers")

                number_recycler_list.layoutManager = LinearLayoutManager(this)
                number_recycler_list.adapter = TwoColRecyclerAdapter(itemList1, itemList2, this)

                mDatabaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for(dataSnapshot1: DataSnapshot in dataSnapshot.children){
                            val value = dataSnapshot1.getValue(Numbers::class.java)
                            itemList1.add(value!!.numberdata)
                            itemList2.add(value.numberToWord)
                        }
                        number_recycler_list.adapter!!.notifyDataSetChanged()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(applicationContext, "Can't fetch data", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        itemList1.clear()
        itemList2.clear()
        mAuth.addAuthStateListener(mAuthListener)
    }

    public override fun onStop() {
        super.onStop()
            mAuth.removeAuthStateListener(mAuthListener)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
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
