package com.mercedes.sre.storage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Data
public class Datastore {

    // <URL < Time, State>>
    Map<String, Map.Entry<LocalDateTime, String>> dataMap = new HashMap<>();
}
