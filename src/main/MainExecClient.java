package main;

import java.io.IOException;

import client.ClientCore;
 
public class MainExecClient {
    public static void main(String[] args) throws IOException {

        String hostName = "localhost";
        int portNumber = 9998;
 
        ClientCore client = new ClientCore(hostName,portNumber);
        client.run();
    }
}
