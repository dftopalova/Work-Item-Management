package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.WorkItem;

import java.util.List;

public class ListAllWorkItemsCommand implements Command {

    private WorkItemRepository repository;

    private List<WorkItem> workItemList;

    public ListAllWorkItemsCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        workItemList=repository.getWorkItems();

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("List of all work items:"+System.lineSeparator());
        workItemList.stream().forEach(item -> strBuilder.append(item));
        strBuilder.append(System.lineSeparator());

        return strBuilder.toString();
    }
}
