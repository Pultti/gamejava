package com.isopoussu.gamejava;


import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class Game {

     enum GameState {
        ONGOING,
        WON,
        LOST
    }

    private int score = 0;
    private int questionNumber = 1;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int remainingAttempts = 5;
    private int currentQuestionIndex = 0;

    private GameState gameState = GameState.ONGOING;

    private List<String> questions = Arrays.asList(
    "Mikä on Suomen pääkaupunki?",
    "Mikä on Suomen kansallislintu?",
    "Mikä on Suomen pohjoisin kaupunki?",
    "Mikä on Suomen suurin järvi?",
    "Mikä on Suomen kansalliskukka?"
);

private List<String> correctAnswerlist = Arrays.asList(
    "Helsinki",
    "Laulujoutsen",
    "Nuorgam",
    "Saimaa",
    "Kielo"
);

    public GameState getGameState() {
        return gameState;
    }

    public String getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    // Getterit ja setterit
    public int getScore() {
        return score;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public Map<String, Integer> getPlayerStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("correctAnswers", correctAnswers);
        stats.put("wrongAnswers", wrongAnswers);
        return stats;
    }
    
    // Muuta pelilogiikkaa
    public void incrementQuestion() {
        this.questionNumber++;
    }

    public void correctAnswer() {
        this.score += 10;
        this.correctAnswers++;
    }

    public void wrongAnswer() {
        this.wrongAnswers++;
    }



        public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public boolean guess(String answer) {
        if (remainingAttempts <= 0) {
            // Peli on ohi, ehkä palauta false ja aseta jokin status-koodi tms.
            return false;
        }

        String currentCorrectAnswer = correctAnswerlist.get(currentQuestionIndex);

        if (currentCorrectAnswer.equalsIgnoreCase(answer)) {  // Käytetään nyt currentCorrectAnsweria
            remainingAttempts--;
            correctAnswer();
            return true;
        } else {
            remainingAttempts--;
            if (score >= 5) {
                score -= 5;
            }
            
            wrongAnswer();
        }
        
        if (remainingAttempts == 0) {
            // Peli on ohi
        }
    
        return false;
    }

    public void nextRound() {

        if (gameState != GameState.ONGOING) {
            return;
        }
    currentQuestionIndex++;
         if (currentQuestionIndex >= questions.size()) {

            gameState = GameState.WON;
            currentQuestionIndex = 0;  // Aloita alusta tai lopeta peli
        }
    }

    public void resetGame() {
    this.score = 0;
    this.questionNumber = 1;
    this.correctAnswers = 0;
    this.wrongAnswers = 0;
    this.remainingAttempts = 5;
    this.currentQuestionIndex = 0;
    this.gameState = GameState.ONGOING;  
}



}