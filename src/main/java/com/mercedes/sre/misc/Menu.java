package com.mercedes.sre.misc;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.command.Command;
import com.mercedes.sre.command.Fetch;
import com.mercedes.sre.command.Live;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableAsync
public class Menu {

    Command command;

    @Autowired
    Communication communication;

    @Autowired
    WebsiteConfiguration websiteConfiguration;


    private boolean isValidMenuOption(String token) {
        try {
            Integer tokenInt = Integer.parseInt(token);
            return tokenInt > 0 && tokenInt <= 4;
        }catch(Exception e){
            return false;
        }
    }

    @Bean
    @Async
    @DependsOn({"communication","websiteConfiguration"})
    public void displayMenu() {

        System.out.println("\n (1) Fetch \n (2) Live \n (3) History \n (4) Backup");

        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String option = sc.next();
            if (isValidMenuOption(option)) {
                switch(option) {
                    case "1":
                        command = new Fetch();
                    //case "2":
                    //    command = new Live();
                }
                command.execute(communication, websiteConfiguration);
            }
            else {
                System.out.println("No choice available, try again...");
            }
        }
        sc.close();
    }

}
