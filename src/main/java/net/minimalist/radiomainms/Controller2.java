package net.minimalist.radiomainms;

import java.io.BufferedInputStream;
import java.io.IOException;

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
public class Controller2 {

    private final Fm104 fm104;

    @RequestMapping(value = "/main2", method = RequestMethod.GET, produces = {"audio/mpeg"})
    public ResponseEntity spring() throws IOException {
        BufferedInputStream pipedInputStream = fm104.play();
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity(new InputStreamResource(pipedInputStream), httpHeaders, HttpStatus.OK);
    }



}
