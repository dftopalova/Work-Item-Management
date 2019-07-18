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
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String teamName;
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

        Board board = repository.getTeams().get(teamName).getBoard(boardName);
        Person person = board.getTeamOwner().getMembersList().get(personName);

        WorkItem workItem = board.getWorkItem(workItemName);
        workItem.addComment(person, comment);

        return String.format(COMMENT_SUCCESSFULLY_ADDED, comment, workItemName);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() < EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(INVALID_TEAM, teamName));
        }

        if (!repository.getBoardsList().stream().anyMatch(item -> item.getName().equals(boardName))) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardName));
        }

        if (!repository.getTeams().get(teamName).getBoardsList().containsKey(boardName)) {
            throw new IllegalArgumentException(String.format(BOARD_NOT_IN_TEAM, boardName, teamName));
        }

//TODO fix tests

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


//
//        if (!repository.getPersons().containsKey(personName)) {
//            throw new IllegalArgumentException(String.format(INVALID_PERSON, personName));
//        }
//
//        Team tempTeam = repository.getBoardsList().stream().
//                filter(board1 -> board1.getName().equals(boardName))
//                .findFirst().get()
//                .getTeamOwner();
//
//        if (!tempTeam.getMembersList().containsKey(personName)) {
//            throw new IllegalArgumentException(String.format(PERSON_NOT_IN_TEAM, personName, tempTeam.getName(), boardName));
//        }
//
//        if (!tempTeam.getBoardsList().containsKey(boardName)) {
//            throw new IllegalArgumentException(String.format(BOARD_NOT_IN_TEAM, boardName, tempTeam.getName()));
//        }
//
//        if (!repository.getBoardsList().stream().
//                filter(board1 -> board1.getName().equals(boardName))
//                .findFirst().get()
//                .getWorkItemsList().containsKey(workItemName)) {
//            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemName));
//        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            teamName = parameters.get(0);
            boardName = parameters.get(1);
            workItemName = parameters.get(2);
            personName = parameters.get(3);

            List<String> fullComment = parameters.subList(4, parameters.size());
            comment = fullComment.toString().replaceAll(",", " ");
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


}
