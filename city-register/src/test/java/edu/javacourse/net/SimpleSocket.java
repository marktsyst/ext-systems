package edu.javacourse.net;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleSocket {
    @Test
    public void simpleSocket() throws IOException {
        try (Socket socket = new Socket("localhost", 8082);
             InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream()) {

            String command = "GET / HTTP/1.1\r\nHost:java-course.ru\r\n\r\n";
            os.write(command.getBytes());
            os.flush();

            int c = 0;
            while ((c = is.read()) != -1) {
                System.out.print((char)c);
            }
        }
    }
}
