package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Team;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class CreateBoardInTeamCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String boardNameToBeAdded;
    private String teamToAddName;


    public CreateBoardInTeamCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }


    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();


        Team team = repository.getTeams().get(teamToAddName);
        Board board = new BoardImpl(boardNameToBeAdded);
        team.addBoard(boardNameToBeAdded, board);
        repository.addBoard(boardNameToBeAdded, board);

        return String.format(BOARD_ADDED_TO_TEAM, boardNameToBeAdded, teamToAddName);
    }


    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getTeams().containsKey(teamToAddName)) {
            throw new IllegalArgumentException(String.format(INVALID_TEAM, teamToAddName));
        }
        if (repository.getTeams().get(teamToAddName).getBoards().containsKey(boardNameToBeAdded)) {
            throw new IllegalArgumentException(String.format(BOARD_ALREADY_EXISTS, boardNameToBeAdded, teamToAddName));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {

            boardNameToBeAdded = parameters.get(0);
            teamToAddName = parameters.get(1);
        } catch (Exception e) {
            throw new IllegalArgumentException("");
//            TODO EXCEPTION
        }
    }


}
