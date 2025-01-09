package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AddCardSETActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcardset)

        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}

//will receive a name
//MAKE SURE NAME LENGTH <50 OR WHATEVER
//will receive a number of cards MAKE SURE IT IS A NUMBER(isdigit or whatever) UP TO 15(if not, say to choose a smaller number)
//(if<0 say to choose a bigger number
//if everything matches, listen to the button and lead to an activity where each individual card is created
//there on top the current card number is shown like YOU ARE CREATING A CARD 4 / 10
//when 10/10 done go to activity which shows sets of all cards(second button on main activity)
//if clicked on the set which is displayed in a recyclerview, show each individual flashcard inside that set



//further: make flashcard demonstration actually purposeful by randomizing card order blah blah