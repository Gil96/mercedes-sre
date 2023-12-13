package com.mercedes.sre.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mercedes.sre.utils.CommandUtils;
class FetchTest {

    Map<String, String> urlStatus = new HashMap<>();

    @BeforeEach
    void setUp() {
        urlStatus.put("url", "state");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void execute() {

        when(CommandUtils.scanUrlStatus(any(), any(), any())).thenReturn(urlStatus);
    }
}