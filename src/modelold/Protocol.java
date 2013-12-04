package modelold;

public class Protocol {
	public static final int port = 9998;
	
	// Discriminants
	public static final byte GET_MY_BRUTE = (byte) 0x01;
	public static final byte UPDATE_MY_BRUTE =(byte) 0x02;
	public static final byte DELETE_MY_BRUTE = (byte) 0x03;
	
	public static final byte MAKE_WIN = (byte) 0x10;
	public static final byte MAKE_LOSE = (byte) 0x11;
	
	public static final byte GET_IMAGE = (byte) 0x20;
	public static final byte GET_BONUS = (byte) 0x21;
	
	public static final byte OK = (byte) 0x90;
	public static final byte ERROR_GENERAL = (byte) 0x91;

	public static final byte TEST = (byte) 0x99;
}
