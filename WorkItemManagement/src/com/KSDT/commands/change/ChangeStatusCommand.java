package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeStatusCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private String WORK_ITEM_TYPE;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String personName;
    private int boardId;
    private String workItemId;
    private StatusType newStatus;

    public ChangeStatusCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateStatus();

        WorkItem workItem = repository.getBoards().get(boardId).getWorkItem(workItemId);
        Person person = repository.getBoards().get(boardId).getTeamOwner().getMembersList().get(personName);
        String oldStatus = workItem.getStatus().toString();
        workItem.changeStatus(person, newStatus);
        return String.format(STATUS_SUCCESSFULLY_CHANGED, workItemId, oldStatus, newStatus.toString());
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

    private void validateStatus() {
        if (repository.getBoards().get(boardId).getWorkItemsList().get(workItemId).getStatus().equals(newStatus)) {
            throw new IllegalArgumentException(String.format(WORK_ITEM_STATUS_SAME, workItemId));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardId = Integer.valueOf(parameters.get(0));
            workItemId = String.valueOf(parameters.get(1));
            validateParameters();


            String workItemClassSimpleName = repository.getBoards().get(boardId).getWorkItem(workItemId).getClass().getSimpleName().toUpperCase();
//            String workItemClassSimpleName =  repository.getWorkItemsList().get(workItemId).getClass().getSimpleName().toUpperCase();
            WORK_ITEM_TYPE = workItemClassSimpleName.substring(0, workItemClassSimpleName.length() - 4).concat("_");
            newStatus = StatusType.valueOf(WORK_ITEM_TYPE + (parameters.get(2).toUpperCase()));
            personName = parameters.get(3);


        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }

}
