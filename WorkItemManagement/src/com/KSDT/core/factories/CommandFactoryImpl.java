package com.KSDT.core.factories;

import com.KSDT.commands.addition.AddCommentCommand;
import com.KSDT.commands.addition.AddPersonCommand;
import com.KSDT.commands.change.*;
import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.*;
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
            case CREATEPERSON:
                return new CreatePersonCommand(repository, factory);
            case ADDPERSON:
                return new AddPersonCommand(repository, factory);
            case CREATEBOARDINTEAM:
                return new CreateBoardInTeamCommand(repository, factory);
            case CREATEBUGINBOARD:
                return new CreateBugInBoard(repository, factory);
            case CREATESTORYINBOARD:
                return new CreateStoryInBoard(repository,factory);
            case CREATEFEEDBACKINBOARD:
                return new CreateFeedbackInBoard(repository,factory);
            case CHANGESTATUS:
                return new ChangeStatusCommand(repository, factory);
            case ADDCOMMENT:
                return new AddCommentCommand(repository, factory);
            case CHANGEBUGPRIORITY:
                return new ChangeBugPriorityCommand(repository, factory);
            case CHANGEBUGSEVERITY:
                return new ChangeBugSeverityCommand(repository, factory);
            case CHANGESTORYSIZE:
                return new ChangeStorySizeCommand(repository, factory);
            case CHANGEFEEDBACKRATING:
                return new ChangeFeedRatingCommand(repository, factory);
            case CHANGEPRIORITY:
                return new ChangePriorityCommand(repository, factory);
        }
        throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));
    }
}
