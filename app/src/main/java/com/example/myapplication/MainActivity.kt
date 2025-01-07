package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val textView1 = findViewById<TextView>(R.id.chigibam)
        val textView2 = findViewById<TextView>(R.id.secondtextview)
        // Set up the button click listener
        button.setOnClickListener {
            // Show a Toast message when the button is clicked
            textView1.text = "Button Clicked!"
            Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show()
            textView2.text = "Button Clicked too!"

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}