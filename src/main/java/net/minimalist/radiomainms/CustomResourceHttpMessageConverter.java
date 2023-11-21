package net.minimalist.radiomainms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.ResourceHttpMessageConverter;

public class CustomResourceHttpMessageConverter extends ResourceHttpMessageConverter {

    public CustomResourceHttpMessageConverter() {
        System.out.println("USING MY CUSTOMRESOURCEHTTPMESSAGECONVERTER");
    }

    @Override
    protected void writeContent(Resource resource, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        try {
            InputStream in = resource.getInputStream();
            try {
                OutputStream out = outputMessage.getBody();
                in.transferTo(out);
                out.flush();
            } catch (NullPointerException ex) {
                // ignore, see SPR-13620
            } finally {
                try {
//                    in.close();
                } catch (Throwable ex) {
                    // ignore, see SPR-12999
                }
            }
        } catch (FileNotFoundException ex) {
            // ignore, see SPR-12999
        }
//        super.writeContent(resource, outputMessage);
    }
}
