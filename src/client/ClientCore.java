package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
					this.cWriter.writeDiscriminant(Protocol.CONNECT_ME);
					this.cWriter.writeString("mon pseudoooo");
					this.cWriter.send();
					this.cReader.readDiscriminant();
					System.out.println("OK !");
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