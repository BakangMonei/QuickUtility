package com.madassignment.quickutility

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madassignment.quickutility.Fragments.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Load the default fragment (Calendar)
        loadFragment(CalendarFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_calendar -> loadFragment(CalendarFragment())
                R.id.nav_calculator -> loadFragment(CalculatorFragment())
                R.id.nav_converter -> loadFragment(ConverterFragment())
                R.id.nav_flash_events -> loadFragment(FlashEventsFragment())
            }
            true
        }
    }

    // Inflate the overflow menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle overflow menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                showSettingsDialog()
                true
            }
            R.id.action_help -> {
                showHelpDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Function to load a fragment into the fragment container
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    // Settings dialog to change appearance (light or dark theme)
    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Settings")

        // Appearance change options (light or dark theme)
        val themes = arrayOf("Light", "Dark")
        builder.setSingleChoiceItems(themes, -1) { dialog, which ->
            when (which) {
                0 -> {
                    Toast.makeText(this, "Light theme selected", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    Toast.makeText(this, "Dark theme selected", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    // Help dialog to provide user instructions
    private fun showHelpDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Help")
        builder.setMessage("""
            This app contains the following features:
            - Calendar: Add and view events on the selected date.
            - Calculator: Perform basic and scientific calculations.
            - Converter: Convert units between different types.
            - Flash Events: Create and manage quick events.
            
            If you need further assistance, please contact support.
        """.trimIndent())
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }
}
