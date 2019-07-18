package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Story;
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
    private PriorityType priority;
    private SizeType size;
    private String description;

    public CreateStoryInBoard(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();

        Board board = repository.getTeams().get(teamName).getBoardsList().get(boardToAddName);
        Story story = factory.createStory(storyNameToBeAdded, status, description, priority, size);
//        repository.addWorkItem(story);
        repository.addStory(story);
        board.addWorkItem(storyNameToBeAdded, story);

        return String.format(STORY_ADDED_TO_BOARD, repository.getWorkItemID(repository.getStoryMap(), story), boardToAddName);
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() < EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if(!repository.getTeams().containsKey(teamName)){
            throw new IllegalArgumentException(String.format(INVALID_TEAM,teamName));
        }

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
            priority = PriorityType.valueOf(parameters.get(4).toUpperCase());
            size = SizeType.valueOf(parameters.get(5).toUpperCase());
            description = String.join(" ", parameters.subList(6, parameters.size()));

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
