package main;

import java.io.IOException;

import modelold.Protocol;

import server.ServerCore;

public class MainExecServer {
    public static void main(String[] args) throws IOException {
        
        ServerCore server = new ServerCore(Protocol.port);
        server.run();
    }
}