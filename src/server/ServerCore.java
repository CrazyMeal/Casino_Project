package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class ServerCore {
	private int port;
	private boolean online;
	private ServerSocket serverSocket;
	private HashMap<Long,Thread> services;

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
			this.online = true;
			System.out.println("Server online");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (this.online) {
			try {
				Thread thread = new Thread(new ServerService(serverSocket.accept()));
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
}