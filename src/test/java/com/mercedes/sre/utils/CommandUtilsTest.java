package com.mercedes.sre.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.misc.WebsiteConfiguration;
import com.mercedes.sre.storage.Datastore;

@ExtendWith(MockitoExtension.class)
class CommandUtilsTest {

    @Mock
    private Communication communication;
    @Mock
    private WebsiteConfiguration websiteConfiguration;
    @Mock
    private Datastore datastore;
    @InjectMocks
    private CommandUtils testee;

    List<String> websiteList;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void scanUrlStatus() {

        when(websiteConfiguration.getListWebsites()).thenReturn(Arrays.asList("url1", "url2", "url3"));
        when(communication.makeRestCall(anyString())).thenReturn("status1");
        Map<String, String> resultMap = testee.scanUrlStatus("", websiteConfiguration, communication);
        assertTrue(resultMap.size() == 3);
    }

    @Test
    void saveDatastore() {
        Map<LocalDateTime, String> result = new HashMap<>();
        result.put(LocalDateTime.now(), "status1");
        Map<String, String> statusCode = new HashMap<>();
        datastore.setDataMap(new HashMap<>());
        Map<String, Map<LocalDateTime, String>> dataMap = new HashMap<>();
        statusCode.put("url1", "status2");
        dataMap.put("url1", new HashMap<>());
        when(datastore.getDataMap().get("url1")).thenReturn(result);
        testee.saveDatastore(statusCode, datastore);
    }

}