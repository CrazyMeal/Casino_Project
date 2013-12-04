package server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import modelold.Brute;
import modelold.BruteReader;
import modelold.BruteWriter;
import modelold.Protocol;

public class ServerService implements Runnable{
	private BruteReader reader;
	private BruteWriter writer;
	private Socket serviceSocket;
	private boolean online;
	private Brute myBrute;
	private String pseudo;
	
	public ServerService(){}
	
	public ServerService(Socket serviceSocket){
		this.serviceSocket = serviceSocket;
		this.online = false;
	}
	
	@Override
	public void run() {
		this.online = true;
		System.out.println("Service launched for client #"+this.getId());
		try {
			this.reader = new BruteReader(this.serviceSocket.getInputStream());
			this.writer = new BruteWriter(this.serviceSocket.getOutputStream());
			while(this.online){
				byte discriminant = this.reader.readDiscriminant();
				switch(discriminant){
				case Protocol.TEST:
					System.out.println("trame de test");
					System.out.println(this.reader.readString());
					System.out.flush();
					break;
				case Protocol.GET_MY_BRUTE:
					this.createBrute();
					if(this.myBrute != null){
						this.writer.writeDiscriminant(Protocol.OK);
						this.writer.writeBrute(this.myBrute);
						this.writer.send();
						System.out.println("Brute envoyée");
						break;
					}
					else{
						this.writer.writeDiscriminant(Protocol.ERROR_GENERAL);
						this.writer.send();
						System.out.println("Erreur lors de l'envoi de la brute");
						break;
					}
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

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
}
