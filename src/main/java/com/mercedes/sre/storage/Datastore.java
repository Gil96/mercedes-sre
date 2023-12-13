package com.mercedes.sre.storage;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
public class Datastore implements Serializable {

    // <URL < Time, State>>
    Map<String, Map<LocalDateTime, String>> dataMap = new HashMap<>();
}
