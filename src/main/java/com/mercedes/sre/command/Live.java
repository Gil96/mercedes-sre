package com.mercedes.sre.command;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.misc.WebsiteConfiguration;

@Component
public class Live implements Command {

    @Override
    public void execute(String optional, String subset) {

    }

}
