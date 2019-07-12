package com.KSDT.core.factories;

import com.KSDT.commands.addition.AddCommentCommand;
import com.KSDT.commands.addition.AddPersonCommand;
import com.KSDT.commands.addition.AssignWorkItemCommand;
import com.KSDT.commands.addition.UnassignWorkItemCommand;
import com.KSDT.commands.change.*;
import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.*;
import com.KSDT.commands.enums.CommandType;
import com.KSDT.commands.listing.*;
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
            case CREATEWORKITEMINBOARD:
                return new CreateWorkItemInBoard(repository,factory);
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
            case SHOWALLPEOPLE:
                return new ShowAllPeopleCommand(repository);
            case SHOWALLTEAMS:
                return new ShowAllTeamsCommand(repository);
            case SHOWALLTEAMMEMBERS:
                return new ShowAllTeamMembersCommand(repository);
            case SHOWALLTEAMBOARDS:
                return new ShowAllTeamBoardsCommand(repository);
            case SHOWPERSONACTIVITY:
                return new ShowPersonActivityCommand(repository);
            case SHOWTEAMACTIVITY:
                return new ShowTeamActivityCommand(repository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivityCommand(repository);
            case ASSIGNWORKITEM:
                return new AssignWorkItemCommand(repository);
            case UNASSIGNWORKITEM:
                return new UnassignWorkItemCommand(repository);
            case LISTALLWORKITEMS:
                return new ListAllWorkItemsCommand(repository);
            case LISTFILTER:
                return new ListFilter(repository);
        }
        throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));
    }
}
