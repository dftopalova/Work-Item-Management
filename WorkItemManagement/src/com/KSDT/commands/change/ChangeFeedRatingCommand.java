package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Feedback;
import com.KSDT.models.contracts.Person;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeFeedRatingCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String personName;
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
        Person person = repository.getBoards().get(boardId).getTeamOwner().getMembersList().get(personName);
        int oldRating = feedback.getRating();
        validateRating(oldRating, newRating);

        feedback.changeRating(person, newRating);
        return String.format(RATING_SUCCESSFULLY_CHANGED, workItemId, oldRating, newRating);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getBoards().get(boardId).getWorkItemsList().containsKey(workItemId)) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemId));
        }
        if (!repository.getBoards().get(boardId).getTeamOwner().getMembersList().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(PERSON_NOT_IN_TEAM, personName, repository.getBoards().get(boardId).getTeamOwner().getName(), boardId));
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
            personName = parameters.get(3);

            validateParameters();


        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }

}
