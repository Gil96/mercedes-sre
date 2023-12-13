package com.mercedes.sre.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mercedes.sre.storage.Datastore;
import com.mercedes.sre.utils.CommandUtils;

@Component
public class History implements Command {

    @Autowired
    Datastore datastore;

    @Override
    public void execute(String optional, String subset) {
        datastore.getDataMap().forEach((key, value) -> System.out.println(">>>" + key + " # " + value.entrySet()));
        CommandUtils.printCompleteMsg(getClass());
    }
}
