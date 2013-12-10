package model.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class CasinoWriter {
	private OutputStream outputStream;
	private ByteArrayOutputStream byteOutputStream;
	
	public CasinoWriter(){}
	
	public CasinoWriter(OutputStream outputStream){
		this.outputStream = outputStream;
		this.byteOutputStream = new ByteArrayOutputStream();
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
		this.byteOutputStream.write(i);
	}
	public void writeByte(byte valueByte){
		this.byteOutputStream.write(valueByte);
	}
	public void writeDiscriminant(byte discriminant){
		this.writeByte(discriminant);
	}
	public void send() throws IOException{
		ByteArrayOutputStream message = new ByteArrayOutputStream();
        message.write(this.byteOutputStream.toByteArray());
        byte[] send = message.toByteArray();
        
        this.outputStream.write(send);
        this.flush();
	}
	public void flush(){
		try {
			this.byteOutputStream.flush();
			this.outputStream.flush();
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
