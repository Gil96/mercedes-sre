package com.mercedes.sre.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import com.mercedes.sre.command.Command;
import com.mercedes.sre.command.Fetch;
import com.mercedes.sre.command.Live;

@Component
@EnableAsync
public class Menu {

    Map<String, Command> commands;


    private List<String> validateAndTokenize(String line) {

        try {
            String[] tokens = line.split(" ");

            Integer commandToken = Integer.parseInt(tokens[0]);
            String optionToken = tokens[1];
            String subsetToken = null;
            List<String> tokensList = new ArrayList<>(List.of(tokens[0], optionToken));

            Predicate<Integer> commandTokenPredicate = x -> x > 0 && x <= commands.size();
            Predicate<String> optionTokenPredicate = x -> x.matches("y|n");
            Predicate<String> subsetTokenPredicate = x -> x.matches("\\b\\w+\\b");

            if (tokens.length == 3) {
                subsetToken = tokens[2];
                if (subsetTokenPredicate.test(subsetToken)) {
                    tokensList.add(subsetToken);
                }
            }
            if (commandTokenPredicate.test(commandToken)
                    && optionTokenPredicate.test(optionToken)){
                return tokensList;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Autowired
    public Menu(Fetch fetch, Live live) {
        commands = Map.of("1", fetch, "2", live);
    }

    @Bean
    @Async
    @DependsOn({"communication","websiteConfiguration"})
    public void mainMenu() {

        printWelcomeMessages();

        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String line = sc.nextLine();

           List<String> tokens = validateAndTokenize(line);
           if (tokens != null) {
                commands.get(tokens.get(0)).execute(tokens.get(1), tokens.size()==3 ? tokens.get(2) : "");
           }
           else {
               printTryAgainMessage();
           }
        }
        sc.close();
    }

    private void printWelcomeMessages() {
        System.out.println("\n************************************");
        System.out.println("\n WELCOME - MERCEDES SRE");
        System.out.println("\n*1 Argument: \n (1) Fetch \n (2) Live \n (3) History \n (4) Backup \n - Required");
        System.out.println("\n*2 Argument: \n (y) Yes, Enable Optional Switch \n (n) No, Disable Optional Switch \n - Required");
        System.out.println("\n*3 Argument: \n (abcdef) Type a word to only process the urls that match \n - Not Required");
        System.out.println("\n*Examples*: \n > fetch y google \n > live n\"");
        System.out.println("\n************************************\n");
    }

    private void printTryAgainMessage() {
        System.out.println("No choice available, try again...");
    }
}
