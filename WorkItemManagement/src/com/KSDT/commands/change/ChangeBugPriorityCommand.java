package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.StatusType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeBugPriorityCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

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

        Bug bug = (Bug) repository.getBoards().get(boardId).getWorkItem(workItemId);
        PriorityType oldPriority = bug.getPriority();
        validatePriority(oldPriority, newPriority);

        bug.changePriority(newPriority);
        return String.format(PRIORITY_SUCCESSFULLY_CHANGED, workItemId, oldPriority, newPriority);
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

    private void validatePriority(PriorityType oldPriority, PriorityType newPriority) {
        if (oldPriority.equals(newPriority)) {
            throw new IllegalArgumentException(String.format(WORK_ITEM_PRIORITY_SAME, workItemId));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardId = Integer.valueOf(parameters.get(0));
            workItemId = String.valueOf(parameters.get(1));
            newPriority = PriorityType.valueOf(parameters.get(2).toUpperCase());
            validateParameters();


        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }


}
