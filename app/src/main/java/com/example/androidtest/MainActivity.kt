package com.example.androidtest

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

var counter = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /// Ways of getting elements from xml file
        //1 var counterText = findViewById<TextView>(R.id.textView)
        //1 counterText.text = counter.toString()
        //2 findViewById<TextView>(R.id.textView).text = counter.toString()

        // Toast i Log
        Toast.makeText(applicationContext, "onCreate", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onCreate")

        // "counter" preference to allow keeping the value upon rotating the phone
        //! Keep value in function onPause()
        val pref = getPreferences(MODE_PRIVATE)
        counter = pref.getInt("counter", 0)
        findViewById<TextView>(R.id.textView).text = counter.toString()

        registerForContextMenu(findViewById<TextView>(R.id.textView))
    }

    fun countUp(view: View) {
        ++counter
        findViewById<TextView>(R.id.textView).text = counter.toString()

        // When counter reaches 10
        // Go to new activity "SuccessActivity"
        if (counter == 10) {
            val intent = Intent(this, SuccessActivity::class.java).apply {
                putExtra("username", findViewById<EditText>(R.id.inputName).text.toString())
            }
            startActivity(intent)
            counter = 0
            findViewById<TextView>(R.id.textView).text = counter.toString()
        }
    }

    fun countDown(view: View) {
        --counter
        if (counter < 0) {
            counter = 0
        }
        findViewById<TextView>(R.id.textView).text = counter.toString()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(applicationContext, "onResume", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(applicationContext, "onPause", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onPause")

        // Keep "counter" value upon rotating the phone
        val prefEdit = getPreferences(MODE_PRIVATE).edit()
        prefEdit.putInt("counter", counter)
        prefEdit.commit()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(applicationContext, "onStop", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(applicationContext, "onRestart", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext, "onDestroy", Toast.LENGTH_SHORT).show()
        Log.i("MyLog", "onDestroy")
    }

    // Navigation menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Reset counter (through navigation menu)
    fun resetCounter(item: android.view.MenuItem) {
        counter = 0
        findViewById<TextView>(R.id.textView).text = counter.toString()
    }

    // Language change (through navigation menu)
    @Suppress("DEPRECATION")
    fun changeLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        res.updateConfiguration(config, res.displayMetrics)
    }

    // Language change (through navigation menu)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.restore_counter -> {
                counter = 0
                findViewById<TextView>(R.id.textView).text = counter.toString()
                true
            }
            R.id.croatian -> {
                changeLanguage(this, "hr")
                recreate()
                true
            }
            R.id.english -> {
                changeLanguage(this, "en")
                recreate()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Context menu (appearing when clicking and holding on the counter TextView)
    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }
}