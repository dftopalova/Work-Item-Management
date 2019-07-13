package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ShowAllTeamBoardsCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private WorkItemRepository repository;

    private String teamName;

    public ShowAllTeamBoardsCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        teamName = parameters.get(0);
        validateParameters();

        Board[] boardsToPrint = repository.getTeams().get(teamName).getBoardsList().values().toArray(new Board[0]);
        if (checkIsEmpty(boardsToPrint)) return String.format(NO_BOARDS_IN_TEAM_ERROR, teamName);

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("All Boards in team %s:", teamName)).append(System.lineSeparator());
        for (Board board : boardsToPrint) {
            strBuilder.append("=====" + System.lineSeparator() + board + "=====" + System.lineSeparator());
        }
        return strBuilder.toString();
    }

    private boolean checkIsEmpty(Board[] boardsToPrint) {
        if (boardsToPrint.length == 0) {
            return true;
        }
        return false;
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
    }
}
