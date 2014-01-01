package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import model.Casino;
import model.games.BlackAndRedGame;
import model.games.RockPaperScissorsGame;

public class ServerCore {
	private int port;
	private boolean online;
	private ServerSocket serverSocket;
	private HashMap<Long,Thread> services;
	private Casino casino;
	
	public ServerCore() {
	}

	public ServerCore(int port) {
		this.port = port;
		this.online = false;
		this.services = new HashMap<Long,Thread>();
		this.init();
	}

	public void init() {
		try {
			this.serverSocket = new ServerSocket(this.port);
			this.casino = new Casino("My awesome Casino");
			this.casino.insertGameTable(new RockPaperScissorsGame("RPS"));
			this.casino.insertGameTable(new BlackAndRedGame("BlackAndRed"));
			this.online = true;
			System.out.println("Server online");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (this.online) {
			try {
				Thread thread = new Thread(new PlayerService(serverSocket.accept(),this.casino));
				this.services.put(thread.getId(),thread);
				
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void stop(){
		try {
			this.serverSocket.close();
			this.online = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Casino getCasino() {
		return casino;
	}

	public void setCasino(Casino casino) {
		this.casino = casino;
	}
}