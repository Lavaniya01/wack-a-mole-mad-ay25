# Wack-a-Mole (MAD AY25)

## 📱 Project Overview
This project was developed to satisfy the assignment requirements for the module  
**Mobile Applications Development (MAD)** under **Ngee Ann Polytechnic**,  
**Academic Year 2025, Semester 2**.

The application is a **Wack-a-Mole game** built using Android Studio and Jetpack Compose.  
The focus of this submission is on fulfilling the **Basic/Core requirements** of the assignment.

---

## 📂 Project Structure
wack-a-mole-mad-ay25/
├── Basic/ # Core requirements implementation
├── README.md
└── LICENSE

- **Basic/** contains the fully working core implementation of the Wack-a-Mole game.
- **Advanced/** was reserved for extended features such as database and multi-user support but was not implemented for this submission.

---

## 🕹️ Basic Features Implemented
- Single-player Wack-a-Mole gameplay
- 3×3 grid of buttons representing mole holes
- Only one mole appears at a time
- Random mole position generation
- Mole moves every 700–1000 milliseconds
- Score increases when the correct mole is tapped
- Countdown timer (30 seconds)
- Start and Restart buttons
- Game automatically ends when time runs out
- Final score displayed at the end of the game
- High score saved using **SharedPreferences**
- High score persists after app restart
- Settings screen with navigation support

---

## 🛠️ Technology Used
- **Kotlin**
- **Jetpack Compose**
- **Android Studio**

---

## ▶️ How to Run the Application
1. Open the **Basic** folder in Android Studio
2. Connect an Android device or start an emulator
3. Click **Run**
4. Press **Start** to begin playing the game

---

## 🤖 LLM Usage Declaration

Large Language Models (LLMs) were used as a **support tool** during the development of this project.  
The LLM was not used to complete the assignment automatically, but to assist in understanding concepts, debugging issues, and improving code clarity.

### Tools Used
- **ChatGPT (OpenAI)**

### Example Prompts / Questions Asked
1. “Why am I getting an unresolved reference error in Jetpack Compose navigation?”
2. “How can I move UI elements in a Column layout in Jetpack Compose?”
3. “How does SharedPreferences work for saving high scores in Android?”
4. “How can I slightly change the UI layout to make it look more original?”

### Areas Influenced by LLM Support
- **Debugging and error clarification**  
  The LLM helped explain Android Studio error messages and suggested possible fixes, which were then manually applied and tested.

- **UI layout ideas**  
  Suggestions were used to adjust spacing, layout arrangement, and button placement. The final layout was modified and customised by the student.

- **Code improvement and refactoring**  
  Small code snippets and logic explanations were provided, which were reviewed, simplified, and rewritten to suit the student’s understanding and coding style.

### Adaptation and Understanding
All code influenced by LLM suggestions was:
- Reviewed and understood by the student
- Modified to match assignment requirements
- Tested on a real Android device
- Integrated manually rather than copied blindly

### Key Takeaways and Learning
- Better understanding of Jetpack Compose layouts and state management
- Improved ability to debug Android/Kotlin errors independently
- Learned how to persist data using SharedPreferences
- Gained confidence in structuring a complete Android application

The final implementation reflects the student’s own understanding and effort, with LLMs used only as a learning aid.

---

## 👤 Author
**Lavaniya Rajamoorthi**

---

## 📜 License
This project is licensed under the **MIT License**.
