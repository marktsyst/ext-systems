package edu.javacourse.net;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) throws IOException {
        for(int i=0; i<10; i++) {
            SimpleClient sc = new SimpleClient();
            sc.start();
        }
    }
}

class SimpleClient extends Thread {
    @Override
    public void run() {
        try (Socket socket = new Socket("127.0.0.1", 25225);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            System.out.println("Started:" + LocalDateTime.now());

            String userName = "Антон";
            bw.write(userName);
            bw.newLine();
            bw.flush();

            String answer = br.readLine();
            System.out.println("Client got string:" + answer);

            System.out.println("Finished:" + LocalDateTime.now());
        } catch (UnknownHostException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}