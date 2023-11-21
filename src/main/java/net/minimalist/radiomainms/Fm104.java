package net.minimalist.radiomainms;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
public class Fm104 {

    InputStream inputStream;
    PipedInputStream pipedInputStream;
    PipedOutputStream pipedOutputStream;

    public Fm104() throws IOException {

        inputStream = new URL("https://wg.cdn.tibus.net/fm104MP3128").openStream();
        log.info("create stream fm104");

        pipedOutputStream = new PipedOutputStream();
        pipedInputStream = new PipedInputStream(pipedOutputStream);

        Thread copyThread = new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    pipedOutputStream.write(buffer, 0, bytesRead);
                }
//                    pipedOutputStream.close(); // Close the output stream when done
            } catch (IOException e) {
                System.out.println("thread error");
                e.printStackTrace();
            }
            System.out.println("thread finish");
        });

        copyThread.start();
    }

    public BufferedInputStream play() throws IOException {
        log.info("connected to fm104 bean (play)");

        return new BufferedInputStream(pipedInputStream, 512);
    }
}

// Create a thread to read from the PipedInputStream
//            Thread readerThread = new Thread(() -> {
//                try {
//                    int data;
//                    while ((data = pipedInputStream.read()) != -1) {
//                        // Process or print the data as needed
//                        System.out.print((char) data);
//                    }
//                    pipedInputStream.close(); // Close the input stream when done
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
