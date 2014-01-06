package server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import model.Casino;
import model.Player;
import model.net.CasinoReader;
import model.net.CasinoWriter;
import model.net.Protocol;
import modelold.Brute;
import modelold.BruteReader;
import modelold.BruteWriter;
import modelold.ProtocolBrute;

public class PlayerService implements Runnable{
	private Brute myBrute;
	private BruteReader reader;
	private BruteWriter writer;
	
	private Socket serviceSocket;
	private CasinoReader cReader;
	private CasinoWriter cWriter;
	private Casino casino;
	private boolean online;
	private Player player;
	
	public PlayerService(){}
	
	public PlayerService(Socket serviceSocket,Casino casino){
		this.serviceSocket = serviceSocket;
		this.online = false;
		this.casino = casino;
		this.player = new Player();
	}
	
	@Override
	public void run() {
		this.online = true;
		System.out.println("Service launched for client #"+this.getId());
		try {
			this.reader = new BruteReader(this.serviceSocket.getInputStream());
			this.writer = new BruteWriter(this.serviceSocket.getOutputStream());
			
			this.cReader = new CasinoReader(this.serviceSocket.getInputStream());
			this.cWriter = new CasinoWriter(this.serviceSocket.getOutputStream());
			
			while(this.online){
				byte discriminant = this.reader.readDiscriminant();
				switch(discriminant){
				/* ------------------------------ OLD ---------------------------------------- */
				case ProtocolBrute.TEST:
					System.out.println("trame de test");
					System.out.println(this.cReader.readInt());
					this.cWriter.writeInt(256);
					this.cWriter.send();
					break;
					/* ------------------------------ OLD ---------------------------------------- */
				case Protocol.CONNECT_ME:
					this.player.setPseudo(this.cReader.readString());
					System.out.println("Player gives his pseudo => "+this.player.getPseudo());
					this.cWriter.writeDiscriminant(Protocol.OK);
					this.cWriter.send();
					break;
				case Protocol.GET_TABLE_LIST:
					System.out.println("Client demande liste des table");
					this.cWriter.writeTables(this.casino);
					this.cWriter.send();
					break;
				case Protocol.GET_STATE:
					break;
				case Protocol.JOIN_TABLE:
					System.out.println("Client veut join");
					String tableChoosen = this.cReader.readString();
					this.cWriter.writeChoosenTable(this.casino.getTablesList().get(tableChoosen));
					this.cWriter.send();
					break;
				case Protocol.BUY:
					this.player.getMoreCash();
					System.out.println("Le joueur "+this.player.getPseudo()+" a maintenant "+String.valueOf(this.player.getCash())+"$");
					this.cWriter.writeDiscriminant(Protocol.OK);
					this.cWriter.send();
					break;
				case Protocol.CASH:
					this.cWriter.writeInt(this.player.getCash());
					System.out.println("Envoi du montant du cash au joueur"+this.player.getCash());
					this.cWriter.send();
					break;
				case Protocol.BID:
					int bid = this.cReader.readInt();
					this.player.setBid(bid);
					System.out.println("Le joueur vient de parier"+String.valueOf(bid));
					this.cWriter.writeDiscriminant(Protocol.OK);
					this.cWriter.send();
					break;
				case Protocol.LEAVE:
					this.online = false;
					this.cWriter.writeDiscriminant(Protocol.OK);
					this.cWriter.send();
					break;
				}
			}
			this.stop();
		} catch (IOException e) {
			if(e.getClass().equals(SocketException.class)){
				this.stop();
				System.out.println("Client #"+this.getId()+" déconnecté inopinément");
			}
			else
				e.printStackTrace();
		}
	}
	public void createBrute(){
		this.myBrute = new Brute("Patator");
	}
	public void stop(){
		try {
			this.reader.close();
			this.writer.close();
			this.online = false;
			this.serviceSocket.close();
			System.out.println("Client #"+this.getId()+" s'est déconnecté");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public long getId(){
		return Thread.currentThread().getId();
	}

	public Casino getCasino() {
		return casino;
	}

	public void setCasino(Casino casino) {
		this.casino = casino;
	}
}
