package com.KSDT.commands.change;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.enums.PriorityType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public  class ChangePriorityCommand implements Command {

    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private int boardId;
    private String workItemId;
    private PriorityType newPriority;

    public ChangePriorityCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {

        switch (parameters.get(0).toUpperCase()) {
            case "BUG":
                parameters.remove(0);
                return new ChangeBugPriorityCommand(repository, factory).execute(parameters);
            case "STORY":
                parameters.remove(0);
                return new ChangeStoryPriorityCommand(repository, factory).execute(parameters);
            case "FEEDBACK":
                return FEEDBACK_DOESNT_CONTAIN_PRIORITY;
        }

        return String.format(WRONG_WORK_ITEM_TYPE, parameters.get(0));
    }


}
