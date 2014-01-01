package model.games;

public class GameTable {
	protected String name;
	protected String[] gameChoices;
	
	public GameTable(){	}
	
	public GameTable(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String[] getChoices(){
		return this.gameChoices;
	}
	public int getSize(){
		return this.gameChoices.length;
	}
}
