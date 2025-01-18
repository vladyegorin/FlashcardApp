package com.example.myapplication

import androidx.room.Room
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.FlashcardDatabase
import com.example.myapplication.data.FlashcardSet
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddCardSETActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcardset)

        val backButton = findViewById<Button>(R.id.backButton)
        val createButton = findViewById<Button>(R.id.startCreatingButton)
        val nameInput = findViewById<TextInputEditText>(R.id.setNameInput)
        val numberInput = findViewById<TextInputEditText>(R.id.setNumberInput)
        val database = Room.databaseBuilder(
            applicationContext, // Or `this` in an activity context
            FlashcardDatabase::class.java,
            "flashcard_database" // Name of the database file
        ).build()
        val flashcardSetDao = database.flashcardSetDao()

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        createButton.setOnClickListener {
            var validName = false;
            var validNumber = false;

            if(nameInput.text.toString().length < 50 && nameInput.text.toString().isNotEmpty()) {
                validName = true;
            } else{
                Toast.makeText(this, "Enter a valid name.", Toast.LENGTH_LONG).show()
            }
            if(numberInput.text.toString().isNotEmpty() && numberInput.text.toString().toInt() <= 15) {
                validNumber = true;
            } else{
                Toast.makeText(this, "Enter a valid number.", Toast.LENGTH_LONG).show()
            }

            if(validName && validNumber) {
                GlobalScope.launch {
                    val newSet = FlashcardSet(
                        setId = 0, // Auto-generate if `@PrimaryKey(autoGenerate = true)` is used
                        setName = nameInput.text.toString()
                    )
                    flashcardSetDao.insertFlashcardSet(newSet)
                }
                val intentCards = Intent(this, AddCardActivity::class.java)
                intentCards.putExtra("name", nameInput.text.toString())
                intentCards.putExtra("currnumber", 1.toString())
                intentCards.putExtra("number", numberInput.text.toString().toInt())
                startActivity(intentCards)

                val intentSeeSets = Intent(this, SeeSetsActivity::class.java)
                intentSeeSets.putExtra("name", nameInput.text.toString())
                startActivity(intentSeeSets)
            }
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

//when goes into the addcardactivity, pass the name of the cardset and the number of cards to show user on which card are they on now

//further: make flashcard demonstration actually purposeful by randomizing card order blah blah