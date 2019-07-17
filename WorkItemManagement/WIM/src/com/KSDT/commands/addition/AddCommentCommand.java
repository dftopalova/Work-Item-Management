package com.KSDT.commands.addition;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;

import java.util.List;
import java.util.stream.Collectors;

import static com.KSDT.commands.CommandConstants.*;

public class AddCommentCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String boardName;
    private String workItemName;
    private String personName;
    private String comment;


    public AddCommentCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();

        Board board = repository.getBoardsList().stream().filter(board1 -> board1.getName().equals(boardName)).findFirst().get();
        WorkItem workItem = board.getWorkItem(workItemName);
        Person person = repository.getPersons().get(personName);
        workItem.addComment(person, comment);

        return String.format(COMMENT_SUCCESSFULLY_ADDED, comment, workItemName);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() < EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getBoardsList().stream().anyMatch(item -> item.getName().equals(boardName))) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardName));
        }

        if (!repository.getPersons().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(INVALID_PERSON, personName));
        }

        Team tempTeam = repository.getBoardsList().stream().
                filter(board1 -> board1.getName().equals(boardName))
                .findFirst().get()
                .getTeamOwner();

        if (!tempTeam.getMembersList().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(PERSON_NOT_IN_TEAM, personName, tempTeam.getName(), boardName));
        }

        if (!repository.getBoardsList().stream().
                filter(board1 -> board1.getName().equals(boardName))
                .findFirst().get()
                .getWorkItemsList().containsKey(workItemName)) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemName));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardName = parameters.get(0);
            workItemName = parameters.get(1);
            personName = parameters.get(2);

            List<String> fullComment = parameters.stream().collect(Collectors.toList());
            fullComment.remove(0);
            fullComment.remove(0);
            fullComment.remove(0);
//            TODO FIX collect stream
            comment = fullComment.toString().replaceAll(",", " ");
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }


}
