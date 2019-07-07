package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class CreateStoryInBoard implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 7;
    private static final String WORK_ITEM_TYPE = "STORY_";
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String teamName;
    private String boardToAddName;
    private String storyNameToBeAdded;
    private StatusType status;
    private String description;
    private PriorityType priority;
    private SizeType size;

    public CreateStoryInBoard(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository=repository;
        this.factory=factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();

        Board board = repository.getTeams().get(teamName).getBoardsList().get(boardToAddName);
        WorkItem story = factory.createStory(storyNameToBeAdded, status, description, priority,size);
        repository.addWorkItem(story);
        board.addWorkItem(storyNameToBeAdded, story);
        return String.format(STORY_ADDED_TO_BOARD, repository.getWorkItems().size() - 1, boardToAddName);
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getTeams().get(teamName).getBoardsList().containsKey(boardToAddName)) {
            throw new IllegalArgumentException(String.format(INVALID_BOARD, boardToAddName));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            teamName = parameters.get(0);
            boardToAddName = parameters.get(1);
            storyNameToBeAdded = parameters.get(2);
            status = StatusType.valueOf(WORK_ITEM_TYPE + (parameters.get(3).toUpperCase()));
            description = parameters.get(4);
            priority = PriorityType.valueOf(parameters.get(5).toUpperCase());
            size = SizeType.valueOf(parameters.get(6).toUpperCase());

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
//            TODO EXCEPTION
        }
    }
}
