package co.edu.udea.compumovil.gr01_20231.lab1

import android.app.DatePickerDialog
import android.view.View
import android.widget.EditText
import java.util.*
import kotlin.collections.ArrayList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    true
                }
                R.id.menu_register -> {
                    val intent = Intent(this, ContactDataActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_profile -> {
                    true
                }
                else -> false
            }
        }
    }
}
