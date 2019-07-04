package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.enums.SeverityType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeBugSeverityCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private int boardId;
    private String workItemId;
    private SeverityType newSeverity;

    public ChangeBugSeverityCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);

        Bug bug = (Bug) repository.getBoards().get(boardId).getWorkItem(workItemId);
        SeverityType oldSeverity = bug.getSeverity();
        validateSeverity(oldSeverity, newSeverity);

        bug.changeSeverity(newSeverity);
        return String.format(SEVERITY_SUCCESSFULLY_CHANGED, workItemId, oldSeverity, newSeverity);
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
    }

    private void validateSeverity(SeverityType oldSeverity, SeverityType newSeverity) {
        if (oldSeverity.equals(newSeverity)) {
            throw new IllegalArgumentException(String.format(WORK_ITEM_SEVERITY_SAME, workItemId));
//     TODO MAKE GENERIC METHOD FOR THAT VALIDATION AND IMPLEMENTED IT IN OTHER PLACES!!!
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardId = Integer.valueOf(parameters.get(0));
            workItemId = String.valueOf(parameters.get(1));
            newSeverity = SeverityType.valueOf(parameters.get(2).toUpperCase());
            validateParameters();


        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }


}
