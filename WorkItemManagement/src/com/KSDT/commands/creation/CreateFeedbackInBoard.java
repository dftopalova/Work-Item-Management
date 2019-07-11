package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class CreateFeedbackInBoard implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 6;
    private static final String WORK_ITEM_TYPE = "FEEDBACK_";
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String teamName;
    private String boardToAddName;
    private String feedbackToBeAdded;
    private StatusType status;
    private String description;
    private int rating;

    public CreateFeedbackInBoard(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();

        Board board = repository.getTeams().get(teamName).getBoardsList().get(boardToAddName);
        WorkItem feedback = factory.createFeedback(feedbackToBeAdded, status, description, rating);
        repository.addWorkItem(feedback);
        board.addWorkItem(feedbackToBeAdded, feedback);
        return String.format(FEEDBACK_ADDED_TO_BOARD, repository.getWorkItems().size() - 1, boardToAddName);
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() < EXPECTED_NUMBER_OF_ARGUMENTS) {
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
            feedbackToBeAdded = parameters.get(2);
            status = StatusType.valueOf(WORK_ITEM_TYPE + (parameters.get(3).toUpperCase()));
            rating = Integer.parseInt(parameters.get(4));
            description = String.join(" ", parameters.subList(5, (parameters.size())));

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }
}
