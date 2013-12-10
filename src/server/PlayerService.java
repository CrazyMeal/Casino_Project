package server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

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
	private boolean online;
	private Player player;
	
	public PlayerService(){}
	
	public PlayerService(Socket serviceSocket){
		this.serviceSocket = serviceSocket;
		this.online = false;
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
					System.out.println(this.reader.readString());
					System.out.flush();
					break;
				case ProtocolBrute.GET_MY_BRUTE:
					this.createBrute();
					if(this.myBrute != null){
						this.writer.writeDiscriminant(ProtocolBrute.OK);
						this.writer.writeBrute(this.myBrute);
						this.writer.send();
						System.out.println("Brute envoyée");
						break;
					}
					else{
						this.writer.writeDiscriminant(ProtocolBrute.ERROR_GENERAL);
						this.writer.send();
						System.out.println("Erreur lors de l'envoi de la brute");
						break;
					}
					/* ------------------------------ OLD ---------------------------------------- */
				case Protocol.CONNECT_ME:
					this.player.setPseudo(this.cReader.readString());
					System.out.println("Player gives his pseudo => "+this.player.getPseudo());
					this.cWriter.writeDiscriminant(Protocol.OK);
					this.cWriter.send();
					break;
				case Protocol.BUY:
					this.player.getMoreCash();
					this.cWriter.writeDiscriminant(Protocol.OK);
					this.cWriter.send();
					break;
				case Protocol.CASH:
					this.cWriter.writeInt(this.player.getCash());
					this.cWriter.send();
					break;
				}
			}
		} catch (IOException e) {
			if(e.getClass().equals(SocketException.class)){
				this.stop();
				System.out.println("Client #"+this.getId()+" disconnected");
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public long getId(){
		return Thread.currentThread().getId();
	}
}
