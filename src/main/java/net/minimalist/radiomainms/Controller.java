package net.minimalist.radiomainms;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Log
@RestController
public class Controller {

    private final Fm104 fm104;

    @RequestMapping(value = "/play", method = RequestMethod.GET, produces = { "audio/mpeg" })
    public ResponseEntity playAudio(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("[downloadRecipientFile]");

        HttpHeaders httpHeaders = new HttpHeaders();
        BufferedInputStream pipedInputStream = fm104.play();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(pipedInputStream);

        return new ResponseEntity(new InputStreamResource(bufferedInputStream), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = { "audio/mpeg" })
    public ResponseEntity hello() throws IOException {
        InputStream inputStream = new URL("https://wg.cdn.tibus.net/fm104MP3128").openStream();

        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        Thread copyThread = new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    pipedOutputStream.write(buffer, 0, bytesRead);
                }
//                    pipedOutputStream.close(); // Close the output stream when done
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        copyThread.start();
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity(new InputStreamResource(pipedInputStream), httpHeaders, HttpStatus.OK);
    }


    @RequestMapping(value = "/best", method = RequestMethod.GET, produces = { "audio/mpeg" })
    public ResponseEntity spring() throws IOException {
        InputStream inputStream =  new URL("https://wg.cdn.tibus.net/fm104MP3128").openStream();
        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(new InputStreamResource(inputStream), httpHeaders, HttpStatus.OK);
    }
}
