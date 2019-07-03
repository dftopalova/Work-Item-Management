package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.WorkItem;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.KSDT.commands.CommandConstants.*;

public class AddCommentCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private int boardId;
    private String workItemId;
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

        Board board = repository.getBoards().get(boardId);
        WorkItem workItem = board.getWorkItem(workItemId);
        workItem.addComment(comment);

        return String.format(COMMENT_SUCCESSFULLY_ADDED, comment, workItemId);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (repository.getBoards().size() <= boardId) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardId));
        }
        if (!repository.getBoards().get(boardId).getWorkItems().containsKey(workItemId)) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemId));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardId = Integer.valueOf(parameters.get(0));
            workItemId = parameters.get(1);
//            List<String> fullComment = parameters.stream().collect(Collectors.joining( ));
//            TODO FIX collect stream
            comment = parameters.get(2);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }


}
