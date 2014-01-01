package model.net;

public class Protocol {
	public static final int port = 9998;
	
	// Discriminants
	public static final byte CONNECT_ME = (byte) 0x01;
	public static final byte GET_TABLE_LIST = (byte) 0x02;
	public static final byte GET_STATE = (byte) 0x03;
	public static final byte JOIN_TABLE = (byte) 0x04;
	public static final byte BUY = (byte) 0x05;
	public static final byte CASH = (byte) 0x06;
	public static final byte BID = (byte) 0x07;
	public static final byte LEAVE = (byte) 0x08;
	
	public static final byte MAKE_WIN = (byte) 0x10;
	public static final byte MAKE_LOSE = (byte) 0x11;
	
	public static final byte OK = (byte) 0x90;
	public static final byte ERROR_GENERAL = (byte) 0x91;

	public static final byte TEST = (byte) 0x99;
}
