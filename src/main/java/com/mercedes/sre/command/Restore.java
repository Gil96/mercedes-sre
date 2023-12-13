package com.mercedes.sre.command;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.xml.crypto.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mercedes.sre.misc.LocalDateTimeDeserializer;
import com.mercedes.sre.storage.Datastore;

@Service
public class Restore implements Command{

    @Autowired
    Datastore datastore;

    @Value("${path.restore}")
    private String pathRestore;

    @Override
    public void execute(String optional, String subset) {

        SimpleModule simpleModule = new SimpleModule();
        ObjectMapper objectMapper = new ObjectMapper();
        simpleModule.addKeyDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(simpleModule);

        //TypeReference<Map<String, Map<LocalDateTime, String>>> typeRef = new TypeReference<Map<String, Map<LocalDateTime, String>>>() {};

        try {
            Map<String, Map<LocalDateTime, String>> stringMapMap = objectMapper.readValue(new File(pathRestore), Datastore.class).getDataMap();
            datastore.setDataMap(stringMapMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Invalid  Restore");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Restore finished");
    }
}
