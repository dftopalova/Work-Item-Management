package com.KSDT.core.factories;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.CreateTeamCommand;
import com.KSDT.commands.enums.CommandType;
import com.KSDT.core.contracts.CommandFactory;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;

public class CommandFactoryImpl implements CommandFactory {
    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    @Override
    public Command createCommand(String commandName, WorkItemFactory factory, WorkItemRepository repository) {
        CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
        switch (commandType) {
            case CREATETEAM:
                return new CreateTeamCommand(repository, factory);
        }
        throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));
    }
}
