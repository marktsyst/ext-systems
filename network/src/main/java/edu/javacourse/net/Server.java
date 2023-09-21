package edu.javacourse.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static Map<String, Integer> userCount = new HashMap<>();
    public static Map<Integer, String> answerDict = Map.of(
            0, "Привет",
            1, "Виделись уже",
            2, "Снова ты",
            3, "Да сколько можно",
            4, "Заманал ты");

    public static void main(String[] args) throws IOException {
        try (ServerSocket socket = new ServerSocket(25225)) {
            System.out.println("Server is started");
            while (true) {
                Socket client = socket.accept();
                handleRequest(client);
            }
        }
    }

    private static void handleRequest(Socket client) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

            String userName = br.readLine();

            String answer = getAnswer(userName);

            System.out.println("Server got string:" + userName);
            StringBuilder sb = new StringBuilder(answer).append(", ").append(userName);
            bw.write(sb.toString());
            bw.newLine();
            bw.flush();
        }
        client.close();
    }

    private static String getAnswer(String userName) {
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
