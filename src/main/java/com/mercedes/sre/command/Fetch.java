package com.mercedes.sre.command;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.misc.WebsiteConfiguration;
import com.mercedes.sre.storage.Datastore;
import com.mercedes.sre.utils.CommandUtils;

@Component
public class Fetch implements Command {

    @Autowired
    WebsiteConfiguration websiteConfiguration;

    @Autowired
    Communication communication;

    @Autowired
    Datastore datastore;

    @Override
    public void execute(String optional, String subset) {

        Map<String, String> urlStatus = CommandUtils.scanUrlStatus(subset, websiteConfiguration, communication);
        CommandUtils.saveDatastore(urlStatus, datastore);
        if (optional.equals("y")) {
            datastore.getDataMap().forEach((key, value) -> System.out.println(">>>" + key + " # " + value.entrySet()));
        }
        CommandUtils.printCompleteMsg(getClass());
    }
}
