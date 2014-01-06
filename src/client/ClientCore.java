package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import model.net.CasinoReader;
import model.net.CasinoWriter;
import model.net.Protocol;
import modelold.Brute;
import modelold.BruteReader;
import modelold.BruteWriter;
import modelold.ProtocolBrute;


public class ClientCore {
	private BruteReader reader;
	private BruteWriter writer;
	
	private Socket socket;
	private CasinoReader cReader;
	private CasinoWriter cWriter;
	private boolean alive;
	
	public ClientCore(){}
	
	public ClientCore(String hostName,int port){
		try {
			this.socket = new Socket(hostName,port);
			this.reader = new BruteReader(this.socket.getInputStream());
			this.writer = new BruteWriter(this.socket.getOutputStream());
			
			this.cReader = new CasinoReader(this.socket.getInputStream());
			this.cWriter = new CasinoWriter(this.socket.getOutputStream());
			
			CasinoFrame frame = new CasinoFrame(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run(){
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		this.alive = true;
		String userInput;
        try {
			while (this.alive && (userInput = stdIn.readLine()) != null) {
				if(userInput.equals("test")){
					this.writer.writeDiscriminant(ProtocolBrute.TEST);
					this.writer.writeString("SALUT");
					this.writer.send();
					System.out.println("trame envoyée");
				}
				if(userInput.equals("brute")){
					this.writer.writeDiscriminant(ProtocolBrute.GET_MY_BRUTE);
					this.writer.send();
					if(this.reader.readDiscriminant() == ProtocolBrute.OK){
						Brute brute = this.reader.readBrute();
						System.out.println(brute.toString());
					}
				}
				if(userInput.contains("connect")){
					String pseudo = userInput.split(" ")[1];
					this.cWriter.writeDiscriminant(Protocol.CONNECT_ME);
					this.cWriter.writeString(pseudo);
					this.cWriter.send();
					this.cReader.readDiscriminant();
					System.out.println("OK !");
				}
				if(userInput.contains("tables")){
					this.cWriter.writeDiscriminant(Protocol.GET_TABLE_LIST);
					this.cWriter.send();
					ArrayList<String> list = this.cReader.readTables();
					System.out.println(list.get(0));
					System.out.println(list.get(1));
				}
				if(userInput.contains("join")){
					this.cWriter.writeByte(Protocol.JOIN_TABLE);
					this.cWriter.writeString(userInput.split(" ")[1]);
					this.cWriter.send();
					ArrayList<String> list2 = this.cReader.readChoosenTable();
					System.out.println(list2.get(0));
					System.out.println(list2.get(1));
				}
				// Link to frame done
				if(userInput.contains("leave")){
					this.cWriter.writeDiscriminant(Protocol.LEAVE);
					this.cWriter.send();
					if(this.cReader.readDiscriminant() == Protocol.OK){
						System.out.println("Deconnecté avec succès");
						this.alive = false;
					}
					else{
						System.out.println("Erreur lors de la deconnection");
					}
				}
				// Link to frame done
				if(userInput.equals("buy")){
					this.cWriter.writeDiscriminant(Protocol.BUY);
					this.cWriter.send();
					if(this.cReader.readDiscriminant() == Protocol.OK){
						System.out.println("Achat effectué avec succès");
					}else{
						System.out.println("Echec lors de l'achat de cash");
					}
				}
				if(userInput.contains("bid")){
					this.cWriter.writeDiscriminant(Protocol.BID);
					this.cWriter.writeInt(Integer.parseInt(userInput.split(" ")[1]));
					this.cWriter.send();
					if(this.cReader.readDiscriminant() == Protocol.OK){
						System.out.println("Paris enregistré avec succès");
					} else {
						System.out.println("Echec lors de l'enregistrement du paris");
					}
				}
				if(userInput.equals("cash")){
					this.cWriter.writeDiscriminant(Protocol.CASH);
					this.cWriter.send();
					System.out.println("Vous avez actuellement "+this.cReader.readInt());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendPseudo(String pseudo){
		this.cWriter.writeDiscriminant(Protocol.CONNECT_ME);
		this.cWriter.writeString(pseudo);
		try {
			this.cWriter.send();
			this.cReader.readDiscriminant();
			System.out.println("OK !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void doLeave(){
		this.cWriter.writeDiscriminant(Protocol.LEAVE);
		try {
			this.cWriter.send();
			if(this.cReader.readDiscriminant() == Protocol.OK){
				System.out.println("Deconnecté avec succès");
				this.alive = false;
			}
			else{
				System.out.println("Erreur lors de la deconnection");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void doBuy(){
		this.cWriter.writeDiscriminant(Protocol.BUY);
		try {
			this.cWriter.send();
			if(this.cReader.readDiscriminant() == Protocol.OK){
				System.out.println("Achat effectué avec succès");
			}else{
				System.out.println("Echec lors de l'achat de cash");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int doCash(){
		this.cWriter.writeDiscriminant(Protocol.CASH);
		try {
			this.cWriter.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int cash = this.cReader.readInt();
		System.out.println("Vous avez actuellement "+cash);
		return cash;
	}
	public void doBid(int bid){
		this.cWriter.writeDiscriminant(Protocol.BID);
		this.cWriter.writeInt(bid);
		try {
			this.cWriter.send();
			if(this.cReader.readDiscriminant() == Protocol.OK){
				System.out.println("Paris enregistré avec succès");
			} else {
				System.out.println("Echec lors de l'enregistrement du paris");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String[] doList(){
		this.cWriter.writeDiscriminant(Protocol.GET_TABLE_LIST);
		try {
			this.cWriter.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<String> list = this.cReader.readTables();
		String[] stockArr = new String[list.size()];
	    stockArr = list.toArray(stockArr);
	    for(String s : stockArr)
	        System.out.println(s);
		return stockArr;
	}
	public String[] doJoin(String choice){
		this.cWriter.writeByte(Protocol.JOIN_TABLE);
		this.cWriter.writeString(choice);
		try {
			this.cWriter.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<String> list = this.cReader.readChoosenTable();
		String[] stockArr = new String[list.size()];
	    stockArr = list.toArray(stockArr);
	    for(String s : stockArr)
	        System.out.println(s);
		return stockArr;
	}
	public void stop(){
		try {
			this.writer.close();
			this.reader.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}