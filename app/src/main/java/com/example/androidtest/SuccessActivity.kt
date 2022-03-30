package com.example.androidtest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val username = intent.getStringExtra("username")
        findViewById<TextView>(R.id.textView).text =
            "$username you have successfully reached 10 steps." // getString(R.string.inputName, username)

        // Hide menu_main
        supportActionBar?.hide()
    }

    fun backToHomeScreen(view: View) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
        startActivity(intent)
    }

    // Disable the Back button from calling the onDestroy() function so that the user can return to the same instance of that activity
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    fun sendMsg(view: View) {
        val uri = Uri.parse("smsto:+385917973174")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", findViewById<TextView>(R.id.textView).text.toString())
        startActivity(intent)
    }

    // Add records to DB
    fun addUsers(view: View) {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val username = findViewById<TextView>(R.id.textView).text.toString()

        if (username.isNotEmpty()) {
            // Insert the values into the DB through addUsers method
            // ID is automatically incremented
            val status =
                databaseHandler.addUsers(ModelClass(0, username))
            // If the status is -1 there is an error, otherwise execute
            if (status > -1) {
                // Message to show that records are saved
                Toast.makeText(applicationContext, "Record saved to DB.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Name or email can not be blank.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}