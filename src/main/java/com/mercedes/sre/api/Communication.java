package com.mercedes.sre.api;

import java.util.concurrent.Future;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Communication extends Thread{

    private final RestTemplate restTemplate;

    public Communication() {
        this.restTemplate = new RestTemplate();
        log.info("restTemplate: " + restTemplate);
    }


    public String makeRestCall(String url) {

        HttpStatusCode statusCode;
        try {
            // OK
            statusCode = restTemplate.getForEntity(url, String.class).getStatusCode();
        } catch (HttpClientErrorException e) {
            // 404 and others
            statusCode = e.getStatusCode();
        }catch (Exception e) {
            //Unexpected
            return "Unknown";
        }
        return statusCode.toString();
    }
}
