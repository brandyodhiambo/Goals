package com.kanyiakinyidevelopers.goals.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kanyiakinyidevelopers.goals.R
import com.kanyiakinyidevelopers.goals.databinding.ActivityMainBinding
import com.kanyiakinyidevelopers.goals.ui.fragments.auth.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("user")

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView254) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.toolbarMain)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.settings -> {
                Toast.makeText(this, "This is settings", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.share -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Goals")
                    startActivity(Intent.createChooser(intent, "Choose One!"))
                return true
            }
            R.id.history -> {
                Toast.makeText(this, "This is history", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.help -> {
                Toast.makeText(this, "This is is help", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.logout -> {
                Toast.makeText(this@MainActivity, "Sign out", Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}