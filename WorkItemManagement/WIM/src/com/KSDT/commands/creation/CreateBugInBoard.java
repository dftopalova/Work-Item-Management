package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class CreateBugInBoard implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 8;
    private static final String WORK_ITEM_TYPE = "BUG_";

    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String teamName;
    private String bugNameToBeAdded;
    private String boardToAddName;
    private StatusType status;
    private String stepsToReproduce;
    private PriorityType priority;
    private SeverityType severity;
    private String description;

    public CreateBugInBoard(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();


        Board board = repository.getTeams().get(teamName).getBoardsList().get(boardToAddName);
        Bug bug = factory.createBug(bugNameToBeAdded, status, description, stepsToReproduce, priority, severity);
        repository.addBug(bug);
//        repository.addWorkItem(bug);
        board.addWorkItem(bugNameToBeAdded, bug);

        return String.format(BUG_ADDED_TO_BOARD, repository.getWorkItemID(repository.getBugMap(), bug), boardToAddName);
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() < EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {

        if(!repository.getTeams().containsKey(teamName)){
            throw new IllegalArgumentException(String.format(INVALID_TEAM,teamName));
        }

        if (!repository.getTeams().get(teamName).getBoardsList().containsKey(boardToAddName)) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardToAddName));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            teamName = parameters.get(0);
            boardToAddName = parameters.get(1);
            bugNameToBeAdded = parameters.get(2);
            status = StatusType.valueOf(WORK_ITEM_TYPE + (parameters.get(3).toUpperCase()));
            stepsToReproduce = parameters.get(4);
            priority = PriorityType.valueOf(parameters.get(5).toUpperCase());
            severity = SeverityType.valueOf(parameters.get(6).toUpperCase());
            description = String.join(" ", parameters.subList(7, (parameters.size())));

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
