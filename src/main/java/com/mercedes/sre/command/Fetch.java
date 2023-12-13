package com.mercedes.sre.command;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.misc.WebsiteConfiguration;
import com.mercedes.sre.storage.Datastore;

@Component
public class Fetch implements  Command{

  @Autowired
  WebsiteConfiguration websiteConfiguration;

  @Autowired
  Communication communication;

  @Autowired
  Datastore datastore;


    @Override
  public void execute(String optional, String subset) {

      Map<String, String> statusCodeMap = websiteConfiguration.getListWebsites()
              .stream()
              .filter(ws -> ws.contains(subset))
              .collect(Collectors.toMap(ws -> ws, communication::makeRestCall));

        saveDatastore(statusCodeMap);
      if (optional.equals("y")){
        datastore.getDataMap().forEach((key, value) -> System.out.println(">>>>" + key + " # " + value.getKey() +" # "+value.getValue()));
      }

  }

    private void saveDatastore(Map<String, String> statusCodeMap) {
        statusCodeMap.entrySet()
                .stream()
                .forEach((e) -> datastore.getDataMap().put(e.getKey(), Map.entry(LocalDateTime.now(), e.getValue())));
    }
}
