package model.games;

public class RockPaperScissorsGame extends GameTable{
	private String[] gameChoices;
	
	public RockPaperScissorsGame(){
		gameChoices[0] = "Paper";
		gameChoices[1] = "Rock";
		gameChoices[2] = "Scissors";
	}
	
	public void insertNewChoice(String newChoice){
		this.gameChoices[this.gameChoices.length+1] = newChoice;
	}
	public String[] getChoices(){
		return this.gameChoices;
	}
}
