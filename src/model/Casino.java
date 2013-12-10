package model;

import java.util.HashMap;
import java.util.Map.Entry;

import model.games.GameTable;

public class Casino {
	private HashMap<String,GameTable> tablesList;
	private String name;
	
	public Casino(){}
	
	public Casino(String name){
		this.name = name;
		this.tablesList = new HashMap<String,GameTable>();
	}
	
	GameTable getTableByObject(GameTable gameTable){
		GameTable tableToReturn = null;
		for(Entry<String, GameTable> gameTableEntry : this.tablesList.entrySet()){
			if(gameTableEntry.equals(gameTable)){
				tableToReturn = gameTableEntry.getValue();
			}
		}
		if(tableToReturn != null){
			System.out.println("Pas de récupération de table trouvée");
			return null;
		}else{
			return tableToReturn;
		}
	}
	GameTable getTableByName(String tableName){
		return this.tablesList.get(tableName);
	}
	public HashMap<String,GameTable> getTablesList() {
		return tablesList;
	}

	public void setTablesList(HashMap<String,GameTable> tablesList) {
		this.tablesList = tablesList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
