package com.KSDT.core.contracts;

import com.KSDT.commands.contracts.Command;

public interface CommandFactory {
    Command createCommand(String commandTypeAsString, FurnitureFactory factory, FurnitureRepository agencyRepository);
}
