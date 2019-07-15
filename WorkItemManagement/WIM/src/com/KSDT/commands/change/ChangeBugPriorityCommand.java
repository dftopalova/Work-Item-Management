package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.enums.PriorityType;

import java.time.Period;
import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeBugPriorityCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String personName;
    private int boardId;
    private String workItemId;
    private PriorityType newPriority;


    public ChangeBugPriorityCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();


        Board board = repository.getBoards().get(boardId);
        Bug bug = (Bug) board.getWorkItem(workItemId);
        Person person = board.getTeamOwner().getMembersList().get(personName);
        PriorityType oldPriority = bug.getPriority();
        ValidationHelper.equalityCheck(oldPriority, newPriority, String.format(WORK_ITEM_PRIORITY_SAME, workItemId));

        bug.changePriority(person, newPriority);
        board.addToHistory(HistoryHelper.collectChange(oldPriority, newPriority));
        return String.format(PRIORITY_SUCCESSFULLY_CHANGED, workItemId, oldPriority, newPriority);
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (repository.getBoards().size() <= boardId) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, String.valueOf(boardId)));
        }
        if (!repository.getBoards().get(boardId).getWorkItemsList().containsKey(workItemId)) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemId));
        }
        if (!repository.getBoards().get(boardId).getTeamOwner().getMembersList().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(PERSON_NOT_IN_TEAM, personName, repository.getBoards().get(boardId).getTeamOwner().getName(), boardId));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardId = Integer.valueOf(parameters.get(0));
            workItemId = parameters.get(1);
            newPriority = PriorityType.valueOf(parameters.get(2).toUpperCase());
            personName = parameters.get(3);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }


}
