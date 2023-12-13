package com.mercedes.sre.command;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mercedes.sre.storage.Datastore;
@Service
public class Backup implements Command{

    @Autowired
    Datastore datastore;

    @Value("${path.backup}")
    private String pathBackup;

    @Override
    public void execute(String optional, String subset) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            File file = ResourceUtils.getFile(pathBackup);
            writer.writeValue(file, datastore);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Backup finished");
    }
}
