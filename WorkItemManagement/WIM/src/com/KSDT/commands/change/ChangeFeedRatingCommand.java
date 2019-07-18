package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Feedback;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeFeedRatingCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String teamName;
    private String personName;
    private String boardName;
    private String workItemName;
    private int newRating;


    public ChangeFeedRatingCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();


        Board board = repository.getTeams().get(teamName).getBoard(boardName);
        Person person = board.getTeamOwner().getMembersList().get(personName);

        WorkItem tempItem = board.getWorkItem(workItemName);
        Integer key = repository.getWorkItemID(repository.getAllItems(), tempItem);
        Feedback feedback =  repository.getFeedbackMap().get(key);

        int oldRating = feedback.getRating();
        ValidationHelper.equalityCheck(oldRating, newRating, String.format(WORK_ITEM_RATING_SAME, workItemName));

        feedback.changeRating(person, newRating);
        board.addToHistory(HistoryHelper.collectChange(oldRating, newRating, "rating"));

        return String.format(RATING_SUCCESSFULLY_CHANGED, workItemName, oldRating, newRating);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(INVALID_TEAM, teamName));
        }
        if (!repository.getTeams().get(teamName).getBoardsList().containsKey(boardName)) {
            throw new IllegalArgumentException(String.format(BOARD_NOT_IN_TEAM, boardName, teamName));
        }
        if (!repository.getBoardsList().stream().anyMatch(item -> item.getName().equals(boardName))) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardName));
        }


        if (!repository.getTeams().entrySet().stream()
                .filter(item -> item.getValue().getName().equals(teamName))
                .findFirst().get().getValue()
                .getBoardsList().entrySet()
                .stream()
                .filter(item -> item.getValue().getName().equals(boardName))
                .findFirst().get().getValue()
                .getWorkItemsList().containsKey(workItemName)) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemName));
        }

        if (!repository.getTeams().entrySet().stream().filter(item -> item.getValue().getName().equals(teamName))
                .findFirst().get().getValue()
                .getBoardsList().entrySet()
                .stream()
                .filter(item -> item.getValue().getName().equals(boardName))
                .findFirst().get().getValue()
                .getTeamOwner().getMembersList().containsKey(personName)) {
            throw new IllegalArgumentException(
                    String.format(PERSON_NOT_IN_TEAM,
                            personName,
                            repository.getBoardsList()
                                    .stream()
                                    .filter(item -> item.getName().equals(boardName))
                                    .findFirst().get()
                                    .getTeamOwner().getName(),
                            boardName));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            teamName = parameters.get(0);
            boardName = parameters.get(1);
            workItemName = parameters.get(2);
            newRating = Integer.valueOf(parameters.get(3).toUpperCase());
            personName = parameters.get(4);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
