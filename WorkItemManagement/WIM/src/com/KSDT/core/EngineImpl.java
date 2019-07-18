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
    private static final String HELP_MENU_MESSAGE = "SHOW_OPTIONS";
    private static final String WELCOME_MESSAGE = "    ~~~WELCOME TO WORK ITEM MANAGEMENT CONSOLE APPLICATION~~~";
    private static final String CHECK_ALL_OPTIONS_MESSAGE = "   IF YOU NEED TO CHECK AGAIN ALL OPTIONS PLEASE ENTER \"SHOW_OPTIONS\"";

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

        writer.writeLine(WELCOME_MESSAGE + System.lineSeparator());
        writer.writeLine(showOptions());

        while (true) {
            try {

                String commandAsString = reader.readLine();
                if (commandAsString.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                if(commandAsString.equalsIgnoreCase(HELP_MENU_MESSAGE)){
                    writer.writeLine(showOptions());
                    continue;
                }

                processCommand(commandAsString);

                writer.writeLine(CHECK_ALL_OPTIONS_MESSAGE + System.lineSeparator());

            } catch (Exception exception) {
                writer.writeLine(exception.getMessage() != null && !exception.getMessage().isEmpty()
                        ? exception.getMessage() : exception.toString());
            }
        }
    }

    private String showOptions() {
        StringBuilder options = new StringBuilder();
        options.append(System.lineSeparator() +
                "        LIST OF ALL OPTIONS, WHICH ARE SUPPORTED:"
                + System.lineSeparator() +
                "CreateTeam <team_name>"
                + System.lineSeparator() +
                "CreatePerson <person_name>"
                + System.lineSeparator() +
                "CreateBoardInTeam <team_name> <board_name>"
                + System.lineSeparator() +
                "CreateBugInBoard <team_name> <board_name> <bug_name> <status (Active/Fixed)> <steps to reproduce> <priority (High/Medium/Low)> <severity (Critical/Major/Minor)> <description>"
                + System.lineSeparator() +
                "CreateStoryInBoard <team_name> <board_name> <story_name> <status (NotDone/InProgress/Done)> <priority (High/Medium/Low)> <size (Large/Medium/Small)> <description>"
                + System.lineSeparator() +
                "CreateFeedbackInBoard <team_name> <board_name> <feedback_name> <status (New/Unscheduled/Scheduled/Done)> <rating> <description>"
                + System.lineSeparator() + "========================================================" + System.lineSeparator() +
                "AddPerson <person_name> <team_name>"
                + System.lineSeparator() +
                "AddComment <team_name> <board_name> <item_name> <person, who wrote the comment> <comment>"
                + System.lineSeparator() +
                "AssignWorkItem <item_name> <person to assign>"
                + System.lineSeparator() +
                "UnassignWorkItem <item_name> <assignee name>"
                + System.lineSeparator() + "========================================================" + System.lineSeparator() +
                "ChangeStatus <team_name> <board_name> <item_name> <new status> <person, who made the change>"
                + System.lineSeparator() +
                "ChangePriority <item_type> <team_name> <board_name> <item_name> <new priority> <person, who made the change>"
                + System.lineSeparator() +
                "ChangeBugSeverity <team_name> <board_name> <bug_name> <new severity> <person, who made the change>"
                + System.lineSeparator() +
                "ChangeStorySize <team_name> <board_name> <story_name> <new size> <person, who made the change>"
                + System.lineSeparator() +
                "ChangeFeedbackRating <team_name> <board_name> <feedback_name> <new rating> <person, who made the change>"
                + System.lineSeparator() + "========================================================" + System.lineSeparator() +
                "ListAllWorkItems"
                + System.lineSeparator() +
                "ListFilter [-type] <items_type (BUG/STORY/FEEDBACK)> [-status] <status_type> [-assignee] <assignee> [-sort] <sort_criteria (title/priority/severity/size/rating)>"
                + System.lineSeparator() +
                "ShowAllPeople / ShowAllTeams"
                + System.lineSeparator() +
                "ShowAllTeamBoards <team_name> / ShowAllTeamMembers <team_name>"
                + System.lineSeparator() +
                "ShowPersonActivity <person_name> / ShowTeamActivity <team_name> / ShowBoardActivity <board_name>"
                + System.lineSeparator());

        return options.toString();
    }

    private void processCommand(String commandAsString) {
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
