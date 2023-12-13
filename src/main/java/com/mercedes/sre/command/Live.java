package com.mercedes.sre.command;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.misc.WebsiteConfiguration;
import com.mercedes.sre.storage.Datastore;

@Component
public class Live implements Command {

    @Autowired
    WebsiteConfiguration websiteConfiguration;

    @Autowired
    Communication communication;

    @Autowired
    Datastore datastore;

    @Value("${live.pooling.interval}")
    Integer poolingInterval;

    @Value("${live.time.duration}")
    Integer timeDuration;

    @Override
    public void execute(String optional, String subset) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            liveExecution(optional, subset);
        }, 0, poolingInterval, TimeUnit.SECONDS);

        // Schedule a stop after 1 minute
        scheduler.schedule(() -> {
            scheduler.shutdown();
            System.out.println("Live task stopped after " + timeDuration.toString() + " seconds" );
        }, timeDuration, TimeUnit.SECONDS);
    }

    private void liveExecution(String optional, String subset) {

        Map<String, String> urlStatus = scanUrlStatus(subset);
        if (optional.equals("y")) {
            urlStatus.entrySet().stream().forEach(entry -> System.out.println(">>>>" + entry.getKey() + " # " + entry.getValue() + " # " + LocalDateTime.now()));
        }
        saveDatastore(urlStatus);
    }

    private Map<String, String> scanUrlStatus(String subset) {
        return websiteConfiguration.getListWebsites()
                .stream()
                .filter(ws -> ws.contains(subset))
                .collect(Collectors.toMap(ws -> ws, communication::makeRestCall));
    }

    private void saveDatastore(Map<String, String> statusCodeMap) {
        statusCodeMap.entrySet()
                .stream()
                .forEach((e) ->
                {
                    Map.Entry<LocalDateTime, String> entry = Map.entry(LocalDateTime.now(), e.getValue());
                    datastore.getDataMap().putIfAbsent(e.getKey(), new HashMap<>());
                    datastore.getDataMap().get(e.getKey()).put(LocalDateTime.now(), e.getValue());
                });
    }
}
