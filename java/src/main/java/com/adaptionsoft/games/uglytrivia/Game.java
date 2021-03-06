package com.adaptionsoft.games.uglytrivia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Game {

    private static final Logger logger = Logger.getLogger("kata.trivia.Game");
    private static FileHandler fileHandler = null;
    private final QuestionMaker questionMaker = new QuestionMaker();
    ;

    //TODO: Move playerName, places, purses and inPenaltyBox to a new class Player.
    //TODO: Make player lists type-safe
    private ArrayList players = new ArrayList();
    private int[] places = new int[6];
    private int[] purses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {

        try {
            fileHandler = new FileHandler("%h/Game-logging.log", 10000000, 1, true);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        logger.addHandler(fileHandler);

        for (int i = 0; i < 50; i++) {

            questionMaker.addPopQuestion("Pop Question " + i);
            questionMaker.addScienceQuestion(("Science Question " + i));
            questionMaker.addSportsQuestion(("Sports Question " + i));
            questionMaker.addRockQuestion("Rock Question " + i);
        }
    }

    public void add(String playerName) {

        getPlayers().add(playerName);
        getPlaces()[howManyPlayers()] = 0;
        getPurses()[howManyPlayers()] = 0;
        getInPenaltyBox()[howManyPlayers()] = false;

        logger.info(playerName + " was added");
        logger.info("They are player number " + getPlayers().size());
    }

    private int howManyPlayers() {
        return getPlayers().size();
    }

    public void roll(int rollingNumber) {
        logger.info(getPlayers().get(getCurrentPlayer()) + " is the current player");
        logger.info("They have rolled a " + rollingNumber);

        if (getInPenaltyBox()[getCurrentPlayer()]) {
            if (rollingNumber % 2 != 0) {
                setGettingOutOfPenaltyBox(true);

                logger.info(getPlayers().get(getCurrentPlayer()) + " is getting out of the penalty box");
                currentPlayerMovesToNewPlaceAndAnswersAQuestion(rollingNumber);
                if (getPlaces()[getCurrentPlayer()] > 11)
                    getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] - 12;

                logger.info(getPlayers().get(getCurrentPlayer())
                        + "'s new location is "
                        + getPlaces()[getCurrentPlayer()]);
                logger.info("The category is " + currentCategory());
                askQuestion();
            } else {
                logger.info(getPlayers().get(getCurrentPlayer()) + " is not getting out of the penalty box");
                setGettingOutOfPenaltyBox(false);
            }

        } else {

            currentPlayerMovesToNewPlaceAndAnswersAQuestion(rollingNumber);
            if (getPlaces()[getCurrentPlayer()] > 11)
                getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] - 12;

            logger.info(getPlayers().get(getCurrentPlayer())
                    + "'s new location is "
                    + getPlaces()[getCurrentPlayer()]);
            logger.info("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void currentPlayerMovesToNewPlaceAndAnswersAQuestion(int rollingNumber) {
        getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] + rollingNumber;
    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            logger.info(questionMaker.removeFirstPopQuestion());
        if (currentCategory() == "Science")
            logger.info(questionMaker.removeFirstScienceQuestion());
        if (currentCategory() == "Sports")
            logger.info(questionMaker.removeFirstSportsQuestion());
        if (currentCategory() == "Rock")
            logger.info(questionMaker.removeFirstRockQuestion());
    }

    private String currentCategory() {
        if (getPlaces()[getCurrentPlayer()] == 0) return "Pop";
        if (getPlaces()[getCurrentPlayer()] == 4) return "Pop";
        if (getPlaces()[getCurrentPlayer()] == 8) return "Pop";
        if (getPlaces()[getCurrentPlayer()] == 1) return "Science";
        if (getPlaces()[getCurrentPlayer()] == 5) return "Science";
        if (getPlaces()[getCurrentPlayer()] == 9) return "Science";
        if (getPlaces()[getCurrentPlayer()] == 2) return "Sports";
        if (getPlaces()[getCurrentPlayer()] == 6) return "Sports";
        if (getPlaces()[getCurrentPlayer()] == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (getInPenaltyBox()[getCurrentPlayer()]) {
            if (isGettingOutOfPenaltyBox()) {
                return currentPlayerGetsAGoldCoinAndSelectNextPlayer();
            } else {
                nextPlayer();
                return true;
            }

        } else {
            return currentPlayerGetsAGoldCoinAndSelectNextPlayer();
        }
    }

    private boolean currentPlayerGetsAGoldCoinAndSelectNextPlayer() {
        logger.info("Answer was correct!!!!");
        getPurses()[getCurrentPlayer()]++;
        logger.info(getPlayers().get(getCurrentPlayer())
                + " now has "
                + getPurses()[getCurrentPlayer()]
                + " Gold Coins.");

        boolean isGameStillInProgress = isGameStillInProgress();
        nextPlayer();

        return isGameStillInProgress;
    }

    private void nextPlayer() {
        setCurrentPlayer(getCurrentPlayer() + 1);
        if (getCurrentPlayer() == getPlayers().size()) setCurrentPlayer(0);
    }

    public boolean wrongAnswer() {
        logger.info("Question was incorrectly answered");
        logger.info(getPlayers().get(getCurrentPlayer()) + " was sent to the penalty box");
        getInPenaltyBox()[getCurrentPlayer()] = true;

        nextPlayer();

        //TODO-later: The return value of method Game.wrongAnswer() is unnecessary and should be eliminated.
        return true;
    }

    private boolean isGameStillInProgress() {
        return !(getPurses()[getCurrentPlayer()] == 6);
    }

    public ArrayList getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList players) {
        this.players = players;
    }

    public int[] getPlaces() {
        return places;
    }

    public void setPlaces(int[] places) {
        this.places = places;
    }

    public int[] getPurses() {
        return purses;
    }

    public void setPurses(int[] purses) {
        this.purses = purses;
    }

    public boolean[] getInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean[] inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }


    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }
}
