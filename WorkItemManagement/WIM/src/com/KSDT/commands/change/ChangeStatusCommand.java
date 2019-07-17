package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ChangeStatusCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private String workItemType;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String personName;
    private int boardId; //TODO string?!?
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
        validateParameters();


        Board board = repository.getBoardsList().get(boardId);
        WorkItem workItem = board.getWorkItem(workItemId);
        Person person = board.getTeamOwner().getMembersList().get(personName);
        StatusType oldStatus = workItem.getStatus();
        ValidationHelper.equalityCheck(oldStatus, newStatus, String.format(WORK_ITEM_STATUS_SAME, workItemId));


        workItem.changeStatus(person, newStatus);
        board.addToHistory(HistoryHelper.collectChange(oldStatus, newStatus));
        return String.format(STATUS_SUCCESSFULLY_CHANGED, workItemId, oldStatus, newStatus.toString());
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (repository.getBoardsList().size() < boardId) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, String.valueOf(boardId)));
        }
        if (!repository.getBoardsList().get(boardId).getWorkItemsList().containsKey(workItemId)) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemId));
        }
        if (!repository.getBoardsList().get(boardId).getTeamOwner().getMembersList().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(PERSON_NOT_IN_TEAM, personName, repository.getBoardsList().get(boardId).getTeamOwner().getName(), boardId));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardId = Integer.valueOf(parameters.get(0));
            workItemId = String.valueOf(parameters.get(1));

            String workItemClassSimpleName = repository.getBoardsList().get(boardId).getWorkItem(workItemId).getClass().getSimpleName().toUpperCase();
            workItemType = workItemClassSimpleName.replace("IMPL", "").concat("_");
            newStatus = StatusType.valueOf(workItemType + (parameters.get(2).toUpperCase()));
            personName = parameters.get(3);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }
}
