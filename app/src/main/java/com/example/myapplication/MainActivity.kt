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
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val textView1 = findViewById<TextView>(R.id.chigibam)
        val textView2 = findViewById<TextView>(R.id.secondtextview)
        // Set up the button click listener
        button1.setOnClickListener {
            // Show a Toast message when the button is clicked
            textView1.text = "Button Clicked!"
            Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show()
            textView2.text = "Button Clicked too!"

        }
        button2.setOnClickListener {
            // Show a Toast message when the button is clicked
            textView1.text = "Button 222222222222222222 Clicked!"
            Toast.makeText(this, "Button 2222222 Clicked!", Toast.LENGTH_SHORT).show()
            textView2.text = "Button 222222 Clicked too!"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}