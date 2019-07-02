package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class CreateBugInBoard implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String bugNameToBeAdded;
    private String boardToAddName;
    private StatusType status;
    private String stepsToReproduce;
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

        Board board = repository.getBoards().get(boardToAddName);
        WorkItem bug = factory.createBug(boardToAddName, status, stepsToReproduce, severity);
        board.addWorkItem(bugNameToBeAdded, bug); // FIX TODO In BoardImpl

        return String.format(BUG_ADDED_TO_BOARD, bugNameToBeAdded, boardToAddName);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getBoards().containsKey(boardToAddName)) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardToAddName));
        }
    }


    private void parseParameters(List<String> parameters) {
        try {
            boardToAddName = parameters.get(0);
            bugNameToBeAdded = parameters.get(1);
            status = StatusType.valueOf(parameters.get(2)); //
            stepsToReproduce = parameters.get(3);
            severity = SeverityType.valueOf(parameters.get(4));

        } catch (Exception e) {
            throw new IllegalArgumentException("");
//            TODO EXCEPTION
        }
    }

}
