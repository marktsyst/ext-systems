package javacourse.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Server {
    public static void main(String[] args) throws IOException {
        Map<String, Greetable> handlers = loadHandlers();

        try (ServerSocket socket = new ServerSocket(25225, 200)) {
            System.out.println("Server is started");
            while (true) {
                Socket client = socket.accept();
                new SimpleServer(client, handlers).start();
            }
        }
    }

    private static Map<String, Greetable> loadHandlers() {
        Map<String, Greetable> result = new HashMap<>();

        try (InputStream is = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            Properties properties = new Properties();
            properties.load(is);

            for (Object command: properties.keySet()) {
                String className = properties.getProperty(command.toString());
                Class<Greetable> cl = (Class<Greetable>) Class.forName(className);
                Greetable handler = cl.getConstructor().newInstance();
                result.put(command.toString(), handler);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return result;
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
    private Map<String, Greetable> handlers;

    public SimpleServer(Socket client, Map<String, Greetable> handlers) {
        this.client = client;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        handleRequest(client);
    }
    private void handleRequest(Socket client) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

            String request = br.readLine();
            String[] lines = request.split("\\s+");
            String command = lines[0];
            String userName = lines[1];

            System.out.println("Server got string 1:" + command);
            System.out.println("Server got string 2:" + userName);

            String response = buildResponse(command, userName);

            //String answer = getAnswer(userName);

            System.out.println("Server got string:" + userName);
            //Thread.sleep(2000);//StringBuilder sb = new StringBuilder(ук).append(", ").append(userName);
            bw.write(response);
            bw.newLine();
            bw.flush();
            client.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private String buildResponse(String command, String userName) {
        String answer = userName + ", " + getAnswer(userName);
        Greetable handler = handlers.get(command);
        if (handler != null) {
            return handler.buildResponse(answer);
        }
        return "Hello, " + answer;
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
