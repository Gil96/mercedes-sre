package com.mercedes.sre.command;

public interface Command {

    void execute(String optional, String subset);
}
