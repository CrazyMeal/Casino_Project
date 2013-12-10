package model.games;

public class GameTable {
	private String name;
	
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
}
