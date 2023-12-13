package com.mercedes.sre.misc;

import java.io.IOException;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
public class LocalDateTimeDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
        return LocalDateTime.parse(s);
    }
}
