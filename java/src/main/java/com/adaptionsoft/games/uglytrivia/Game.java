package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

	//TODO-working-on : Check public interface of the server-side code to see how it is being used by the client-side code

	//TODO: The fields of class Game should be private.
	private ArrayList players = new ArrayList();
    private int[] places = new int[6];
    private int[] purses  = new int[6];
    private boolean[] inPenaltyBox  = new boolean[6];
    
    private LinkedList popQuestions = new LinkedList();
    private LinkedList scienceQuestions = new LinkedList();
    private LinkedList sportsQuestions = new LinkedList();
    private LinkedList rockQuestions = new LinkedList();
    
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			getPopQuestions().addLast("Pop Question " + i);
			getScienceQuestions().addLast(("Science Question " + i));
			getSportsQuestions().addLast(("Sports Question " + i));

			//TODO: inline method Game.createRockQuestion();
			getRockQuestions().addLast(createRockQuestion(i));
    	}
    }

    //TODO: change method to be private.
	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	//TODO: remove the unused method.
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	//TODO: The return value of the method is not used.
	public boolean add(String playerName) {
		
		
	    getPlayers().add(playerName);
	    getPlaces()[howManyPlayers()] = 0;
	    getPurses()[howManyPlayers()] = 0;
	    getInPenaltyBox()[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + getPlayers().size());
		return true;
	}

	//TODO: this method should be private because it is only used by its own class Game.
	public int howManyPlayers() {
		return getPlayers().size();
	}

	//TODO: Rename the name of the parameter of method Game.roll() to be rollingNumber.
	public void roll(int roll) {
		System.out.println(getPlayers().get(getCurrentPlayer()) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (getInPenaltyBox()[getCurrentPlayer()]) {
			if (roll % 2 != 0) {
				setGettingOutOfPenaltyBox(true);
				
				System.out.println(getPlayers().get(getCurrentPlayer()) + " is getting out of the penalty box");

				//TODO: Duplicate code in method Game.roll()
				getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] + roll;
				if (getPlaces()[getCurrentPlayer()] > 11) getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] - 12;
				
				System.out.println(getPlayers().get(getCurrentPlayer())
						+ "'s new location is " 
						+ getPlaces()[getCurrentPlayer()]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(getPlayers().get(getCurrentPlayer()) + " is not getting out of the penalty box");
				setGettingOutOfPenaltyBox(false);
				}
			
		} else {
		
			getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] + roll;
			if (getPlaces()[getCurrentPlayer()] > 11) getPlaces()[getCurrentPlayer()] = getPlaces()[getCurrentPlayer()] - 12;
			
			System.out.println(getPlayers().get(getCurrentPlayer())
					+ "'s new location is " 
					+ getPlaces()[getCurrentPlayer()]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(getPopQuestions().removeFirst());
		if (currentCategory() == "Science")
			System.out.println(getScienceQuestions().removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(getSportsQuestions().removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(getRockQuestions().removeFirst());
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
		if (getInPenaltyBox()[getCurrentPlayer()]){
			if (isGettingOutOfPenaltyBox()) {
				System.out.println("Answer was correct!!!!");
				getPurses()[getCurrentPlayer()]++;
				System.out.println(getPlayers().get(getCurrentPlayer())
						+ " now has "
						+ getPurses()[getCurrentPlayer()]
						+ " Gold Coins.");


				//TODO: Rename variable winner to be isGameStillInProgress
				boolean winner = didPlayerWin();
				setCurrentPlayer(getCurrentPlayer() + 1);
				if (getCurrentPlayer() == getPlayers().size()) setCurrentPlayer(0);
				
				return winner;
			} else {

				//TODO: Duplicate code in method Game.wasCorrectlyAnswered()
				setCurrentPlayer(getCurrentPlayer() + 1);
				if (getCurrentPlayer() == getPlayers().size()) setCurrentPlayer(0);
				return true;
			}
			
			
			
		} else {

			//TODO: Duplicate code in method Game.wasCorrectlyAnswered(). Outer.
			System.out.println("Answer was corrent!!!!");
			getPurses()[getCurrentPlayer()]++;
			System.out.println(getPlayers().get(getCurrentPlayer())
					+ " now has "
					+ getPurses()[getCurrentPlayer()]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			setCurrentPlayer(getCurrentPlayer() + 1);
			if (getCurrentPlayer() == getPlayers().size()) setCurrentPlayer(0);
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(getPlayers().get(getCurrentPlayer())+ " was sent to the penalty box");
		getInPenaltyBox()[getCurrentPlayer()] = true;
		
		setCurrentPlayer(getCurrentPlayer() + 1);
		if (getCurrentPlayer() == getPlayers().size()) setCurrentPlayer(0);

		//TODO: The return value of method Game.wrongAnswer() is unnecessary and should be eliminated.
		return true;
	}


	//TODO: The name of the method Game.didPlayerWin() should be Game.isGameStillInProgress()
	private boolean didPlayerWin() {
		return !(getPurses()[getCurrentPlayer()] == 6);
	}

	public boolean isGameStillInProgress() {
		return false;
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

	public LinkedList getPopQuestions() {
		return popQuestions;
	}

	public void setPopQuestions(LinkedList popQuestions) {
		this.popQuestions = popQuestions;
	}

	public LinkedList getScienceQuestions() {
		return scienceQuestions;
	}

	public void setScienceQuestions(LinkedList scienceQuestions) {
		this.scienceQuestions = scienceQuestions;
	}

	public LinkedList getSportsQuestions() {
		return sportsQuestions;
	}

	public void setSportsQuestions(LinkedList sportsQuestions) {
		this.sportsQuestions = sportsQuestions;
	}

	public LinkedList getRockQuestions() {
		return rockQuestions;
	}

	public void setRockQuestions(LinkedList rockQuestions) {
		this.rockQuestions = rockQuestions;
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
