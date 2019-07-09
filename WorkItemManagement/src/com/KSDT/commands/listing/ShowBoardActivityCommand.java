package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ShowBoardActivityCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private WorkItemRepository repository;

    private String teamName;
    private String boardName;

    public ShowBoardActivityCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        teamName = parameters.get(0);
        boardName = parameters.get(1);
        validateParameters();

        Board boardToShowActivity = repository.getTeams().get(teamName).getBoard(boardName);

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("Board %s`s activity:" + System.lineSeparator(), boardName));
        strBuilder.append(boardToShowActivity.getHistory() + System.lineSeparator());
        return strBuilder.toString();
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(INVALID_TEAM, teamName));
        }

        if (!repository.getTeams().get(teamName).getBoardsList().containsKey(boardName)) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardName));
        }
    }
}
