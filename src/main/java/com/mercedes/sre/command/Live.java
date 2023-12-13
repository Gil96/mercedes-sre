package com.mercedes.sre.command;

import java.time.LocalDateTime;
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

    @Override
    public void execute(String optional, String subset) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            liveExecution(optional, subset);
        }, 0, poolingInterval, TimeUnit.SECONDS);

        // Schedule a stop after 1 minute
        scheduler.schedule(() -> {
            scheduler.shutdown();
            System.out.println("Task scheduler stopped after 1 minute.");
        }, 20, TimeUnit.SECONDS);
    }

    private void liveExecution(String optional, String subset) {

        saveDatastore(scanUrlStatus(subset));
        if (optional.equals("y")) {
            datastore.getDataMap().forEach((key, value) -> System.out.println(">>>>" + key + " # " + value.getKey() + " # " + value.getValue()));
        }
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
                .forEach((e) -> datastore.getDataMap().put(e.getKey(), Map.entry(LocalDateTime.now(), e.getValue())));
    }
}
