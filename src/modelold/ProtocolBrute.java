package modelold;

public class ProtocolBrute {
	public static final int port = 9998;
	
	// Discriminants
	public static final byte GET_MY_BRUTE = (byte) 0x81;
	public static final byte UPDATE_MY_BRUTE =(byte) 0x82;
	public static final byte DELETE_MY_BRUTE = (byte) 0x83;
	
	public static final byte MAKE_WIN = (byte) 0x10;
	public static final byte MAKE_LOSE = (byte) 0x11;
	
	public static final byte GET_IMAGE = (byte) 0x20;
	public static final byte GET_BONUS = (byte) 0x21;
	
	public static final byte OK = (byte) 0x95;
	public static final byte ERROR_GENERAL = (byte) 0x94;

	public static final byte TEST = (byte) 0x99;
}
