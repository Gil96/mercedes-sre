package com.mercedes.sre.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.mercedes.sre.api.Communication;
import com.mercedes.sre.misc.WebsiteConfiguration;
import lombok.extern.slf4j.Slf4j;


public interface Command {

    public void execute( WebsiteConfiguration websiteConfiguration);

}
