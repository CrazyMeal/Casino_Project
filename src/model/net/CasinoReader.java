package model.net;

import java.io.IOException;
import java.io.InputStream;

public class CasinoReader {
private InputStream inputStream;
	
	public CasinoReader(){}
	
	public CasinoReader(InputStream inputStream){
		this.inputStream = inputStream;
	}
	
	public byte readDiscriminant() throws IOException{
		return this.readByte();
	}
	public String readString(){
		String outString = null;
		try{
			int stringSize = this.readInt();
			byte[] bytedString = new byte[stringSize];
			int read = 0;
			while(read < stringSize){
				read+= this.inputStream.read(bytedString,read,stringSize-read);
			}
			outString = new String(bytedString,"UTF-8");
		}catch (IOException e) {
			System.out.println("Problem lors de lecture de String");
			e.printStackTrace();
		}
		return outString;
	}
	public int readInt(){
		int tmp = 0;
		try {
			tmp = this.inputStream.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tmp;
	}
	public byte readByte() throws IOException{
		byte[] b = new byte[1];
		this.inputStream.read(b);
		return b[0];
	}
	public void close() throws IOException{
		this.inputStream.close();
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
