package model.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map.Entry;

import model.Casino;
import model.games.GameTable;

public class CasinoWriter {
	private OutputStream outputStream;
	private ByteArrayOutputStream byteOutputStream;
	
	public CasinoWriter(){}
	
	public CasinoWriter(OutputStream outputStream){
		this.outputStream = outputStream;
		this.byteOutputStream = new ByteArrayOutputStream();
	}
	public void writeChoosenTable(GameTable gameTable){
		int size = gameTable.getSize();
		this.writeInt(size);
		for(int i=0;i<size;i++){
			this.writeString(gameTable.getChoices()[i]);
		}
	}
	public void writeTables(Casino casino){
		int size = casino.getTablesList().size();
		this.writeInt(size);
		for(Entry<String, GameTable> gt : casino.getTablesList().entrySet()){
			this.writeString(gt.getKey());
		}
	}
	public void writeString(String string){
		try {
			byte[] str = string.getBytes(Charset.forName("UTF-8"));
			this.writeInt(str.length);
			this.byteOutputStream.write(str);
		} catch (IOException e) {
			System.out.println("Probleme lors de l'ecriture de String");
			e.printStackTrace();
		}
	}
	public void writeInt(int i){
		//this.byteOutputStream.write(i);
		try {
			this.byteOutputStream.write(ByteBuffer.allocate(4).putInt(i).array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeByte(byte valueByte){
		this.byteOutputStream.write(valueByte);
	}
	public void writeDiscriminant(byte discriminant){
		this.writeByte(discriminant);
	}
	public void send() throws IOException{
		byte[] send = this.byteOutputStream.toByteArray();
        this.outputStream.write(send);
        this.flush();
	}
	public void flush(){
		try {
			this.byteOutputStream.flush();
			this.outputStream.flush();
			this.byteOutputStream.reset();
		} catch (IOException e) {
			System.out.println("Probleme lors du flush des streams");
			e.printStackTrace();
		}
	}
	public void close() throws IOException{
		this.outputStream.close();
		this.byteOutputStream.close();
	}
	public OutputStream getOutputStream() {
		return outputStream;
	}
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
}
