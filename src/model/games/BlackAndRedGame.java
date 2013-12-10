package model.games;

public class BlackAndRedGame extends GameTable{
private String[] gameChoices;
	
	public BlackAndRedGame(){
		gameChoices[0] = "Black";
		gameChoices[1] = "Red";
	}
	public String[] getChoices(){
		return this.gameChoices;
	}
}
