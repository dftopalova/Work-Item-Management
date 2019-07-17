package com.KSDT.core;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.*;
import com.KSDT.core.factories.CommandFactoryImpl;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.core.providers.CommandParserImpl;
import com.KSDT.core.providers.ConsoleReader;
import com.KSDT.core.providers.ConsoleWriter;

import java.util.List;

public class EngineImpl implements Engine {
    private static final String EMPTY_NULL_COMMAND_EXCEPTION = "Command cannot be null or empty.";
    private static final String TERMINATION_COMMAND = "Exit";

    private WorkItemFactory workItemFactory;
    private WorkItemRepository workItemRepository;
    private CommandParser commandParser;
    private CommandFactory commandFactory;
    private Writer writer;
    private Reader reader;

    public EngineImpl() {
        workItemFactory = new WorkItemFactoryImpl();
        workItemRepository = new WorkItemRepositoryImpl();
        commandParser = new CommandParserImpl();
        commandFactory = new CommandFactoryImpl();
        writer = new ConsoleWriter();
        reader = new ConsoleReader();
    }

    @Override
    public void start() {
        while (true) {
            try {
                String commandAsString = reader.readLine();
                if (commandAsString.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(commandAsString);

            } catch (Exception exception) {
                writer.writeLine(exception.getMessage() != null && !exception.getMessage().isEmpty()
                        ? exception.getMessage() : exception.toString());
            }
        }
    }

    private void processCommand( String commandAsString) {
        if (commandAsString == null || commandAsString.trim().equals("")) {
            throw new IllegalArgumentException(EMPTY_NULL_COMMAND_EXCEPTION);
        }

        String commandName = commandParser.parseCommand(commandAsString);
        Command command = commandFactory.createCommand(commandName, workItemFactory, workItemRepository);
        List<String> parameters = commandParser.parseParameters(commandAsString);
        String executionResults = command.execute(parameters);
        writer.writeLine(executionResults);
    }
}
