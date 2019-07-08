package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.WorkItem;
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
    private String description;
    private String stepsToReproduce;
    private PriorityType priority;
    private SeverityType severity;

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
        WorkItem bug = factory.createBug(bugNameToBeAdded, status, description, stepsToReproduce, priority, severity);
        repository.addWorkItem(bug);
        board.addWorkItem(bugNameToBeAdded, bug);

//      TODO - logic for finding exact name match for workitem
//        WorkItem findTest = repository.getWorkItemsList().stream()
//                .filter(e -> e.getTitle().equals(bugNameToBeAdded))
//                .findFirst()
//                .get();


        return String.format(BUG_ADDED_TO_BOARD, repository.getWorkItems().size() - 1, boardToAddName);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
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
            description = parameters.get(4);
            stepsToReproduce = parameters.get(5);
            priority = PriorityType.valueOf(parameters.get(6).toUpperCase());
            severity = SeverityType.valueOf(parameters.get(7).toUpperCase());

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }

}
