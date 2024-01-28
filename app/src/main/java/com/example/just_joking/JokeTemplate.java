package com.example.just_joking;

import java.util.Random;

public class JokeTemplate {
    private String joke;
    private int jokeimage;
    private String type;


    public JokeTemplate(String joke, String type) {
        this.joke = joke;
        this.type = type;

        do {
            jokeimage = getRandomNumber(1, 16);
        } while (jokeimage == 10);
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
