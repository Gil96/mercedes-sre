package com.mercedes.sre.command;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatusCode;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.misc.WebsiteConfiguration;

public class Fetch implements  Command{

  // @Autowired
  // WebsiteConfiguration websiteConfiguration;

  // @Autowired
  // Communication communication;


    @Override
  public void execute( WebsiteConfiguration websiteConfiguration) {

        ExecutorService es = Executors.newFixedThreadPool(8);
        Communication communication = new Communication();

        for (final String ws : websiteConfiguration.getListWebsites()) {
            es.submit(new Runnable() {
                @Override
                public void run() {
                    String status = communication.makeRestCall(ws);
                    System.out.println("->->status: " + status);
                }
            });
        }

        es.shutdown();

      Map<String, String> statusCodeMap = websiteConfiguration.getListWebsites()
              .stream()
              .collect(Collectors.toMap(ws -> ws, communication::makeRestCall));

      // save

      // bonus

      statusCodeMap.forEach((key, value) -> System.out.println(">>> " + key + " -> " + value));

  }
}
