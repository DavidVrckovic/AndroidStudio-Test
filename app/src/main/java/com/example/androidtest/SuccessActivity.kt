package com.example.androidtest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val username = intent.getStringExtra("username")
        findViewById<TextView>(R.id.textView).text =
            "$username you have successfully reached 10 steps."
    }

    fun backToHomeScreen(view: View) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
        startActivity(intent)
    }

    override fun onBackPressed() {}

    fun sendMsg(view: android.view.View) {
        /*val Intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("sms", "0921854645", findViewById<TextView>(R.id.textView).text.toString()))
        startActivity(Intent)*/
        val uri = Uri.parse("smsto:+385917973174")
        val Intent = Intent(Intent.ACTION_SENDTO, uri)
        Intent.putExtra("sms_body", findViewById<TextView>(R.id.textView).text.toString())
        startActivity(Intent)
    }
}