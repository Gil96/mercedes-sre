package com.mercedes.sre.misc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
@Data
public class WebsiteConfiguration {

  List<String> listWebsites;

  @Value("${path.website}")
  private String websitePath;

  @Bean
  public void fetchWebSites() {

    try {
      File file = ResourceUtils.getFile(websitePath);
      List<String> lines = Files.readAllLines(file.toPath());
      setListWebsites(lines);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
