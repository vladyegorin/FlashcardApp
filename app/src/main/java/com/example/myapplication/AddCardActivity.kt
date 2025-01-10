package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcard)

        val backButton = findViewById<Button>(R.id.backButton)
        val setName = intent.getStringExtra("name")
        val setNumber = intent.getIntExtra("number", 0)


        backButton.setOnClickListener {
            val intent = Intent(this, AddCardSETActivity::class.java)
            startActivity(intent)
        }
        val nameTextView = findViewById<TextView>(R.id.name)
        val numberTextView = findViewById<TextView>(R.id.number)
        nameTextView.setText(setName)
        numberTextView.setText(setNumber.toString())


    }

}

