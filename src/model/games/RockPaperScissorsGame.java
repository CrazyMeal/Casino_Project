package model.games;

public class RockPaperScissorsGame extends GameTable{
	public RockPaperScissorsGame(String name){
		this.gameChoices = new String[3];
		this.gameChoices[0] = "Paper";
		this.gameChoices[1] = "Rock";
		this.gameChoices[2] = "Scissors";
		this.name = name;
		
	}
	
	public void insertNewChoice(String newChoice){
		this.gameChoices[this.gameChoices.length+1] = newChoice;
	}
}
