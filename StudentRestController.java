package com.isopoussu.gamejava;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isopoussu.gamejava.Game.GameState;

@RestController
public class StudentRestController {

    private final Game game = new Game();

    @GetMapping("/")
    public String info() {
        return "Tervetuloa peliin! Käytä seuraavia endpointteja:" +
           "\n\nGET-mappaukset:" +

           "\n/question1-5 - Tarkista kysymykset." +
           "\n/currentQuestion - näytä nykyinen kysymys" +
           "\n/score - näytä pelaajan pisteet" +
           "\n/playerStats - Näytä pelaajan tilastot." +

           "\n/getQuestionByNumber?number='NUMERO' - Hae kysymystä annetun numeron perusteella." +
           "\n/getQuestionDetails?number='NUMERO' - Hae kysymyksen tiedot annetun numeron perusteella." +

           "\n\nPOST-mappaukset:" +
           "\n/submitAnswerString - Lähetä vastaus (string muodossa). Parametri: answer" +
           "\n/submitAnswerJson - Lähetä vastaus ja saat vastauksen JSON-muodossa. Parametri: answer" +
           "\n/restartGame - Aloita peli alusta." +

           "\n\nOnnea peliin!";
}

// Controllerissa
@GetMapping("/currentQuestion")
public String getCurrentQuestion() {
    return game.getCurrentQuestion();  // Oletan, että `game` on saatavilla
}

    // 4 erilaista endpointtia
    @GetMapping("/question1")
public String getQuestion2() {
    return "Mikä on Suomen pääkaupunki?";
}

@GetMapping("/question2")
public String getQuestion3() {
    return "Mikä on Suomen kansallislintu?";
}

@GetMapping("/question3")
public String getQuestion4() {
    return "Mikä on Suomen pohjoisin kaupunki?";
}

@GetMapping("/question4")
public String getQuestion() {
    return "Mikä on Suomen suurin järvi?";
}

@GetMapping("/question5")
public String getQuestion5() {
    return "Mikä on Suomen kansalliskukka?";
}


@GetMapping("/score")
public Map<String, Integer> getScore() {
    return Collections.singletonMap("score", game.getScore());
}
        
@GetMapping("/questionNumber")
public String getQuestionNumber() {
    return "Olet kysymyksessä numero " + game.getQuestionNumber();
}


@GetMapping("/playerStats")
public Map<String, Integer> getPlayerStats() {
    return game.getPlayerStats();
}

@GetMapping("/getQuestionByNumber")
public String getQuestionByNumber(@RequestParam int number) {
    // Palauta kysymys perustuen parametrina annettuun numeroon.
    // Tämä on vain esimerkki, joten voit mukauttaa sen tarpeisiisi.
    if (number == 1) {
        return "Mikä on Suomen pääkaupunki?";
    } else if (number == 2) {
        return "Mikä on suomen kansallislintu?";
    } else if (number == 3) {
        return "Mikä on Suomen pohjoisin kaupunki?";
    } else if (number == 4) {
        return "Mikä on Suomen suurin järvi?";
    } else if (number == 5) {
        return "Mikä on Suomen kansalliskukka?";
    } else {}
    return "Kysymystä ei löytynyt.";
}

@GetMapping("/getQuestionDetails")
public Map<String, Object> getQuestionDetails(@RequestParam int number) {
    Map<String, Object> response = new HashMap<>();
    if (number == 1) {
        response.put("question", "Mikä on Suomen pääkaupunki?");
        response.put("options", List.of("Helsinki", "Espoo", "Tampere", "Oulu"));
    } else if (number == 2) {
        response.put("question", "Mikä on Suomen kansallislintu?");
        response.put("options", List.of("Hyttynen", "Kyhmyjoutsen", "Lokki", "Laulujoutsen"));
    } else if (number == 3) {
        response.put("question", "Mikä on Suomen pohjoisin kaupunki?");
        response.put("options", List.of("Turku", "Nuorgam", "Imatra", "Rovaniemi"));
    } else if (number == 4) {
        response.put("question", "Mikä on Suomen suurin järvi?");
        response.put("options", List.of("Lämsänjärvi", "Saimaa", "Laatokka", "Oulujärvi"));
    } else if (number == 5) {
        response.put("question", "Mikä on Suomen kansalliskukka?");
        response.put("options", List.of("Kielo", "Rentunruusu", "Nokkonen", "Tulppaani"));
    } else {
        response.put("question", "Kysymystä ei löytynyt.");
    }
   
    return response;
}


@PostMapping("/submitAnswerString")
public String submitAnswerString(@RequestParam String answer) {
    if (game.getGameState() != GameState.ONGOING) {
        return "Peli on jo päättynyt.";
    }
    if (game.guess(answer)) {
        game.nextRound();
        return "Oikein! Sait 10 pistettä. Seuraava kysymys: " + game.getCurrentQuestion() + " - "+ game.getRemainingAttempts() +  " kysymystä jäljellä.";
    } else {
        game.nextRound();
        return "Väärin. Menetit 5 pistettä. Seuraava kysymys: " + game.getCurrentQuestion() + " - "+ game.getRemainingAttempts() +  " kysymystä jäljellä.";
    }
}


@PostMapping("/submitAnswerJson")
public Map<String, Object> submitAnswerJson(@RequestParam String answer) {
    Map<String, Object> response = new HashMap<>();
    if (game.getGameState() != GameState.ONGOING) {
        response.put("result", "Peli on jo päättynyt.");
        return response;
    }
    response.put("Vastauksesi", answer);

    if (game.guess(answer)) {
        game.nextRound();
        response.put("Tulos", "Oikein! Sait 10 pistettä.");
    } else {
        game.nextRound();
        response.put("Tulos", "Väärin. Menetit 5 pistettä.");
    }
    
    response.put("Seuraava kysymys", game.getCurrentQuestion());
    response.put("Jäljellä olevat kysymykset", game.getRemainingAttempts());
    response.put("Pisteet", game.getScore());

    return response;
}






@PostMapping("/restartGame")
public Map<String, String> restartGame() {
    game.resetGame();
    return Collections.singletonMap("message", "Peli on nollattu, voit aloittaa alusta! Ensimmäinen kysymys --- " + game.getCurrentQuestion() + " - "+ game.getRemainingAttempts() +  " kysymystä jäljellä.");
}



}

