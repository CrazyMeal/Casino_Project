package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import modelold.Brute;
import modelold.BruteReader;
import modelold.BruteWriter;
import modelold.Protocol;


public class ClientCore {
	private Socket socket;
	private BruteReader reader;
	private BruteWriter writer;
	private boolean alive;
	
	public ClientCore(){}
	
	public ClientCore(String hostName,int port){
		try {
			this.socket = new Socket(hostName,port);
			this.reader = new BruteReader(this.socket.getInputStream());
			this.writer = new BruteWriter(this.socket.getOutputStream());
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
					this.writer.writeDiscriminant(Protocol.TEST);
					this.writer.writeString("SALUT");
					this.writer.send();
					System.out.println("trame envoyée");
				}
				if(userInput.equals("brute")){
					this.writer.writeDiscriminant(Protocol.GET_MY_BRUTE);
					this.writer.send();
					if(this.reader.readDiscriminant() == Protocol.OK){
						Brute brute = this.reader.readBrute();
						System.out.println(brute.toString());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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