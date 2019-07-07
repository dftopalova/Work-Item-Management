package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;

import java.util.List;

import static com.KSDT.commands.CommandConstants.WRONG_WORK_ITEM_TYPE;

public class CreateWorkItemInBoard implements Command {

    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    public CreateWorkItemInBoard(WorkItemRepository repository, WorkItemFactory factory){
        this.repository=repository;
        this.factory=factory;
    }

    @Override
    public String execute(List<String> parameters) {
        String workItemType=parameters.get(0).toUpperCase();
        switch (workItemType){
            case "BUG":
                parameters.remove(0);
                return new CreateBugInBoard(repository,factory).execute(parameters);
            case "STORY":
                parameters.remove(0);
                return new CreateStoryInBoard(repository,factory).execute(parameters);
            case "FEEDBACK":
                parameters.remove(0);
                return new CreateFeedbackInBoard(repository,factory).execute(parameters);
        }

        return String.format(WRONG_WORK_ITEM_TYPE, workItemType);
    }
}
