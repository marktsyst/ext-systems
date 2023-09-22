package edu.javacourse.net;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) throws IOException {
        for(int i=0; i<5; i++) {
            SimpleClient sc = new SimpleClient(i);
            sc.start();
        }
    }
}

class SimpleClient extends Thread {
    private static final String[] COMMAND = {"HELLO", "MORNING", "DAY", "EVENING"};

    private int cmdNumber;

    public SimpleClient(int cmdNumber) {
        this.cmdNumber = cmdNumber;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("127.0.0.1", 25225);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            //System.out.println("Started:" + LocalDateTime.now());

            String command = COMMAND[cmdNumber % COMMAND.length];
            String request = command + " " + "Антон";
            bw.write(request);
            bw.newLine();
            bw.flush();

            String answer = br.readLine();
            System.out.println("Client got string:" + answer);

            //System.out.println("Finished:" + LocalDateTime.now());
        } catch (UnknownHostException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}