package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Priorityable;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public  class ChangePriorityCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String personName;
    private String boardName;
    private String workItemName;
    private PriorityType newPriority;

    public ChangePriorityCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        switch (parameters.get(0).toUpperCase()) {
            case "FEEDBACK":
                return FEEDBACK_DOESNT_CONTAIN_PRIORITY;
            case "BUG":
                parameters.remove(0);
                return changePriority(parameters);
            case "STORY":
                parameters.remove(0);
                return changePriority(parameters);
        }

        return String.format(WRONG_WORK_ITEM_TYPE, parameters.get(0));
    }

    private String changePriority(List<String> params) {
        validateInput(params);
        parseParameters(params);
        validateParameters();

        Board board = repository.getBoards().stream()
                .filter(board1 -> board1.getName().equals(boardName))
                .findFirst().get();
        Person person = board.getTeamOwner().getMembersList().get(personName);

        WorkItem tempItem = repository.getAllItems().get(workItemName);
        Integer key = repository.getWorkItemID(repository.getAllItems(), tempItem);
        Priorityable item = repository.getPrioritizableItems().get(key);

        PriorityType oldPriority = item.getPriority();
        ValidationHelper.equalityCheck(oldPriority, newPriority, String.format(WORK_ITEM_PRIORITY_SAME, workItemName));
        item.changePriority(person, newPriority);
        board.addToHistory(HistoryHelper.collectChange(oldPriority, newPriority));

        return String.format(PRIORITY_SUCCESSFULLY_CHANGED, workItemName, oldPriority, newPriority);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getBoards().stream().anyMatch(item -> item.getName().equals(boardName))) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardName));
        }

        if (!repository.getBoards()
                .stream()
                .filter(item -> item.getName().equals(boardName))
                .findFirst().get()
                .getWorkItemsList().containsKey(workItemName)) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemName));
        }
        if (!repository.getBoards()
                .stream()
                .filter(item -> item.getName().equals(boardName))
                .findFirst().get()
                .getTeamOwner().getMembersList().containsKey(personName)) {
            throw new IllegalArgumentException(
                    String.format(PERSON_NOT_IN_TEAM,
                            personName,
                            repository.getBoards()
                            .stream()
                            .filter(item -> item.getName().equals(boardName))
                            .findFirst().get()
                            .getTeamOwner().getName(),
                            boardName));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardName = parameters.get(0);
            workItemName = parameters.get(1);
            newPriority = PriorityType.valueOf(parameters.get(2).toUpperCase());
            personName = parameters.get(3);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }

}
