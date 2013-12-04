package modelold;

import java.util.HashMap;
import java.util.Map.Entry;

public class Brute {
	private String name;
	private int level,life,strength,speed;
	private HashMap<String,Bonus> bonuses;
	
	public Brute(){
		this.name = "Default";
		this.level = 0;
		this.life = 0;
		this.strength = 0;
		this.speed = 0;
		this.bonuses = new HashMap<String,Bonus>();
	}
	public Brute(String name){
		this.name = name;
		this.level = 1;
		this.life = 100;
		this.strength = 10;
		this.speed = 10;
		this.bonuses = new HashMap<String,Bonus>();
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Name: "+this.name+"\n");
		sb.append("Level: "+this.level+"\n");
		sb.append("Life: "+this.life+"\n");
		sb.append("Strength: "+this.strength+"\n");
		sb.append("Speed: "+this.speed+"\n");
		if(this.bonuses.size() != 0){
			for(Entry<String, Bonus> bonus : this.bonuses.entrySet()){
				sb.append("un bonus \n"+ bonus.toString());
			}
		}
		return sb.toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public HashMap<String,Bonus> getBonuses() {
		return bonuses;
	}
	public void setBonuses(HashMap<String,Bonus> bonuses) {
		this.bonuses = bonuses;
	}
}
