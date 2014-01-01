package model.games;

public class BlackAndRedGame extends GameTable{
	public BlackAndRedGame(String name){
		this.gameChoices = new String[2];
		this.gameChoices[0] = "Black";
		this.gameChoices[1] = "Red";
		this.name = name;
	}
}
