package com.mercedes.sre.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.misc.WebsiteConfiguration;
import com.mercedes.sre.storage.Datastore;

public class CommandUtils {

    public static Map<String, String> scanUrlStatus(String subset, WebsiteConfiguration websiteConfiguration, Communication communication) {
        return websiteConfiguration.getListWebsites()
                .stream()
                .filter(ws -> ws.contains(subset))
                .collect(Collectors.toMap(ws -> ws, communication::makeRestCall));
    }

    public static void saveDatastore(Map<String, String> statusCodeMap, Datastore datastore) {
        statusCodeMap.entrySet()
                .stream()
                .forEach((e) ->
                {
                    Map.Entry<LocalDateTime, String> entry = Map.entry(LocalDateTime.now(), e.getValue());
                    datastore.getDataMap().putIfAbsent(e.getKey(), new HashMap<>());
                    datastore.getDataMap().get(e.getKey()).put(LocalDateTime.now(), e.getValue());
                });
    }

    public static <T> void printCompleteMsg(Class<T> commandClass) {
        System.out.println("Command: " + commandClass.getSimpleName() + " completed.");
    }

    public static <T> void printFailedMsg(Class<T> commandClass) {
        System.out.println("Command: " + commandClass.getSimpleName() + " failed.");
    }
}
