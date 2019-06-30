package com.KSDT.core.factories;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.CommandFactory;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;

public class CommandFactoryImpl implements CommandFactory {
    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    @Override
    public Command createCommand(String commandTypeAsString, WorkItemFactory factory, WorkItemRepository repository) {
        return null;
    }
}
