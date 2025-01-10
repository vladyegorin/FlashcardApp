package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcard)

        val backButton = findViewById<Button>(R.id.backButton)
        val setName = intent.getStringExtra("name")
        val setTotalCardNumber = intent.getIntExtra("number", 0)
        val currentCardNumber = intent.getIntExtra("currnumber", 1)

        backButton.setOnClickListener {
            val intent = Intent(this, AddCardSETActivity::class.java)
            startActivity(intent)
        }


        //displaying current card number x/x
        val numberTextView = findViewById<TextView>(R.id.cardCount)

        var cardNumberString = currentCardNumber.toString() + "/" + setTotalCardNumber.toString()
        numberTextView.setText(cardNumberString)

        val cardQuestion = findViewById<TextInputEditText>(R.id.questionInput)
        val cardAnswer = findViewById<TextInputEditText>(R.id.answerInput)
        val addCardButton = findViewById<Button>(R.id.createFlashcardButton)

        addCardButton.setOnClickListener {
            var validQ = false;
            var validA = false;

            if(cardQuestion.text.toString().length < 50 && cardQuestion.text.toString().isNotEmpty()) {
                validQ = true;
            } else{
                Toast.makeText(this, "Enter a valid question.", Toast.LENGTH_LONG).show()
            }
            if(cardAnswer.text.toString().length < 50 && cardAnswer.text.toString().isNotEmpty()) {
                validA = true;
            } else{
                Toast.makeText(this, "Enter a valid answer.", Toast.LENGTH_LONG).show()
            }

            if(validQ && validA && currentCardNumber < setTotalCardNumber) {
                val intent = Intent(this, AddCardActivity::class.java)
                //intent.putExtra("name", cardQuestion.text.toString())
                //intent.putExtra("number", cardAnswer.text.toString().toInt())
                intent.putExtra("currnumber", currentCardNumber + 1)
                intent.putExtra("number", setTotalCardNumber)

                startActivity(intent)
            } else{
                Toast.makeText(this, "All cards created!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }



        //next -> make card addition append to Room thingy. increment currentCardCount, after x/x card count reached
        //go to MainActivity of activity which shows all sets of flashcards
    }

}