# Flashcard App

An Android app for creating, organizing, and studying flashcards. Includes a feature to generate a simple 5-question quiz based on any topic.

## Features

- Create flashcard sets with names and optional descriptions
- Add multiple flashcards to each set
- View all flashcard sets in a list
- Tap on a set to view its cards
- Generate a quick 5-question quiz for any topic
- Built with Kotlin, Room Database, and RecyclerView

## Tech Stack

- Kotlin
- Android SDK
- Room Database
- RecyclerView
- Material Design Components

## Database Structure

**FlashcardSet Table**

- `id` (Int, Primary Key)
- `name` (String)
- `description` (String, optional)

**Flashcard Table**

- `id` (Int, Primary Key)
- `setId` (Int, Foreign Key → FlashcardSet)
- `question` (String)
- `answer` (String)

## Getting Started

1. Clone the repository:

    ```
    [git clone https://github.com/yourusername/flashcard-app.git](https://github.com/vladyegorin/FlashcardApp.git)
    ```

2. Open the project in Android Studio.

3. Build and run on a device or emulator.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
