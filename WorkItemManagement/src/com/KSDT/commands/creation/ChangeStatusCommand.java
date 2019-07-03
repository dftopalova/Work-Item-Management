package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeStatusCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private String WORK_ITEM_TYPE;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private int workItemId;
    private StatusType newStatus;

    public ChangeStatusCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateParameters();
        validateInput(parameters);
        parseParameters(parameters);

        WorkItem workItem = repository.getWorkItems().get(workItemId);
        String oldStatus = workItem.getStatus().toString();
        workItem.changeStatus(newStatus);
        return String.format(STATUS_SUCCESSFULLY_CHANGED, workItemId, oldStatus, newStatus.toString());
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (repository.getWorkItems().size() <= workItemId) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemId));
        }
        if (repository.getWorkItems().get(workItemId).getStatus().equals(newStatus)) {
            throw new IllegalArgumentException(String.format(WORK_ITEM_STATUS_SAME, workItemId));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            String workItemClassSimpleName =  repository.getWorkItems().get(workItemId).getClass().getSimpleName().toUpperCase();
            WORK_ITEM_TYPE = workItemClassSimpleName.substring(0, workItemClassSimpleName.length()-4).concat("_");
            workItemId = Integer.valueOf(parameters.get(0));
            newStatus = StatusType.valueOf(WORK_ITEM_TYPE + (parameters.get(1).toUpperCase()));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }

}
