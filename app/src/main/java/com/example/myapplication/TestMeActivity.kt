package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TestMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testingknowledge)

        val setId = intent.getLongExtra("setId", -1L)
        val setName = intent.getStringExtra("setName")
        if (setId == -1L) {
            // Invalid set ID, handle this case as needed
            finish()
            return
        }

        val setNameTextView = findViewById<TextView>(R.id.chigibam)
        setNameTextView.text = "Set name: $setName"
    }
}
