package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Feedback;
import com.KSDT.models.contracts.Story;
import com.KSDT.models.enums.SizeType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeFeedRatingCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private int boardId;
    private String workItemId;
    private int newRating;


    public ChangeFeedRatingCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);

        Feedback feedback = (Feedback) repository.getBoards().get(boardId).getWorkItem(workItemId);
        int oldRating = feedback.getRating();
        validateRating(oldRating, newRating);

        feedback.changeRating(newRating);
        return String.format(RATING_SUCCESSFULLY_CHANGED, workItemId, oldRating, newRating);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getBoards().get(boardId).getWorkItems().containsKey(workItemId)) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemId));
        }
    }

    private void validateRating(int oldRating, int newRating) {
        if (oldRating == newRating) {
            throw new IllegalArgumentException(String.format(WORK_ITEM_RATING_SAME, workItemId));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardId = Integer.valueOf(parameters.get(0));
            workItemId = String.valueOf(parameters.get(1));
            newRating = Integer.valueOf(parameters.get(2).toUpperCase());
            validateParameters();


        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }

}
