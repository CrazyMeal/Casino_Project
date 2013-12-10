package main;

import java.io.IOException;

import modelold.ProtocolBrute;

import server.ServerCore;

public class MainExecServer {
    public static void main(String[] args) throws IOException {
        
        ServerCore server = new ServerCore(ProtocolBrute.port);
        server.run();
    }
}