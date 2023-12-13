package com.mercedes.sre.command;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.misc.WebsiteConfiguration;
import com.mercedes.sre.storage.Datastore;
import com.mercedes.sre.utils.CommandUtils;

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

        scheduler.schedule(() -> {
            scheduler.shutdown();
            CommandUtils.printCompleteMsg(getClass());
        }, timeDuration, TimeUnit.SECONDS);
    }

    private void liveExecution(String optional, String subset) {

        Map<String, String> urlStatus = CommandUtils.scanUrlStatus(subset, websiteConfiguration, communication);
        if (optional.equals("y")) {
            urlStatus.entrySet().stream().forEach(entry -> System.out.println(">>>>" + entry.getKey() + " # " + entry.getValue() + " # " + LocalDateTime.now()));
        }
        CommandUtils.saveDatastore(urlStatus, datastore);
    }
}
