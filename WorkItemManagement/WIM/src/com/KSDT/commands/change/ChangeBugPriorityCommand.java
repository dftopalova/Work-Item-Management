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

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeBugPriorityCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String personName;
    private String boardName;
    private String workItemName;
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

//TODO в общата команда ще валидираме дали итем е част от борда, а след това ИТЕМ-А се взема от getPriorityabale() И НЕ СЕ КАСТВА ЙЕЙЙЙЙ <3


        Board board = repository.getBoardsList().stream().filter(board1 -> board1.getName().equals(boardName)).findFirst().get();
        Bug bug = (Bug) board.getWorkItem(workItemName);
//        Priorityable big = repository.getPrioritizableItems().entrySet().stream().

        Person person = board.getTeamOwner().getMembersList().get(personName);
        PriorityType oldPriority = bug.getPriority();
        ValidationHelper.equalityCheck(oldPriority, newPriority, String.format(WORK_ITEM_PRIORITY_SAME, workItemName));

        bug.changePriority(person, newPriority);
        board.addToHistory(HistoryHelper.collectChange(oldPriority, newPriority));
        return String.format(PRIORITY_SUCCESSFULLY_CHANGED, workItemName, oldPriority, newPriority);
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
//        if (!repository.getBoardsList().stream().anyMatch(item -> item.getName().equals(boardName))) {
//            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardName));
//        }
//
//        if (!repository.getBoardsList().get(boardName).getWorkItemsList().containsKey(workItemName)) {
//            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemName));
//        }
//        if (!repository.getBoardsList().get(boardName).getTeamOwner().getMembersList().containsKey(personName)) {
//            throw new IllegalArgumentException(String.format(PERSON_NOT_IN_TEAM, personName, repository.getBoardsList().get(boardName).getTeamOwner().getName(), boardName));
//        }
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
