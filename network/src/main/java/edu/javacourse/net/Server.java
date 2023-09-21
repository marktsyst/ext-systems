package edu.javacourse.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket socket = new ServerSocket(25225, 200)) {
            System.out.println("Server is started");
            while (true) {
                Socket client = socket.accept();
                new SimpleServer(client).start();
            }
        }
    }
}

class SimpleServer extends Thread {
    private static Map<String, Integer> userCount = new HashMap<>();
    private static Map<Integer, String> answerDict = Map.of(
            0, "Привет",
            1, "Виделись уже",
            2, "Снова ты",
            3, "Да сколько можно",
            4, "Заманал ты");

    private Socket client;

    public SimpleServer(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        handleRequest(client);
    }
    private void handleRequest(Socket client) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

            String userName = br.readLine();

            String answer = getAnswer(userName);

            System.out.println("Server got string:" + userName);
            Thread.sleep(2000);
            StringBuilder sb = new StringBuilder(answer).append(", ").append(userName);
            bw.write(sb.toString());
            bw.newLine();
            bw.flush();
            client.close();
        } catch (InterruptedException  | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private String getAnswer(String userName) {
        var count = userCount.get(userName);
        if (count == null) {
            count = 0;
        }

        var answer = answerDict.get(count);
        if (answer == null) {
            answer = "Иди ты на х";
        }

        userCount.put(userName, ++count);

        return answer;
    }
}
