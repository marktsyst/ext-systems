package edu.javacourse.net;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 7; i++) {
            sendRequest();
        }
    }

    private static void sendRequest() throws IOException {
        try (Socket socket = new Socket("127.0.0.1", 25225);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String userName = "Антон";
            bw.write(userName);
            bw.newLine();
            bw.flush();

            String answer = br.readLine();
            System.out.println("Client got string:" + answer);
        }
    }
}
